public class Test {
    public static void main(String[] args) {
        String str = "1234567890";
        ArrayQueue queue = new ArrayQueue();    

        for (int i = 0; i < str.length(); i++) {
            queue.enqueue(str.charAt(i));       
        }

        for (int i = 0; i < str.length(); i++) {
            queue.dequeue(); 
        }
    }
}
