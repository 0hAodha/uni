import java.awt.*;
import javax.swing.*;

public class Alien extends Sprite2D {
    private int stepSize = 5;

    public Alien(Image image) {
        super(image);
    }

    // method to randomly move the alien
    public void move() {
        // generating either a 1 or a 0 to determine if the square should move left or right and up or down - 1 = left/up, 0 = right/down
        int xDirection = (int)(Math.random()*2);
        int yDirection = (int)(Math.random()*2);

        // changing the x & y co-ordinates (inherited from superclass) by either plus or minus the stepSize, depending on the direction
        x += (xDirection == 1) ? -stepSize : + stepSize; 
        y += (yDirection == 1) ? -stepSize : + stepSize; 
    } 

}
