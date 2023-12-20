// package MyApplication;

import java.awt.*;
import javax.swing.*;


public class MyApplication extends JFrame {
    private static final Dimension WindowSize = new Dimension(600,600);

    public MyApplication() {
        // create & set up the window 
        this.setTitle("Pacman, or something...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // display the window. centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2  - WindowSize.width/2;
        int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);
    }

    public static void main(String[] args) {
        MyApplication w = new MyApplication();
    }

    public void paint(Graphics g) {
        int height = 50, width = 50; // defining the height & width of the rectangle
        int x      = 10, y     = 10; // defining the vertical & horizontal position between rectangles
        
        // looping for each of the 10 rows in the window
        for (int row = 0; row < 10; row++) {
    
            // looping for each of the 10 columns in the window
            for (int column = 0; column < 10; column++) {

                // randomly generating an RGB colour & setting the graphic to use it
                Color c = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
                g.setColor(c);

                // make a filled rectangle with the specified dimensions at the specified x, y co-ordinates
                g.fillRect(x, y, height, width);
            

                // increasing the x by adding the size of a square plus the desired padding
                x += width + 10;
            }

            // resetting the x value to 10 for the next row
            x = 10;

            // increasing the y by adding the height of a square plus the desired padding
            y += height + 10;
        }
    }
}
