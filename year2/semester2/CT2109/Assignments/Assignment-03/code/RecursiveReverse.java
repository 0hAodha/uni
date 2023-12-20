// class to implement method 4 
public class RecursiveReverse implements PalindromeChecker {
    // comparing the String reversed using recursion to the original String (essentially method 1 but with recursion)
    @Override 
    public boolean checkPalindrome(String str) {
        // returning true if the original String is equal to the reversed String, false if not
        NewPalindrome.operations[3]++; 
        return str.equals(reverse(str));
    }

    // method to reverse the characters in a String using recursion 
    public static String reverse(String str) {
        // base case - returning an empty String if there is no character left in the String
        NewPalindrome.operations[3]++;
        if (str.length() == 0) {    
            return "";
        }
        else {
            char firstChar = str.charAt(0);                 NewPalindrome.operations[3] += 1 + 1;
            String remainder = str.substring(1);            NewPalindrome.operations[3] += 1 + 1;   // selecting the rest of the String, excluding the 0th character

            // recursing with what's left of the String
            String reversedRemainder = reverse(remainder);  NewPalindrome.operations[3]++;

            // returning the reversed rest of String with the first character of the String appended
            return reversedRemainder + firstChar;
        }
    }
}
