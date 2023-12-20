import java.awt.*;
import javax.swing.*;

public class Player extends Sprite2D {
    public Player(Image image) {
        super(image);
    }

    // method to move the player by the supplied distance
    public void move(int distance) {
        x += distance; // x is inherited from the Sprite2D superclass
    }
}
