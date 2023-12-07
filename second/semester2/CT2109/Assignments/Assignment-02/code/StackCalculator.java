import java.util.*; 
import java.util.regex.*;

public class StackCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);    // creating a new scanner to read in expressions from the user
        String expr;                            // creating a String to hold the expression read in.
        boolean invalidInput;                   // boolean to tell whether the user's input was invalid

        // will only loop if invalidInput is set to true
        do { 
            // default false, meaning we assume valid input 
            invalidInput = false;

            // prompting the user to enter expression & scanning it in
            System.out.println("Enter an infix numerical expression between 3 & 20 characters:");
            expr = sc.nextLine(); 

            // regex that will be used to match expressions that contain illegal characters
            Pattern illegalchars = Pattern.compile("(?=[^\\^\\*\\/\\+\\-\\(\\)])(?=[^0-9])"); // this is confusing-looking because in java, one has to escape the backslashes for one's regex escape sequences
            Matcher illegalcharsMatcher = illegalchars.matcher(expr);

            // regex that will be used to match numbers that are double-digit or more 
            Pattern doubledigit = Pattern.compile("[0-9][0-9]");    // just checking if a digit is ever followed by another digit 
            Matcher doubledigitMatcher = doubledigit.matcher(expr);

            // checking that the input length is correct
            if (expr.length() > 20 || expr.length() < 3) {
                System.out.println("Invalid input. Please ensure that the length of the input is between 3 and 20 characters");
                invalidInput = true;
            }
            // checking for invalid characters using a regular expression which matches strings that contain characters that are neither operands or digits
            else if (illegalcharsMatcher.find()) {
                System.out.println("Invalid input. Please use only the operators '^, *, /, +, -, (, )' and the operand digits 0-9");
                invalidInput = true;
            }
            // checking for numbers that are not single-digit
            else if (doubledigitMatcher.find()) {
                System.out.println("Invalid input. Please only use single-digit numbers.");
                invalidInput = true;
            }
        } while (invalidInput);

        // converting the expression to postfix
        String postexpr = in2post(expr); 

        // evaluating the postfix expression & printing the result 
        System.out.println(expr + " = " + evalpost(postexpr));
    }

    // method to evaluate postfix expressions
    public static double evalpost(String str) {
        ArrayStack stack = new ArrayStack();    // arraystack to be used during calculations
        char[] chars = str.toCharArray();       // turning the str expression into a character array to make iterating over it easy

        // iterating over the postfix expression
        for (char c : chars) {
            // if the element is an operand, pushing it to the stack 
            if (Character.isDigit(c)) {
                stack.push(c);
            }
            // if the character is not a digit, then it must be an operator
            // popping two operands from the stack for the operator & evaluating them, then pushing the result to the stack
            else {
                // converting the operands to doubles for simplicity's sake if division is encountered
                // using an if statement to detect if the top is a Character or a Double.
                // if it's a Character, casting to char and subtracting the value of the character '0' to get the character's numeric value
                // else, casting it to double
                double operand2 = stack.top() instanceof Character ? (double) ((char) stack.pop() - '0') : (double) stack.pop();    // what would normally be operand 2 in infix will be the first on the stack
                double operand1 = stack.top() instanceof Character ? (double) ((char) stack.pop() - '0') : (double) stack.pop();

                // switch statement on the operator to see which operator it is
                // evaluating the expression and pushing the result to the stack
                switch (c) {
                    // exponentiation
                    case '^':
                        stack.push(Math.pow(operand1, operand2));
                        break; 

                    // multipication
                    case '*':
                        stack.push(operand1 * operand2);
                        break; 

                    // division
                    case '/':
                        stack.push(operand1 / operand2); 
                        break; 

                    // addition
                    case '+':
                        stack.push(operand1 + operand2); 
                        break; 

                    // subtraction
                    case '-':
                        stack.push(operand1 - operand2);
                        break; 

                    // printing an error and exiting with code 1 if an unknown operator is somehow encountered
                    default:
                        System.out.println("The postfix expression contained an unrecognised operator! Exiting...");
                        System.exit(1);
                }
            }
        }

        // returning the final answer - the number on the stack 
        return (double) stack.pop();
    }

    // method to convert infix to postfix
    public static String in2post(String str) {
        ArrayStack stack = new ArrayStack(); 
        char[] chars = str.toCharArray();   // converting str to a character array to make it easier to iterate over 
        String output = "";                 // output string to be returned

        // looping through each character in the array
        for (char c : chars) {
            // if the scanned character is a '(', pushing it to the stack 
            if (c == '(') {
                stack.push(c); 
            }
            // if the scanned character is a ')', popping the stack & appending to the output until a '(' is encountered
            else if (c == ')') {
                while (!stack.isEmpty()) {
                    // if a ( is encountered, popping it & breaking 
                    if (stack.top().equals('(')) {
                        stack.pop();
                        break;
                    }
                    // otherwise, popping the stack & appending to the output
                    else {
                        output += stack.pop();
                    }
                }
            }
            // appending the character to the output string if it is an operand (digit) 
            else if (Character.isDigit(c)) {
                output += c; 
            } 
            // if the stack is empty or contains '(' or the precedence of the scanned operator is greater than the precedence of the operator in the stack
            // important that stack.isEmpty() comes first - the rest of the if condition will not be evaluated if this is true as we are using OR 
            // this prevents any NullPointerExceptions from being thrown if we try to access the top of an empty stack
            else if (stack.isEmpty() || stack.top().equals('(') || precedence(c) > precedence((char) stack.top())) {
                // pushing the scanned operator to the stack 
                stack.push(c);
            }
            else {
                // popping all the operators from the stack which are >= to in precedence to that of the scanned operator & appending them to the output string
                while (!stack.isEmpty() && precedence((char) stack.top()) >= precedence(c)) {
                    // if parenthesis is encountered, popping it, stopping, and pushing the scanned operator
                    if (stack.top().equals('(') || stack.top().equals(')')) {
                        stack.pop(); 
                        break;
                    }
                    // otherwise, popping the stack and appending to output
                    else {
                        output += stack.pop();
                    }
                }

                // after that, pushing the scanned operator to the stack 
                stack.push(c);
            }
        }

        // popping and appending to output any remaining content from the stack 
        while (!stack.isEmpty()) {
            output += stack.pop(); 
        }

        // returning the generated postfix expression
        return output;
    }

    // method to get the precedence of each operator - the higher the returnval, the higher the precedence. -1 indicates no precedence (invalid char) 
    public static int precedence(char c) {
        switch (c) {
            // exponentiation
            case '^':
                return 2; 

            // multiplication
            case '*':
                return 1; 

            // division
            case '/':
                return 1; 
            
            // addition
            case '+':
                return 0;

            // subtraction
            case '-':
                return 0; 
    
            // default - invalid operator
            default:
                return -1;
        }
    }
}
