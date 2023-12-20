import java.util.*; 
import java.io.*;

@SuppressWarnings("unchecked")      // ignoring warnings lol
public class GuessingGame implements Serializable {
    private static BinaryTree<String> tree = new BinaryTree<String>(); 
    private static String input;                        // string to which input will be read
    private static Scanner sc = new Scanner(System.in); // scanner to read in input
    
    public static void main(String[] args) {
        while (1 == 1) {
            // pick the tree that will be used for the game, either from a file or built-in
            loadTree();

            // play the game
            gameplay();

            playAgainLoop: while (1 == 1) {
                System.out.printf("Enter '1' to play again, '2' to quit, or '3' to save the tree to a file\n> ");
                input = sc.nextLine(); 

                switch (input) {
                    case "1":   // going back to start of loop
                        break playAgainLoop; 

                    case "2":
                        System.exit(0);
                        break playAgainLoop;

                    case "3":   // storing the tree, this should not break the loop
                        storeTree();
                        break;

                    default:
                        // loop
                }
            }
        }
    }

    // method that serializes a tree object to a file
    public static void storeTree() {
        // scanning in the name of the file from the user
        System.out.printf("Enter the name of the file that it the tree should be stored as\n> ");
        input = sc.nextLine();

        try {
            // creating output streams
            FileOutputStream fos = new FileOutputStream(input);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // writing the tree object ot the file
            oos.writeObject(tree);

            // closing output streams
            oos.close();
            fos.close();
        }
        // catching IOExceptions
        catch (IOException E) {
            System.out.println(E);
            System.exit(1);
        }
    }

    // method to load a tree, either from a file, from memory, or hardcoded in
    public static void loadTree() {
        // looping until an appropriate choice is made
        loadTreeLoop: while (1 == 1) {
            System.out.printf("Load a tree from a file? If no, then the built-in tree will be used. y/n\n> ");
            input = sc.nextLine(); 

            switch (input) {
                case "y":
                    // looping until valid filename is entered
                    while (1 == 1) {
                        System.out.printf("Enter the file from which the tree should be loaded\n> ");
                        input = sc.nextLine();

                        File treefile = new File(input);

                        // breaking if the file exists
                        if (treefile.exists()) {
                            break;
                        }
                    }

                    try {
                        // creating input streams
                        FileInputStream fis = new FileInputStream(input);
                        ObjectInputStream ois = new ObjectInputStream(fis);

                        // deserializing tree object
                        tree = (BinaryTree<String>) ois.readObject();

                        // closing input streams
                        ois.close();
                        fis.close();
                    }
                    // printing errors and crashing
                    catch(IOException E) {
                        System.out.println(E);
                        System.exit(1);
                    }
                    catch (ClassNotFoundException E) {
                        System.out.println(E);
                        System.exit(1);
                    }

                    break loadTreeLoop; 

                case "n":
                    // if no tree is defined building the default tree
                    if (tree.getRootNode() == null) {
                        // first the leaves
                        BinaryTree<String> cowTree      = new BinaryTree<String>("Is it a cow?");
                        BinaryTree<String> dogTree      = new BinaryTree<String>("Is it a dog?");
                        BinaryTree<String> fishTree     = new BinaryTree<String>("Is it a goldfish?");
                        BinaryTree<String> geckoTree    = new BinaryTree<String>("Is it a gecko?");
                        
                        // Now the subtrees joining leaves:
                        BinaryTree<String> mammalTree    = new BinaryTree<String>("Is it a farm animal?", cowTree, dogTree);
                        BinaryTree<String> notMammalTree = new BinaryTree<String>("Is it a type of fish?", fishTree, geckoTree);

                        // Now the root
                        tree.setTree("Is it a mammal?", mammalTree, notMammalTree);
                    }

                    break loadTreeLoop;

                default:
                    // loop
            }
        }
    }

    public static void gameplay() {
        System.out.println("Enter 'y' for 'yes', 'n' for 'no'");

        BinaryNodeInterface<String> curr = tree.getRootNode();  // current node 

        // looping until a leaf node is reached
        while (!curr.isLeaf()) {
            // looping until an appropriate reply is given by the user
            answerloop: while (1 == 1) {
                // printing the question & scanning in the answer
                System.out.printf(curr.getData() + "\n> ");
                input = sc.nextLine();
                
                switch (input) {
                    case "y":   // continuing via left node if answer to question is yes
                        curr = curr.getLeftChild();
                        break answerloop; 

                    case "n":   // continuing via right node if answer to question is no
                        curr = curr.getRightChild();
                        break answerloop;

                    default:
                        // loop
                }
            }
        }

        // making a guess
        // looping until an appropriate reply is given by the user
        guessloop: while (1 == 1) {
            // printing the question & scanning in the answer
            System.out.printf(curr.getData() + "\n> ");
            input = sc.nextLine();
            
            switch (input) {
                case "y":   // printing a success message if the answer is yes
                    System.out.println("Success! The guess was correct");
                    break guessloop; 

                case "n":   // inserting a new question and putting the two guesses beneath it if wrong
                    System.out.printf("Enter the animal that you were thinking of\n> ");
                    String thinkingOf = sc.nextLine();
                    String wrong = curr.getData();

                    // ask the user for a question to distinguish the wrong guess from the correct answer
                    System.out.printf("Enter a question to distinguish %s from '%s'\n> ", thinkingOf, wrong);
                    String question = sc.nextLine();
                    
                    // replacing the current node with the question to distinguish it  
                    curr.setData(question); 

                    // asking the user for the correct answer to the question for the animal they were thinking of 
                    addNodeLoop: while (1 == 1) {   // looping until an appropriate answer is given
                        System.out.printf("Enter the correct answer to the question that you entered for %s (y or n)\n> ", thinkingOf); 
                        input = sc.nextLine();

                        switch (input) {
                            case "y":   // adding thinkingOf to the left of the question node if the answer is yes
                                curr.setLeftChild(new BinaryNode<String>("Is it a " + thinkingOf + "?"));
                                curr.setRightChild(new BinaryNode<String>(wrong));
                                break addNodeLoop; 

                            case "n":   // adding thinkingOf to the left of the question node if the answer is no
                                curr.setLeftChild(new BinaryNode<String>(wrong));
                                curr.setRightChild(new BinaryNode<String>("Is it a " + thinkingOf + "?"));
                                break addNodeLoop; 

                            default:
                                // loop
                        }
                    }

                    break guessloop;

                default:
                    // loop
            }
        }
    }
}
