import java.util.*;
import java.io.*;

public class MapReduceFiles {

  private static final String CSV_FILE = "performance_results.csv";

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: java MapReduceFiles file1.txt file2.txt ... fileN.txt");
      return;
    }

    Map<String, String> input = new HashMap<>();
    try {
      for (String filename : args) {
        input.put(filename, readFile(filename));
      }
    } catch (IOException ex) {
      System.err.println("Error reading files: " + ex.getMessage());
      ex.printStackTrace();
      return;
    }

    int[] mapSizes = {1000, 2000, 5000, 10000};
    int[] reduceSizes = {100, 200, 500, 1000};

    System.out.println("===== Starting Grid Search =====");

    try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
      writer.println("MapLines,ReduceWords,MapTime,GroupTime,ReduceTime,TotalTime");

      for (int mapSize : mapSizes) {
        for (int reduceSize : reduceSizes) {
          runDistributedMapReduce(input, mapSize, reduceSize, writer);
        }
      }

    } catch (IOException e) {
      System.err.println("Error writing to CSV file: " + e.getMessage());
    }

    System.out.println("===== Grid Search Complete =====");
    System.out.println("Results saved to: " + CSV_FILE);
  }

  public static void runDistributedMapReduce(Map<String, String> input, int linesPerMapThread, int wordsPerReduceThread, PrintWriter csvWriter) {
    final Map<String, Map<String, Integer>> output = new HashMap<>();

    // MAP Phase
    long mapStartTime = System.currentTimeMillis();
    List<MappedItem> mappedItems = Collections.synchronizedList(new ArrayList<>());

    final MapCallback<String, MappedItem> mapCallback = new MapCallback<>() {
      public synchronized void mapDone(String file, List<MappedItem> results) {
        mappedItems.addAll(results);
      }
    };

    List<Thread> mapCluster = new ArrayList<>();
    for (Map.Entry<String, String> entry : input.entrySet()) {
      final String file = entry.getKey();
      final String[] lines = entry.getValue().split("\\r?\\n");

      for (int i = 0; i < lines.length; i += linesPerMapThread) {
        int end = Math.min(i + linesPerMapThread, lines.length);
        final List<String> chunk = new ArrayList<>();
        for (int j = i; j < end; j++) {
          chunk.addAll(splitLongLine(lines[j]));
        }

        Thread t = new Thread(() -> map(file, chunk, mapCallback));
        mapCluster.add(t);
        t.start();
      }
    }

    for (Thread t : mapCluster) {
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    long mapTotalTime = System.currentTimeMillis() - mapStartTime;

    // GROUP Phase
    long groupStartTime = System.currentTimeMillis();
    Map<String, List<String>> groupedItems = new HashMap<>();
    for (MappedItem item : mappedItems) {
      groupedItems.computeIfAbsent(item.getWord(), k -> new ArrayList<>()).add(item.getFile());
    }
    long groupTotalTime = System.currentTimeMillis() - groupStartTime;

    // REDUCE Phase
    long reduceStartTime = System.currentTimeMillis();
    final ReduceCallback<String, String, Integer> reduceCallback = (word, result) -> {
      synchronized (output) {
        output.put(word, result);
      }
    };

    List<Thread> reduceCluster = new ArrayList<>();
    List<Map<String, List<String>>> reduceChunks = new ArrayList<>();
    Map<String, List<String>> currentChunk = new HashMap<>();
    int count = 0;

    for (Map.Entry<String, List<String>> entry : groupedItems.entrySet()) {
      currentChunk.put(entry.getKey(), entry.getValue());
      count++;
      if (count >= wordsPerReduceThread) {
        reduceChunks.add(currentChunk);
        currentChunk = new HashMap<>();
        count = 0;
      }
    }
    if (!currentChunk.isEmpty()) reduceChunks.add(currentChunk);

    for (final Map<String, List<String>> chunk : reduceChunks) {
      Thread t = new Thread(() -> {
        for (Map.Entry<String, List<String>> entry : chunk.entrySet()) {
          reduce(entry.getKey(), entry.getValue(), reduceCallback);
        }
      });
      reduceCluster.add(t);
      t.start();
    }

    for (Thread t : reduceCluster) {
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    long reduceTotalTime = System.currentTimeMillis() - reduceStartTime;
    long totalTime = mapTotalTime + groupTotalTime + reduceTotalTime;

    // Print & Log
    System.out.println("MapLines: " + linesPerMapThread + ", ReduceWords: " + wordsPerReduceThread);
    System.out.println("\tMap Time: " + mapTotalTime + " ms");
    System.out.println("\tGroup Time: " + groupTotalTime + " ms");
    System.out.println("\tReduce Time: " + reduceTotalTime + " ms");
    System.out.println("\tTotal Time: " + totalTime + " ms");
    System.out.println("----------------------------------------------------");

    csvWriter.printf("%d,%d,%d,%d,%d,%d%n",
            linesPerMapThread, wordsPerReduceThread,
            mapTotalTime, groupTotalTime, reduceTotalTime, totalTime);
    csvWriter.flush();
  }

  public static void map(String file, List<String> lines, MapCallback<String, MappedItem> callback) {
    List<MappedItem> results = new ArrayList<>();
    for (String line : lines) {
      String[] words = line.trim().split("\\s+");
      for (String word : words) {
        word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (!word.isEmpty()) {
          results.add(new MappedItem(word, file));
        }
      }
    }
    callback.mapDone(file, results);
  }

  public static void reduce(String word, List<String> list, ReduceCallback<String, String, Integer> callback) {
    Map<String, Integer> reducedList = new HashMap<>();
    for (String file : list) {
      reducedList.put(file, reducedList.getOrDefault(file, 0) + 1);
    }
    callback.reduceDone(word, reducedList);
  }

  public interface MapCallback<E, V> {
    void mapDone(E key, List<V> values);
  }

  public interface ReduceCallback<E, K, V> {
    void reduceDone(E e, Map<K, V> results);
  }

  private static class MappedItem {
    private final String word;
    private final String file;

    public MappedItem(String word, String file) {
      this.word = word;
      this.file = file;
    }

    public String getWord() {
      return word;
    }

    public String getFile() {
      return file;
    }

    @Override
    public String toString() {
      return "[\"" + word + "\",\"" + file + "\"]";
    }
  }

  private static String readFile(String pathname) throws IOException {
    File file = new File(pathname);
    StringBuilder fileContents = new StringBuilder((int) file.length());
    Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
    String lineSeparator = System.getProperty("line.separator");

    try {
      while (scanner.hasNextLine()) {
        fileContents.append(scanner.nextLine()).append(lineSeparator);
      }
      return fileContents.toString();
    } finally {
      scanner.close();
    }
  }

  private static List<String> splitLongLine(String line) {
    List<String> result = new ArrayList<>();
    while (line.length() > 80) {
      int splitAt = line.lastIndexOf(' ', 80);
      if (splitAt <= 0) splitAt = 80;
      result.add(line.substring(0, splitAt));
      line = line.substring(splitAt).trim();
    }
    if (!line.isEmpty()) result.add(line);
    return result;
  }
}
