import java.awt.*;
import javax.swing.*;
import java.util.*;

public class MovingSquaresApplication extends JFrame implements Runnable {
    // window dimensions
    private static final Dimension WindowSize = new Dimension(600,600);

    // array of gameobjects (squares)
    private GameObject gameobjects[] = new GameObject[100]; 

    // constructor
    public MovingSquaresApplication() {
        // create & set up the window 
        this.setTitle("Moving Squares Apllication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // display the window. centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2  - WindowSize.width/2;
        int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);

        // create array of game objects. 
        //Arrays.fill(gameobjects, 100);
        for (int i = 0; i < 100; i++) {
            gameobjects[i] = new GameObject();
        }

        // creating a new thread & starting it
        Thread t = new Thread(this); 
        t.start();
    }

    // run method called by thread
    public void run() {
        while (true) {
            // iterating over array of gameobjects & calling move() on each object 
            for (GameObject go : gameobjects) {
                go.move();
            }

            try {
                // sleeping
                Thread.sleep(100);

            // catching exception
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        // repainting now that every object has been moved
        this.repaint();
        }

    }

    // paint method
    public void paint(Graphics g) {
        // filling the screen with a white square between "frames" of animation to get rid of trails caused by moving objects
        Color c = new Color(255, 255, 255);
        g.setColor(c);
        g.fillRect(0, 0, 1000, 1000);

        // iterating over each GameObject and calling paint on it 
        for (GameObject go : gameobjects) {
            go.paint(g);
        }
    }

    public static void main(String[] args) {
        // instantiating class in main method to begin application
        MovingSquaresApplication w = new MovingSquaresApplication();
    }
}
