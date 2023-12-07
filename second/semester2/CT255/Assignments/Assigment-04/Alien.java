import java.awt.*;
import javax.swing.*;

public class Alien extends Sprite2D {
    private int stepSize = 2;

    public Alien(Image image) {
        super(image);
    }

    // method to randomly move the alien
    public void move(boolean right) {
        // changing the x & y co-ordinates (inherited from superclass) by either plus or minus the stepSize, depending on the direction
        if (right) {
            x += stepSize;
        }
        else {
            x -= stepSize;
        }
    } 

    // method to move aliend down 
    public void moveDown() {
        y += 10;
    }

    // getter method for the alien's x coordinate 
    public int getx() {
        return x; 
    }
}
