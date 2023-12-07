// class to implement Method 3 
public class ReverseVSOriginal implements PalindromeChecker {
    // method 1 - reversed order String vs original String 
    @Override
    public boolean checkPalindrome(String str) {
        String reversedStr = "";    NewPalindrome.operations[0]++;

        // looping through each character in the String, backwards
        // incrementing operations counter by 2, 1 for initialisating i, 1 for getting str.length()
        NewPalindrome.operations[0] += 1 + 1;
        for (int i = str.length(); i > 0; i--) {
            NewPalindrome.operations[0] += 1 + 1;           // for loop condition check & incrementing i

            reversedStr += str.charAt(i-1); NewPalindrome.operations[0] += 1 + 1;
        }

        // returning true if the Strings are equal, false if not
        NewPalindrome.operations[0] += str.length();    // the equals method must loop through each character of the String to check that they are equal so it is O(n)
        return str.equals(reversedStr);
    }
}
