// class to implement Method 2 
public class IVersusNMinusI implements PalindromeChecker {
    // method 2 - comparing each element at index i to the element at n - i where n is the last index
    @Override
    public boolean checkPalindrome(String str) {
        // looping through the first half of the String 
        NewPalindrome.operations[1]++;
        for (int i = 0; i < Math.floor(str.length() / 2); i++) {
            NewPalindrome.operations[1] += 1 + 1 + 1 + 1;   // 1 for the getting str.length(), 1 for Math,floor, 1 for checking condition, 1 for incrementing

            // returning false if the digits don't match
            NewPalindrome.operations[1] += 1 + 1 + 1 + 1;   // 1 for str.charAt(i), 1 for ((str.lenght() -1) - 1), 1 for the other str.charAt(), 1 for checking the condition
            if (str.charAt(i) != str.charAt((str.length()-1) - i)) {
                return false;
            }
        }

        // returning true as default
        return true;
    }
}
