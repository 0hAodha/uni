import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameObject {
    // member data
    private int x, y; 
    private int height = 50, width = 50;
    private Color c;
    private int stepSize = 10;  // how many pixels the object will move at a time

    // constructor 
    public GameObject() {
        // randomly generating colour
        c = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));

        // randomly generating initial x y co-ordinates
        x = (int)(Math.random()*600);
        y = (int)(Math.random()*600);
    }

    // public interface 
    public void move() {
        // generating either a 1 or a 0 to determine if the square should move left or right and up or down - 1 = left/up, 0 = right/down
        int xDirection = (int)(Math.random()*2);
        int yDirection = (int)(Math.random()*2);

        // changing the x & y co-ordinates by either plus or minus the stepSize, depending on the direction
        x += (xDirection == 1) ? -stepSize : + stepSize; 
        y += (yDirection == 1) ? -stepSize : + stepSize; 
    } 

    public void paint(Graphics g) {
        // setting colour
        g.setColor(c);

        // make a filled rectangle with the specified dimensions at the specified x, y co-ordinates
        g.fillRect(x, y, height, width);
    }
}

