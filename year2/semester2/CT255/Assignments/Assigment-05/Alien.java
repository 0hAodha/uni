import java.awt.*;
import javax.swing.*;

public class Alien extends Sprite2D {
    public static long framesDrawn = 0;    // variable to hold the number of frames drawn
    public static int aliensKilled = 0;     // static variable to hold the count of the aliens killed - in alien class as it's intended to be modified by aliens
    public static int stepSize = 0;         // how far the aliens move each frame - 0 by default but will get increased to 5 immediately
    private Image altImage;

    public Alien(Image image, Image altImage) {
        super(image);
        this.altImage = altImage;
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
        y += 25;
    }

    // overridden paint method to allow animation
    @Override 
    public void paint(Graphics g) {
        framesDrawn++;
        
        if (framesDrawn % 100 < 50) {
            g.drawImage(image, x, y, null);
        }
        else {
            g.drawImage(altImage, x, y, null);
        }
    }

    @Override 
    // overridden kill method to set isAlive to false and increment the aliensKilled counter
    public void kill() {
        isAlive = false;
        aliensKilled++;
    }

    // method to increase the stepsize of the aliens
    public static void speedUp() {
        stepSize = stepSize + 3;
    } 
}
