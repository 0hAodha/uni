public class Test {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(); 
        stack.push('a'); 
        String str = ""; 
        str += stack.top(); 
        System.out.println(str);
        
        char c = 'a'; 
        if (c.equals('a')) {
            System.out.println("nice");
        }
            
    }
}
