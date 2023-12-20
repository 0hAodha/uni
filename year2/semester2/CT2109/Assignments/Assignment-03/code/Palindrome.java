import java.io.*;

public class Palindrome {
    // global operations count for each method 
    public static long operations1 = 0;
    public static long operations2 = 0;
    public static long operations3 = 0;
    public static long operations4 = 0;

    // String objects used to hold the csv data (size of problem, number of operations) for each method
    public static StringBuilder data1 = new StringBuilder("operations,size\n");
    public static StringBuilder data2 = new StringBuilder("operations,size\n");
    public static StringBuilder data3 = new StringBuilder("operations,size\n");
    public static StringBuilder data4 = new StringBuilder("operations,size\n");

    public static void main(String args[]) {
        // generating all the String versions of the numbers so that they don't have to be generated for each method
        // 0th column will be decimal, 1st column will be binary
        String[][] strings = new String[1_000_001][2];
        for (int i = 0; i <= 1_000_000; i++) {
            strings[i][0] = Integer.toString(i, 10);        // converting i to a String base 10
            strings[i][1] = binary2string(strings[i][0]);   // converting the decimal String to a binary String

        }

        // variables for method 1 - reversed order String vs original String  
        int decCount1   = 0;    operations1++;  // count of decimal palindromes for use by method 1
        int binCount1   = 0;    operations1++;  // count of binary  palindromes for use by method 1
        int bothCount1  = 0;    operations1++;  // count of numbers that are palindromic in both binary & decimal for use by method 1 

        long startTime1 = System.currentTimeMillis();   operations1 += 1 + 1;

        // testing method 1 - reversed order String vs original String  
        // incrementing the operations counter for the initialisation of i below
        operations1++;
        for (int i = 0; i <= 1_000_000; i++) {
            // incrementing the operations count by 2, 1 for the loop condition check and 1 for incrementing i
            operations1 += 2;

            // converting the number to a decimal or binary String and checking if is a palindrome
            boolean isDecPalindrome = reverseVSoriginal(strings[i][0]); operations1++;
            boolean isBinPalindrome = reverseVSoriginal(strings[i][1]); operations1++; 

            // incrementing the appropriate counter if the number is a palindrome in that base
            decCount1   = isDecPalindrome ? decCount1 + 1 : decCount1;                      operations1 += 1 + 1;       // incremnting by 2, 1 for assignment, 1 for condition check
            binCount1   = isBinPalindrome ? binCount1 + 1 : binCount1;                      operations1 += 1 + 1;
            bothCount1  = isDecPalindrome && isBinPalindrome ? bothCount1 + 1 : bothCount1; operations1 += 1 + 1 +1;    // 2 condition checks and one assignment, so incrementing by 3

            // appending to the data StringBuilder at intervals of 50,000 
            if (i % 50_000 == 0) {
                data1.append(operations1 + "," + i + "\n");
            }
        }

        // calculating total time taken for method 1 and printing out the results
        long totalTime1 = System.currentTimeMillis() - startTime1;  operations1 += 1 + 1;   // incrementing by 2, 1 for getting current time and subtracting start time, 1 for assignment

        System.out.println("Number of decimal palindromes found using Method 1: " + decCount1); 
        System.out.println("Number of binary  palindromes found using Method 1: " + binCount1); 
        System.out.println("Number of palindromes in both decimal & binary found using Method 1: " + bothCount1); 
        System.out.println("Number of primitive operations taken in Method 1: " + operations1);
        System.out.println("Time taken for Method 1: " + totalTime1 + " milliseconds");

        // variables for method 2 - comparing each element at index i to the element at n - i where n is the last index
        int decCount2   = 0;    operations2++;  // count of decimal palindromes for use by method 2
        int binCount2   = 0;    operations2++;  // count of binary  palindromes for use by method 2
        int bothCount2  = 0;    operations2++;  // count of numbers that are palindromic in both binary & decimal for use by method 2 

        long startTime2 = System.currentTimeMillis();   operations2 +=  1 + 1;   

        // testingmethod 2 - comparing each element at index i to the element at n - i where n is the last index 
        operations2++;
        for (int i = 0; i <= 1_000_000; i++) {
            operations2 += 1 + 1;

            // converting the number to a decimal or binary String and checking if is a palindrome
            boolean isDecPalindrome = iVSiMinusn(strings[i][0]);    operations2++;
            boolean isBinPalindrome = iVSiMinusn(strings[i][1]);    operations2++; 

            // incrementing the appropriate counter if the number is a palindrome in that base
            decCount2   = isDecPalindrome ? decCount2 + 1 : decCount2;                      operations2 += 1 + 1;
            binCount2   = isBinPalindrome ? binCount2 + 1 : binCount2;                      operations2 += 1 + 1; 
            bothCount2  = isDecPalindrome && isBinPalindrome ? bothCount2 + 1 : bothCount2; operations2 += 1 + 1 +1;

            // appending to the data StringBuilder at intervals of 50,000 
            if (i % 50_000 == 0) {
                data2.append(operations2 + "," + i + "\n");
            }
        }

        // calculating total time taken for method 2 and printing out the results
        long totalTime2 = System.currentTimeMillis() - startTime2;  operations2 += 1 + 1;

        System.out.println();
        System.out.println("Number of decimal palindromes found using Method 2: " + decCount2); 
        System.out.println("Number of binary  palindromes found using Method 2: " + binCount2); 
        System.out.println("Number of palindromes in both decimal & binary found using Method 2: " + bothCount2); 
        System.out.println("Number of primitive operations taken in Method 2: " + operations2);
        System.out.println("Time taken for Method 2: " + totalTime2 + " milliseconds");

        // variables for method 3 - comparing each element at index i to the element at n - i where n is the last index
        int decCount3   = 0;    operations3++;  // count of decimal palindromes for use by method 3
        int binCount3   = 0;    operations3++;  // count of binary  palindromes for use by method 3
        int bothCount3  = 0;    operations3++;  // count of numbers that are palindromic in both binary & decimal for use by method 3 

        long startTime3 = System.currentTimeMillis();   operations3 += 1 + 1;

        // testingmethod 3 - comparing each element at index i to the element at n - i where n is the last index 
        operations3++;
        for (int i = 0; i <= 1_000_000; i++) {
            operations3 += 1 + 1;
            // converting the number to a decimal or binary String and checking if is a palindrome
            boolean isDecPalindrome = stackVsqueue(strings[i][0]);  operations3++;
            boolean isBinPalindrome = stackVsqueue(strings[i][1]);  operations3++;

            // incrementing the appropriate counter if the number is a palindrome in that base
            decCount3   = isDecPalindrome ? decCount3 + 1 : decCount3;                      operations3 += 1 + 1;
            binCount3   = isBinPalindrome ? binCount3 + 1 : binCount3;                      operations3 += 1 + 1;
            bothCount3  = isDecPalindrome && isBinPalindrome ? bothCount3 + 1 : bothCount3; operations3 += 1 + 1 + 1;

            // appending to the data StringBuilder at intervals of 50,000 
            if (i % 50_000 == 0) {
                data3.append(operations3 + "," + i + "\n");
            }
        }

        // calculating total time taken for method 3 and printing out the results
        long totalTime3 = System.currentTimeMillis() - startTime3;  operations3 += 1 + 1;

        System.out.println();
        System.out.println("Number of decimal palindromes found using Method 3: " + decCount3); 
        System.out.println("Number of binary  palindromes found using Method 3: " + binCount3); 
        System.out.println("Number of palindromes in both decimal & binary found using Method 3: " + bothCount3); 
        System.out.println("Number of primitive operations taken in Method 3: " + operations3);
        System.out.println("Time taken for Method 3: " + totalTime3 + " milliseconds");

        // variables for method 4 - comparing each element at index i to the element at n - i where n is the last index
        int decCount4   = 0;    operations4++;  // count of decimal palindromes for use by method 4
        int binCount4   = 0;    operations4++;  // count of binary  palindromes for use by method 4
        int bothCount4  = 0;    operations4++;  // count of numbers that are palindromic in both binary & decimal for use by method 4 

        long startTime4 = System.currentTimeMillis();               operations4 += 1 + 1;

        // testingmethod 4 - comparing each element at index i to the element at n - i where n is the last index 
        operations4++;
        for (int i = 0; i <= 1_000_000; i++) {
            operations4 += 2;

            // converting the number to a decimal or binary String and checking if is a palindrome
            boolean isDecPalindrome = recursiveReverseVSoriginal(strings[i][0]);    operations4++;
            boolean isBinPalindrome = recursiveReverseVSoriginal(strings[i][1]);    operations4++;

            // incrementing the appropriate counter if the number is a palindrome in that base
            decCount4   = isDecPalindrome ? decCount4 + 1 : decCount4;                      operations4 += 1 + 1;
            binCount4   = isBinPalindrome ? binCount4 + 1 : binCount4;                      operations4 += 1 + 1;
            bothCount4  = isDecPalindrome && isBinPalindrome ? bothCount4 + 1 : bothCount4; operations4 += 1 + 1 + 1;

            // appending to the data StringBuilder at intervals of 50,000 
            if (i % 50_000 == 0) {
                data4.append(operations4 + "," + i + "\n");
            }
        }

        // calculating total time taken for method 4 and printing out the results
        long totalTime4 = System.currentTimeMillis() - startTime4;  operations4 += 1 + 1;

        System.out.println();
        System.out.println("Number of decimal palindromes found using Method 4: " + decCount4); 
        System.out.println("Number of binary  palindromes found using Method 4: " + binCount4); 
        System.out.println("Number of palindromes in both decimal & binary found using Method 4: " + bothCount4); 
        System.out.println("Number of primitive operations taken in Method 4: " + operations4);
        System.out.println("Time taken for Method 4: " + totalTime4 + " milliseconds");

        // outputting the data to separate csv files
        try {
            File csv1 = new File("method1.csv");
            File csv2 = new File("method2.csv");
            File csv3 = new File("method3.csv");
            File csv4 = new File("method4.csv");
            
            // creating files if they don't already exist
            csv1.createNewFile();
            csv2.createNewFile();
            csv3.createNewFile();
            csv4.createNewFile();

            FileWriter writer1 = new FileWriter("method1.csv");
            writer1.write(data1.toString());
            writer1.close();

            FileWriter writer2 = new FileWriter("method2.csv");
            writer2.write(data2.toString());
            writer2.close();

            FileWriter writer3 = new FileWriter("method3.csv");
            writer3.write(data3.toString());
            writer3.close();

            FileWriter writer4 = new FileWriter("method4.csv");
            writer4.write(data4.toString());
            writer4.close();
        } catch (IOException e) {
            System.out.println("IO Error occurred");
            e.printStackTrace();
            System.exit(1);
        }
    }

