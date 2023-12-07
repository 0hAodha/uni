import java.awt.*;
import javax.swing.*;

public class Sprite2D {
    // member data
    protected int x,y;
    protected Image image;
    public boolean isAlive; // boolean to tell if the sprite is alive or not

    public Sprite2D(Image image) {
        this.image = image;
        this.isAlive = true;
    }

    // paint method
    public void paint(Graphics g) {
        // draw the image 
        g.drawImage(image, x, y, null);
    }

    // set the position of the object
    public void setPosition(int x, int y) {
        this.x = x; 
        this.y = y;
    }

    // getter method for the sprite's x position 
    public int getX() {
        return x;
    }

    // getter method for the sprite's y position 
    public int getY() {
        return y;
    }

    // method to set isAlive to false 
    public void kill() {
        isAlive = false;
    }
}
