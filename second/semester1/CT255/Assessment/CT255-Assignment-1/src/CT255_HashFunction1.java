//package ct255;

import java.util.Random;

public class CT255_HashFunction1 {

    public static void main(String[] args) {
        int res = 0;

        if (args != null && args.length > 0) {  // Check for <input> value
            res = hashF1(args[0]);              // call hash function with <input>
            if (res < 0) {                      // Error
                System.out.println("Error: <input> must be 1 to 64 characters long.");
            }
            else {
                System.out.println("input = " + args[0] + " : Hash = " + res);


                System.out.println("Start searching for collisions");
                int collisionCount = 0; // variable to count the number of collisions found

                // string containing all possible ASCII characters (exclusding control characters such as [NULL])
                String allChars = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

                for (int i = 0; i < 100000; i++) {  // checking one million randomly generated strings for hash collisions
                    StringBuilder str = new StringBuilder();    // creating a new StringBuilder to build char by char

                    // generating 64 random characters & appending them to str
                    for (int j = 0; j < 64; j++) {
                        char c = allChars.charAt(new Random().nextInt(allChars.length()));  // selecting a random character from allChars
                        str.append(c);  // appending the randomly selected character to str
                    }

                    // hashing str & checking if the hash matches res
                    if (hashF1(str.toString()) == res) {
                        collisionCount++;   // iterating collisionCount if collision found
                        System.out.println(str);    // printing the String that generated the collision
                    }
                }

                // printing the number of collisions found
                System.out.printf("%d hash collisions found!", collisionCount);
            }
        }
        else { // No <input> 
            System.out.println("Use: CT255_HashFunction1 <Input>");
        } 
    }
        
    private static int hashF1(String s){
        int ret = -1, i;
        int[] hashA = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};  // i extended this array by 6 indices to make code more robust
        // essentially, the extent of my improvements was just increasing the size of the hashA array

        String filler, sIn;
        
        filler = new String("ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH");
        
        if ((s.length() > 64) || (s.length() < 1)) { // String does not have required length
            ret = -1;
        }
        else {
            sIn = s + filler; // Add characters, now have "<input>HABCDEF..."
            sIn = sIn.substring(0, 64); // // Limit string to first 64 characters
            // System.out.println(sIn); // FYI
            for (i = 0; i < sIn.length(); i++){
                char byPos = sIn.charAt(i); // get ith character
                hashA[0] += (byPos * 17); // Note: A += B means A = A + B
                hashA[1] += (byPos * 31);
                hashA[2] += (byPos * 101);
                hashA[3] += (byPos * 79);
                hashA[4] += byPos * 83;
                hashA[5] += byPos * 89;
                hashA[6] += byPos * 103;
                hashA[7] += byPos * 107;
                hashA[8] += byPos * 109;
                hashA[9] += byPos * 113;
            } 
            
            hashA[0] %= 255;  // % is the modulus operation, i.e. division with rest
            hashA[1] %= 255;
            hashA[2] %= 255;
            hashA[3] %= 255;
            hashA[4] %= 255;
            hashA[5] %= 255;
            hashA[6] %= 255;
            hashA[7] %= 255;
            hashA[8] %= 255;
            hashA[9] %= 255;

            ret = hashA[0] + (hashA[1] * 256) + (hashA[2] * 256 * 256) + (hashA[3] * 256 * 256 * 256) + (hashA[4] * 256*256*256*256) + (hashA[5] * 256*256*256*256*256) + (hashA[6] * 256*236*256*256*256*256)
                    + (hashA[6] * 256*256*256*256*256*256) + (hashA[7] * 256*256*256*256*256*256*256) + (hashA[8] * 256*256*256*256*256*256*256*256) + (hashA[9] * 256*256*256*256*256*256*256*256*256);
            if (ret < 0) ret *= -1;
        }
        return ret;
    }
}
