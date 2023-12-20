import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {
    // member data
    private static String workingDirectory = System.getProperty("user.dir");
    
    private Image alienImage;
    private Image playerImage;

    private static final Dimension WindowSize = new Dimension(800, 600); 
    private static final int NUMALIENS = 30; 
    private Alien[] AliensArray = new Alien[NUMALIENS]; 
    boolean right = true;   // boolean to tell which direction the aliens are moving
    private Player player;

    private BufferStrategy strategy; 

    // variable to hold how far the player should be moving in the x axis per frame - default 0. 
    private int dx = 0; 

    // constructor 
    public InvadersApplication() {
        // set up window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // adding a key listener
        addKeyListener(this);

        // display the window. centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
        int x = screensize.width/2  - WindowSize.width/2; int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);

        // load image from disk 
        ImageIcon alienIcon = new ImageIcon(workingDirectory + "/alien_ship_1.png");
        ImageIcon playerIcon = new ImageIcon(workingDirectory + "/player_ship.png");

        alienImage = alienIcon.getImage();
        playerImage = playerIcon.getImage();
        
        // initial x and y coordinates for the aliens
        int alienx = 200; 
        int alieny = 25;
        int column = 0;     // keeps track of which column the alien is in 
        for (int i = 0; i < NUMALIENS; i++) {
            AliensArray[i] = new Alien(alienImage);
            AliensArray[i].setPosition(alienx, alieny);

            alienx += 60;
            column++;

            // go onto a new line every 5 aliens
            if (column >= 6) {
                column = 0;
                alienx = 200; 
                alieny += 60;
            }
        }

        // creating a player icon & setting it's position 
        player = new Player(playerImage);
        player.setPosition(270, 550);

        setVisible(true);

        createBufferStrategy(2); 
        strategy = getBufferStrategy(); 

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
        g = strategy.getDrawGraphics(); 
        // draw a black rectangle on the whole canvas 
        g.setColor(Color.BLACK); 
        g.fillRect(0, 0, 800, 600);


        // iterating through each sprite, moving it, and calling it's paint method 
        for (Alien a : AliensArray) {
            a.move(right);
            a.paint(g);
        }

        // checking if any of the aliens hit the edge when they were moved 
        for (Alien a : AliensArray) {
            // changing direction & moving down if edge is hit 
            if (a.getx() > 750) {
                right = false; 

                // looping through all the aliens and moving them down
                for (Alien alien : AliensArray) {
                    alien.moveDown(); 
                }

                break;
            }
            else if (a.getx() < 0) {
                right = true;

                // looping through all the aliens and moving them down
                for (Alien alien : AliensArray) {
                    alien.moveDown(); 
                }

                break;
            }
        }

        // moving the player dx pixels - should be 0 if no key is being pressed 
        player.move(dx); 

        // calling the Player's paint method 
        player.paint(g);

        strategy.show();
    }

    // application entry point 
    public static void main(String[] args) {
        InvadersApplication ia = new InvadersApplication(); 
    }
}
