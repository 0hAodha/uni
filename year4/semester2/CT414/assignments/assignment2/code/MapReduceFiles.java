import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;

public class MapReduceFiles {

  private static final int LINES_PER_MAP_THREAD = 2000;

  public static void main(String[] args) {

    if (args.length < 3) {
      System.err.println("usage: java MapReduceFiles file1.txt file2.txt file3.txt");

    }

    Map<String, String> input = new HashMap<String, String>();
    try {
      input.put(args[0], readFile(args[0]));
      input.put(args[1], readFile(args[1]));
      input.put(args[2], readFile(args[2]));

      for (String filename : args) {
        input.put(filename, readFile(filename));
      }

    }
    catch (IOException ex)
    {
      System.err.println("Error reading files...\n" + ex.getMessage());
      ex.printStackTrace();
      System.exit(0);
    }

    // APPROACH #1: Brute force
    {
      long startTime = System.currentTimeMillis();

      Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();

      Iterator<Map.Entry<String, String>> inputIter = input.entrySet().iterator();
      while(inputIter.hasNext()) {
        Map.Entry<String, String> entry = inputIter.next();
        String file = entry.getKey();
        String contents = entry.getValue();

        String[] words = contents.trim().split("\\s+");

        for(String word : words) {

          Map<String, Integer> files = output.get(word);
          if (files == null) {
            files = new HashMap<String, Integer>();
            output.put(word, files);
          }

          Integer occurrences = files.remove(file);
          if (occurrences == null) {
            files.put(file, 1);
          } else {
            files.put(file, occurrences.intValue() + 1);
          }
        }
      }

      long timeTaken = System.currentTimeMillis() - startTime;
      System.out.println("Brute Force Results:");
      System.out.println("\tTotal Time: " + timeTaken + "\n");
    }


    // APPROACH #2: MapReduce
    {
      long startTime = System.currentTimeMillis();

      Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();

      // MAP:
      long mapStartTime = System.currentTimeMillis();
      List<MappedItem> mappedItems = new ArrayList<MappedItem>();

      Iterator<Map.Entry<String, String>> inputIter = input.entrySet().iterator();
      while(inputIter.hasNext()) {
        Map.Entry<String, String> entry = inputIter.next();
        String file = entry.getKey();
        String contents = entry.getValue();

        map(file, contents, mappedItems);
      }
      long mapTotalTime = System.currentTimeMillis() - mapStartTime;

      // GROUP:
      long groupStartTime = System.currentTimeMillis();

      Map<String, List<String>> groupedItems = new HashMap<String, List<String>>();

      Iterator<MappedItem> mappedIter = mappedItems.iterator();
      while(mappedIter.hasNext()) {
        MappedItem item = mappedIter.next();
        String word = item.getWord();
        String file = item.getFile();
        List<String> list = groupedItems.get(word);
        if (list == null) {
          list = new ArrayList<String>();
          groupedItems.put(word, list);
        }
        list.add(file);
      }

      long groupTotalTime = System.currentTimeMillis() - groupStartTime;

      // REDUCE:
      long reduceStartTime = System.currentTimeMillis();

      Iterator<Map.Entry<String, List<String>>> groupedIter = groupedItems.entrySet().iterator();
      while(groupedIter.hasNext()) {
        Map.Entry<String, List<String>> entry = groupedIter.next();
        String word = entry.getKey();
        List<String> list = entry.getValue();

        reduce(word, list, output);
      }

      long endTime = System.currentTimeMillis();
      long reduceTotalTime = endTime - reduceStartTime;
      long totalTime = endTime - startTime;

      System.out.println("MapReduce Results:");
      System.out.println("\tMap Time: " + mapTotalTime);
      System.out.println("\tGroup Time: " + groupTotalTime);
      System.out.println("\tReduce Time: " + reduceTotalTime);
      System.out.println("\tTotal Time: " + totalTime + "\n");
    }


    // APPROACH #3: Distributed MapReduce
    {
      long startTime = System.currentTimeMillis();
      final Map<String, Map<String, Integer>> output = new HashMap<String, Map<String, Integer>>();

      // MAP:
      long mapStartTime = System.currentTimeMillis();

      List<MappedItem> mappedItems = new ArrayList<MappedItem>();

      final MapCallback<String, MappedItem> mapCallback = new MapCallback<String, MappedItem>() {
        @Override
        public synchronized void mapDone(String file, List<MappedItem> results) {
          mappedItems.addAll(results);
        }
      };

      List<Thread> mapCluster = new ArrayList<Thread>();

      for (Map.Entry<String, String> entry : input.entrySet()) {
        final String file = entry.getKey();
        final String contents = entry.getValue();
        final String[] lines = contents.split("\\r?\\n");

        for (int i = 0; i < lines.length; i += LINES_PER_MAP_THREAD) {
          int end = Math.min(i + LINES_PER_MAP_THREAD, lines.length);
          final List<String> chunk = new ArrayList<>();
          for (int j = i; j < end; j++) {
            chunk.addAll(splitLongLine(lines[j]));
          }

          Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
              map(file, chunk, mapCallback);
            }
          });
          mapCluster.add(t);
          t.start();
        }
      }

      // wait for mapping phase to be over:
      for(Thread t : mapCluster) {
        try {
          t.join();
        } catch(InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      long mapTotalTime = System.currentTimeMillis() - mapStartTime;

      // GROUP:
      long groupStartTime = System.currentTimeMillis();
      Map<String, List<String>> groupedItems = new HashMap<String, List<String>>();

      Iterator<MappedItem> mappedIter = mappedItems.iterator();
      while(mappedIter.hasNext()) {
        MappedItem item = mappedIter.next();
        String word = item.getWord();
        String file = item.getFile();
        List<String> list = groupedItems.get(word);
        if (list == null) {
          list = new ArrayList<String>();
          groupedItems.put(word, list);
        }
        list.add(file);
      }

      long groupTotalTime = System.currentTimeMillis() - groupStartTime;

      // REDUCE:
      long reduceStartTime = System.currentTimeMillis();

      final ReduceCallback<String, String, Integer> reduceCallback = new ReduceCallback<String, String, Integer>() {
        @Override
        public synchronized void reduceDone(String k, Map<String, Integer> v) {
          output.put(k, v);
        }
      };

      List<Thread> reduceCluster = new ArrayList<Thread>(groupedItems.size());

      // Replace this constant if you want to try different values for performance tests
      final int WORDS_PER_REDUCE_THREAD = 500; // Between 100 and 1000

      List<Map<String, List<String>>> reduceChunks = new ArrayList<>();
      Map<String, List<String>> currentChunk = new HashMap<>();
      int count = 0;

// Build chunks of words (100-1000 per thread)
      for (Map.Entry<String, List<String>> entry : groupedItems.entrySet()) {
        currentChunk.put(entry.getKey(), entry.getValue());
        count++;
        if (count >= WORDS_PER_REDUCE_THREAD) {
          reduceChunks.add(currentChunk);
          currentChunk = new HashMap<>();
          count = 0;
        }
      }
      if (!currentChunk.isEmpty()) {
        reduceChunks.add(currentChunk);
      }

      for (final Map<String, List<String>> chunk : reduceChunks) {
        Thread t = new Thread(new Runnable() {
          @Override
          public void run() {
            for (Map.Entry<String, List<String>> entry : chunk.entrySet()) {
              reduce(entry.getKey(), entry.getValue(), reduceCallback);
            }
          }
        });
        reduceCluster.add(t);
        t.start();
      }

      // wait for reducing phase to be over:
      for(Thread t : reduceCluster) {
        try {
          t.join();
        } catch(InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      long endTime = System.currentTimeMillis();
      long reduceTotalTime = endTime - startTime;
      long totalTime = endTime - startTime;


      System.out.println("Distributed MapReduce Results:");
      System.out.println("\tMap Time: " + mapTotalTime);
      System.out.println("\tGroup Time: " + groupTotalTime);
      System.out.println("\tReduce Time: " + reduceTotalTime);
      System.out.println("\tTotal Time: " + totalTime + "\n");
    }
  }

  public static void map(String file, List<String> lines, MapCallback<String, MappedItem> callback) {
    List<MappedItem> results = new ArrayList<MappedItem>();
    for (String line : lines) {
      String[] words = line.trim().split("\s+");
      for(String word: words) {
        word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (!word.isEmpty()) {
          results.add(new MappedItem(word, file));
        }
      }
    }
    callback.mapDone(file, results);
  }

  public static void map(String file, String contents, List<MappedItem> mappedItems) {
    String[] words = contents.trim().split("\s+");
    for(String word: words) {
      word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
      if (!word.isEmpty()) {
        mappedItems.add(new MappedItem(word, file));
      }
    }
  }

  public static void reduce(String word, List<String> list, Map<String, Map<String, Integer>> output) {
    Map<String, Integer> reducedList = new HashMap<String, Integer>();
    for(String file: list) {
      Integer occurrences = reducedList.get(file);
      if (occurrences == null) {
        reducedList.put(file, 1);
      } else {
        reducedList.put(file, occurrences.intValue() + 1);
      }
    }
    output.put(word, reducedList);
  }

  public static void reduce(String word, List<String> list, ReduceCallback<String, String, Integer> callback) {

    Map<String, Integer> reducedList = new HashMap<String, Integer>();
    for(String file: list) {
      Integer occurrences = reducedList.get(file);
      if (occurrences == null) {
        reducedList.put(file, 1);
      } else {
        reducedList.put(file, occurrences.intValue() + 1);
      }
    }
    callback.reduceDone(word, reducedList);
  }

  public static interface MapCallback<E, V> {
    public void mapDone(E key, List<V> values);
  }

  public static interface ReduceCallback<E, K, V> {
    public void reduceDone(E e, Map<K,V> results);
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
      if (scanner.hasNextLine()) {
        fileContents.append(scanner.nextLine());
      }
      while (scanner.hasNextLine()) {
        fileContents.append(lineSeparator + scanner.nextLine());
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
