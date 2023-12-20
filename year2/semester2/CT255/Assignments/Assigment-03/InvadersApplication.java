import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {
    // member data
    private static String workingDirectory = System.getProperty("user.dir");
    
    private Image alienImage;
    private Image playerImage;

    private static final Dimension WindowSize = new Dimension(600, 600); 
    private static final int NUMALIENS = 30; 
    private Alien[] AliensArray = new Alien[NUMALIENS]; 
    private Player player;

    // variable to hold how far the player should be moving in the x axis per frame - default 0. 
    private int dx = 0; 

    // constructor 
    public InvadersApplication() {
        // set up window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // display the window. centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); int x = screensize.width/2  - WindowSize.width/2; int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);

        // adding a key listener
        addKeyListener(this);

        // load image from disk 
        ImageIcon alienIcon = new ImageIcon(workingDirectory + "/alien_ship_1.png");
        ImageIcon playerIcon = new ImageIcon(workingDirectory + "/player_ship.png");

        alienImage = alienIcon.getImage();
        playerImage = playerIcon.getImage();
        
        // instantiating an alien for each index in the aliens array
        for (int i = 0; i < NUMALIENS; i++) {
            AliensArray[i] = new Alien(alienImage);

            // generating a random starting position for the alien
            AliensArray[i].setPosition((int) (Math.random()*600), (int) (Math.random()*600));
        }

        // creating a player icon & setting it's position 
        player = new Player(playerImage);
        player.setPosition(270, 550);

        setVisible(true);

        // create a new thread & start it 
        Thread t = new Thread(this);
        t.start();
    }

    // thread's entry point
    public void run() {
        while (true) {
            // repainting 
            this.repaint();

            try {
                Thread.sleep(20); 
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            } 
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // getting the keycode of the event 
        int key = e.getKeyCode();

        // if right key pressed, making the distance to be moved 10px to the right 
        if (key == KeyEvent.VK_RIGHT) {
            dx = 5;
        }
        // if left key pressed, making the distance to be moved 10px to the left
        if (key == KeyEvent.VK_LEFT) {
            dx = -5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // getting the keycode of the event 
        int key = e.getKeyCode();

        // if the key released was the left or right arrow, setting dx back to 0
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
    }

    @Override 
    public void keyTyped(KeyEvent e) {
    }

    // application's paint method
    public void paint(Graphics g) {
        // draw a black rectangle on the whole canvas 
        g.setColor(Color.BLACK); 
        g.fillRect(0, 0, 600, 600);

        // iterating through each sprite, moving it, and calling it's paint method 
        for (Alien a : AliensArray) {
            a.move();
            a.paint(g);
        }

        // moving the player dx pixels - should be 0 if no key is being pressed 
        player.move(dx); 

        // calling the Player's paint method 
        player.paint(g);
    }

    // application entry point 
    public static void main(String[] args) {
        InvadersApplication ia = new InvadersApplication(); 
    }
}
