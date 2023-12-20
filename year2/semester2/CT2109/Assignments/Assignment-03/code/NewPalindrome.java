import java.io.*;

public class NewPalindrome {
    public static long[] operations = new long[4];  // array to contain the global operations count for each method 
    public static int[]  decCount   = new int[4];   // array to hold the count of decimal palindromes found using each method
    public static int[]  binCount   = new int[4];   // array to hold the count of binary  palindromes found using each method
    public static int[]  bothCount  = new int[4];   // array to hold the count of numbers that are palindromes in both decimal & binary found using each method
    public static long[] startTime  = new long[4];  // array to hold the start time of each method's test loop
    public static long[] totalTime  = new long[4];  // array to hold the total time of each method's test loop

    // array to hold all the String versions of the numbers so that they don't have to be generated for each method
    // 0th column will be decimal, 1st column will be binary
    public static String[][] strings = new String[1_000_001][2];

    // array of StringBuilder objects used to hold the csv data (size of problem, number of operations) for each method
    public static StringBuilder[] data = new StringBuilder[4]; 

    // array of the four classes that will be tested
    public static PalindromeChecker[] palindromeCheckers = {new ReverseVSOriginal(), new IVersusNMinusI(), new StackVSQueue(), new RecursiveReverse()}; 

    public static void main(String args[]) {
        // initialising the data array to StringBuilder objects
        for (int i = 0; i < 4; i++) {
            data[i] = new StringBuilder("operations,size\n");
        }

        // filling up the strings array
        for (int i = 0; i <= 1_000_000; i++) {
            strings[i][0] = Integer.toString(i, 10);        // converting i to a String base 10
            strings[i][1] = binary2string(strings[i][0]);   // converting the decimal String to a binary String

        }
        // looping through each PalindromeChecker object in the palindromeCheckers array
        for (int j = 0; j < 4; j++) {
            // getting start time 
            startTime[j] = System.currentTimeMillis();  operations[j]++;

            // looping through the numbers 0 to 1,000,000 and checking if their binary & decimal representations are palindromic
            operations[j]++;
            for (int i = 0; i <= 1_000_000; i++) {
                // incrementing the operations count by 2, 1 for the loop condition check and 1 for incrementing i
                operations[j] += 2;

                // converting the number to a decimal or binary String and checking if is a palindrome
                boolean isDecPalindrome = palindromeCheckers[j].checkPalindrome(strings[i][0]); operations[j]++;
                boolean isBinPalindrome = palindromeCheckers[j].checkPalindrome(strings[i][1]); operations[j]++; 

                // incrementing the appropriate counter if the number is a palindrome in that base
                decCount[j]   = isDecPalindrome ? decCount[j] + 1 : decCount[j];                      operations[j] += 1 + 1;       // incremnting by 2, 1 for assignment, 1 for condition check
                binCount[j]   = isBinPalindrome ? binCount[j] + 1 : binCount[j];                      operations[j] += 1 + 1;
                bothCount[j]  = isDecPalindrome && isBinPalindrome ? bothCount[j] + 1 : bothCount[j]; operations[j] += 1 + 1 +1;    // 2 condition checks and one assignment, so incrementing by 3

                // appending to the data StringBuilder at intervals of 50,000 
                if (i % 50_000 == 0) {
                    data[j].append(operations[j] + "," + i + "\n");
                }
            }

            // calculating total time taken for method 1 and printing out the results
            totalTime[j] = System.currentTimeMillis() - startTime[j];  operations[j] += 1 + 1;   // incrementing by 2, 1 for getting current time and subtracting start time, 1 for assignment

            System.out.println("Number of decimal palindromes found using Method " + j + ": " + decCount[j]); 
            System.out.println("Number of binary  palindromes found using Method " + j + ": " + binCount[j]); 
            System.out.println("Number of palindromes in both decimal & binary found using Method " + j + ": " + bothCount[j]); 
            System.out.println("Number of primitive operations taken in Method " + j + ": " + operations[j]);
            System.out.println("Time taken for Method " + j + ": " + totalTime[j] + " milliseconds");
            System.out.println();

            // outputting the data to separate csv files
            try {
                String filename = "method" + j + ".csv";
                File csv = new File(filename);
                
                // creating file if it doesn't already exist
                csv.createNewFile();

                FileWriter writer = new FileWriter(filename);
                writer.write(data[j].toString());
                writer.close();

            } catch (IOException e) {
                System.out.println("IO Error occurred");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    // utility method to convert a decimal String to its equivalent binary String
    public static String binary2string(String decimalStr) {
        return Integer.toString(Integer.parseInt(decimalStr),  2);  // parsing the String to an int and then parsing that int to a binary String 
    }
}
