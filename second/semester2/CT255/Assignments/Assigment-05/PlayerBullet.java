import java.awt.*;
import javax.swing.*;

public class PlayerBullet extends Sprite2D {
    public PlayerBullet(Player player, Image image) {
        super(image);
        x = player.getX() + 54/2; 
        y = player.getY();
    }

    // method to move the bullet up each frame
    public void move() {
        y = y - 10;
    }
}