    // method 1 - reversed order String vs original String 
    public static boolean reverseVSoriginal(String str) {
        String reversedStr = "";    operations1++;

        // looping through each character in the String, backwards
        // incrementing operations counter by 2, 1 for initialisating i, 1 for getting str.length()
        operations1 += 1 + 1;
        for (int i = str.length(); i > 0; i--) {
            operations1 += 1 + 1;           // for loop condition check & incrementing i

            reversedStr += str.charAt(i-1); operations1 += 1 + 1;
        }

        // returning true if the Strings are equal, false if not
        operations1 += str.length();    // the equals method must loop through each character of the String to check that they are equal so it is O(n)
        return str.equals(reversedStr);
    }
    
    // method 2 - comparing each element at index i to the element at n - i where n is the last index
    public static boolean iVSiMinusn(String str) {
        // looping through the first half of the String 
        operations2++;
        for (int i = 0; i < Math.floor(str.length() / 2); i++) {
            operations2 += 1 + 1 + 1 + 1;   // 1 for the getting str.length(), 1 for Math,floor, 1 for checking condition, 1 for incrementing

            // returning false if the digits don't match
            operations2 += 1 + 1 + 1 + 1;   // 1 for str.charAt(i), 1 for ((str.lenght() -1) - 1), 1 for the other str.charAt(), 1 for checking the condition
            if (str.charAt(i) != str.charAt((str.length()-1) - i)) {
                return false;
            }
        }

        // returning true as default
        return true;
    }

