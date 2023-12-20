import java.util.*; 
import java.io.*;

public class Alphabet {
    // initialising a String containing all the letters of the alphabet and turning it into a character array (to save typing)
    public static String alphabetString = "abcdefghijklmnopqrstuvwxyz"; 
    public static char alphabet[] = alphabetString.toCharArray();

    // declaring a Scanner object to use to scan in the user input
    public static Scanner scanner = new Scanner(System.in); 

    public static void main(String args[]) {

        System.out.println("Type the alphabet in order (lowercase).");
        
        // prompting the user to enter 'f' or 'b' and scanning in the input (only scanning the 0th character of input)
        System.out.println("Forwards or Backwards? (f/b): ");
        char fb = scanner.next().charAt(0); 

        // switch statement to determine forward or backwards, or exit 
        switch(fb) {
                case 'f':
                    System.out.println("Time taken: " + forwards() + "s");
                    break;
                
                case 'b':
                    System.out.println("Time taken: " + backwards() + "s");
                    break;

                default:
                    System.out.println("Invalid input!. You must enter either 'f' or 'b' to start.");
                    System.exit(1);

        }
    }
    
    // method for going through the alphabet forwards
    private static long forwards() {
        // getting the time when the user started 
        long start = System.currentTimeMillis(); 

        // looping for each of the 26 letters
        int i = 0;
        while (i < 26) {
            // checking if the scanned in character was the correct one
            // if the it was not, then i will not be incremented, and it will loop again on the same letter.
            if (scanner.next().charAt(0) == alphabet[i]) {
                if (i == 25) {  // checking if it's the last letter, and if so, printing a special message
                    System.out.println("Correct! Well done!");
                    i++;
                }
                else {          // otherwise, prompting the user to enter the next letter 
                    System.out.println("[" + alphabet[i] + ": Correct! Now type '" + alphabet[++i] + "']"); // i gets incremented
                }
            }

        }
        
        // calculating the total time taken
        long total = System.currentTimeMillis() - start; 

        // returning the total time taken in seconds (by dividing by 1000)
        return total / 1000; 
    }

    // method for going through the alphabet backwards
    private static long backwards() {
        // getting the time when the user started 
        long start = System.currentTimeMillis(); 

        // looping for each of the 26 letters
        int i = 25;
        while (i >= 0) {
            // checking if the scanned in character was the correct one
            // if the it was not, then i will not be decremented, and it will loop again on the same letter.
            if (scanner.next().charAt(0) == alphabet[i]) {
                if (i == 0) {  // checking if it's the last letter, and if so, printing a special message
                    System.out.println("Correct! Well done!");
                    i--;
                }
                else {          // otherwise, prompting the user to enter the next letter 
                    System.out.println("[" + alphabet[i] + ": Correct! Now type '" + alphabet[--i] + "']"); // i gets decremented
                }
            }

        }
        
        // calculating the total time taken
        long total = System.currentTimeMillis() - start; 

        // returning the total time taken in seconds (by dividing by 1000)
        return total / 1000; 
    }

}
