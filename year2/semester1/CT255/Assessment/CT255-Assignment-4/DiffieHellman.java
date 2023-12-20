import java.util.ArrayList;

public class DiffieHellman {

    public static void main(String[] args) {
        generate_params(); 
    }

    // method to generate the random DH parameters
    public static void generate_params() {
        int prime          = generate_prime();                           // generating a prime number     
        System.out.println("prime: " + prime);
        int primitive_root = generate_primitive_root(prime);             // generating a primitive root of that prime
        System.out.println("prime: " + prime + ". primtive root : " + primitive_root);
   }

    // method to generate a prime number in the range 10000 - 100000
    public static int generate_prime() {
        // generating an ArrayList of prime numbers in the range 10,000 to 100,000 using the Sieve of Eratosthenes
        // creating a list of consecutive integers from 2 through 100,000
        ArrayList<Integer> integers = new ArrayList<Integer>();

        for (int n = 2; n <= 100000; n++) {
            integers.add(n);  
        }

        // initially, let p equal 2, the smallest prime number in the list.
        int p = integers.get(0);

        // variable to hold the number of known primes in the ArrayList
        int primes_count = 1;

        // looping while p is less than the final value in the list
        while (p < integers.get(integers.size() - 1)) {
            // removing the mutliples of p from the list
            for (int i = 2; (i * p) <= integers.get(integers.size() - 1); i++) {
                integers.remove(Integer.valueOf(i * p));
            }
        
            // let p equal the new smallest element in the ArrayList
            p = integers.get(primes_count++);
        }

        // cutting out the section of the ArrayList that contains prime number outside the appropriate range
        while (integers.get(0) < 10000) {   // removing the first element of the ArrayList while the first index of the ArrayList holds a value less than 10,000  
            integers.remove(0);
        }

        // selecting the element at a random index in the ArrayList of primes as our prime number
        int prime = integers.get((int) (Math.random() * (integers.size() - 1)));

        return prime;
    }

    public static int generate_primitive_root(int prime) {
        // ArrayList<Integer> primitive_roots = new ArrayList<Integer>();  // ArrayList to hold the primitive roots found

        // looping through all the numbers in the range 2 to the prime minus 1 to see if they're primitive roots of the prime, breaking when we find the first primitive root
        for (int n = 3; n < prime - 1; n++) {
            boolean is_n_a_primtive_root = true;                            // boolean to tell whether or not n is a primtive root
            ArrayList<Integer> distinct_values = new ArrayList<Integer>();  // ArrayList to hold the distinct values of a candidate primitive root modulo the prime 
            
            // loop to check if n is a primitive root by raising it to the power of x modulo the prime
            for (int x = 2; x < prime - 2; x++) {

                // setting is_n_a_primtive_root to false and breaking out of the loop if n to the power of x modulo the prime is already in the distinct_values ArrayList
                if (distinct_values.contains((n^x) % prime)) {
                    is_n_a_primtive_root = false;
                    System.out.println(n + " is not a primitive root");
                    break;
                }
                else {
                    distinct_values.add((n^x) % prime);
                }
            }
            // adding n to the list of primitive roots if it is a primitive root of the prime passed to the function
            if (is_n_a_primtive_root) {
                // primitive_roots.add(n);
                return n;
            }
        }

        // selecting a random value in the list of primitive roots to be our primtitive root
        // int random_primitive_root = primitive_roots.get((int) (Math.random() * (primitive_roots.size() - 1))); 
        // int random_primitive_root = -1;
        // return random_primitive_root;
        //
        // returning -1 if no primitive root found
        return -1;
    }
}