    // method 3 - using a stack and a queue to do, essentially, what method 2 does
    public static boolean stackVsqueue(String str) {
        ArrayStack stack = new ArrayStack();    operations3 += 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
        ArrayQueue queue = new ArrayQueue();    operations3 += 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;

        // looping through each character in the String and adding the character to the stack & queue 
        operations3++;
        for (int i = 0; i < str.length(); i++) {
            operations3 += 1 + 1 + 1;

            stack.push(str.charAt(i));          operations3 += 1 + 1 + 1 + 1;
            queue.enqueue(str.charAt(i));       operations3 += 1 + 1 + 1 + 1;
        }

        // looping through each character on the stack & queue and comparing them, returning false if they're different
        operations3++;
        for (int i = 0; i < str.length(); i++) {
            operations3 += 1 + 1 + 1;

            operations3 += 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
            if (!stack.pop().equals(queue.front())) {   
                return false;
            }

            // the complexity of ArrayQueue.dequeue() is 3n+2, where n is the number of items in the queue when dequeue() is called. 
            // we need to determine the number of items in the queue so that we can determine the number of primitive operations performed when queue.dequeue() is called.
            // to do this, we'll loop through the queue, dequeuing each object and enqueueing it in another ArrayQueue. once complete, we'll reassign the variable queue to point to the new ArrayQueue containing all the objects
            ArrayQueue newQueue = new ArrayQueue();     // not counting the operations for this as it's not part of the algorithm, it's part of the operations counting
            int n = 0;                                  // n is the number of items in the ArrayQueue when dequeue() is called

            while (!queue.isEmpty()) {
                newQueue.enqueue(queue.dequeue());
                n++;
            }
            
            queue = newQueue;                           // setting queue to point to the newQueue, which is just the state that queue would have been in if we didn't do this to calculate the primitive operations
            newQueue = null;                            // don't need the newQueue object reference anymore
            
            operations3 += 3*n + 2;                     // complexity of dequeue is 3n+2
            queue.dequeue();
        }

        return true;
    }

    // method 4 - comparing the String reversed using recursion to the original String (essentially method 1 but with recursion)
    public static boolean recursiveReverseVSoriginal(String str) {
        // returning true if the original String is equal to the reversed String, false if not
        operations4++; 
        return str.equals(reverse(str));
    }

    // method to reverse the characters in a String using recursion 
    public static String reverse(String str) {
        // base case - returning an empty String if there is no character left in the String
        operations4++;
        if (str.length() == 0) {    
            return "";
        }
        else {
            char firstChar = str.charAt(0);                 operations4 += 1 + 1;
            String remainder = str.substring(1);            operations4 += 1 + 1;   // selecting the rest of the String, excluding the 0th character

            // recursing with what's left of the String
            String reversedRemainder = reverse(remainder);  operations4++;

            // returning the reversed rest of String with the first character of the String appended
            return reversedRemainder + firstChar;
        }
    }

    // utility method to convert a decimal String to its equivalent binary String
    public static String binary2string(String decimalStr) {
        return Integer.toString(Integer.parseInt(decimalStr),  2);  // parsing the String to an int and then parsing that int to a binary String 
    }
}
