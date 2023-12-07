// class to implement method 3 
public class StackVSQueue implements PalindromeChecker {
    // method 3 - using a stack and a queue to do, essentially, what method 2 does (compare the first index to the last index, etc.)
    @Override
    public boolean checkPalindrome(String str) {
        ArrayStack stack = new ArrayStack();    NewPalindrome.operations[2] += 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
        ArrayQueue queue = new ArrayQueue();    NewPalindrome.operations[2] += 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;

        // looping through each character in the String and adding the character to the stack & queue 
        NewPalindrome.operations[2]++;
        for (int i = 0; i < str.length(); i++) {
            NewPalindrome.operations[2] += 1 + 1 + 1;

            stack.push(str.charAt(i));          NewPalindrome.operations[2] += 1 + 1 + 1 + 1;
            queue.enqueue(str.charAt(i));       NewPalindrome.operations[2] += 1 + 1 + 1 + 1;
        }

        // looping through each character on the stack & queue and comparing them, returning false if they're different
        NewPalindrome.operations[2]++;
        for (int i = 0; i < str.length(); i++) {
            NewPalindrome.operations[2] += 1 + 1 + 1;

            NewPalindrome.operations[2] += 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;
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
            
            NewPalindrome.operations[2] += 3*n + 2;                     // complexity of dequeue is 3n+2
            queue.dequeue();
        }

        return true;
    }
}
