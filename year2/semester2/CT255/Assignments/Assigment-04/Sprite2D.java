import java.awt.*;
import javax.swing.*;

public class Sprite2D {
    // member data
    protected int x,y;
    private Image image;

    public Sprite2D(Image image) {
        this.image = image;
    }

    // paint method
    public void paint(Graphics g) {
        // draw the image 
        g.drawImage(image, x, y, null);
    }

    // set the position of the object
    public void setPosition(int x, int y) {
        this.x = x; 
        this.y = y; }
}
