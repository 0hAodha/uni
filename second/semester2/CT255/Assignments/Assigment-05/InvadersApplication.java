import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {
    // member data
    private static String workingDirectory = System.getProperty("user.dir");
    private static boolean isGraphicsInitialised = false; 
    
    private Image alienImage;
    private Image altAlienImage;
    private Image playerImage;
    private Image bulletImage;

    private static final Dimension WindowSize = new Dimension(800, 600); 
    
    private static final int NUMALIENS = 30; 
    private Alien[] aliensArray = new Alien[NUMALIENS]; // arraylist of all the currently extant bullets
    private ArrayList<PlayerBullet> bullets = new ArrayList<PlayerBullet>();

    boolean right = true;   // boolean to tell which direction the aliens are moving
    private Player player;

    private BufferStrategy strategy; 

    // variable to hold how far the player should be moving in the x axis per frame - default 0. 
    private int dx = 0; 

    public boolean gameOver = false;    // boolean to tell what state the game is in 
    private long score = 0;             // player's score
    private long bestScore = 0;         // the best score the player has achieved

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
        ImageIcon altAlienIcon = new ImageIcon(workingDirectory + "/alien_ship_2.png");
        ImageIcon playerIcon = new ImageIcon(workingDirectory + "/player_ship.png");
        ImageIcon bulletIcon = new ImageIcon(workingDirectory + "/bullet.png");

        alienImage = alienIcon.getImage();
        altAlienImage = altAlienIcon.getImage();
        playerImage = playerIcon.getImage();
        bulletImage = bulletIcon.getImage();
        
        // creating a player icon & setting it's position 
        player = new Player(playerImage);
        player.setPosition(270, 550);

        setVisible(true);

        createBufferStrategy(2); 
        strategy = getBufferStrategy(); 

        // create a new thread & start it 
        Thread t = new Thread(this);
        t.start();

        isGraphicsInitialised = true;
    }

    // thread's entry point
    public void run() {
        while (true) {
            // repainting 
            try {
                this.repaint();
                Thread.sleep(20); 
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            } 
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // what to do on keypress if the player is alive (game is being played)
        if (player.isAlive) {
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
        // what to do on keypress if the player is not alive (game is over)
        else {
            // resetting everything so that the game can be restarted
            resetAll();
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

        // if space key released "firing" a bullet (creating one) 
        if (key == KeyEvent.VK_SPACE) { // firing bullets on release so that the player can't just hold down space to fire
            bullets.add(new PlayerBullet(player, bulletImage)); 
        }
    }

    @Override 
    public void keyTyped(KeyEvent e) {
    }

    // method to reset everything so that a new game may be started 
    public void resetAll() {
        Alien.framesDrawn = 0;    
        Alien.aliensKilled = 0;
        Alien.stepSize = 0;
        newWave();
        //aliensArray = null;

        player.setPosition(270, 550);
        player.isAlive = true;
    } 

    // gameplay method 
    public void gameplay(Graphics g) {
        // checking if any of the aliens hit the edge when they were moved 
        for (Alien a : aliensArray) {
            // changing direction & moving down if edge is hit 
            if (a.getX() > 750) {
                right = false; 

                // looping through all the aliens and moving them down
                for (Alien alien : aliensArray) {
                    alien.moveDown(); 
                }

                break;
            }
            else if (a.getX() < 0) {
                right = true;

                // looping through all the aliens and moving them down
                for (Alien alien : aliensArray) {
                    alien.moveDown(); 
                }

                break;
            }
        }

        // looping through each bullet, moving it, and calling its paint method if it has not collided, removing it and the alien it collided with if it has
        // using an iterator so that bullets can be removed from the list as we loop through
        Iterator bulletsIterator = bullets.iterator(); 
        while (bulletsIterator.hasNext()) {
            PlayerBullet b = (PlayerBullet) bulletsIterator.next(); 
            b.move(); 

            // bullets will be removed to save memory, but aliens will not be removed until a wave is over to preserve correct movement
            for (Alien a : aliensArray) {
                // only checking for collisions if the alien is alive
                if (a.isAlive) {
                    // if the bullet collides with an alien, removing the bullet & hiding the alien
                    // the aliens dimensions are 50x32
                    // the bullets dimensions are 6x16
                    if (
                            ( (a.getX() < b.getX() && a.getX()+50 > b.getX()) || (b.getX() < a.getX() && b.getX()+6 > a.getX()) ) && 
                            ( (a.getY() < b.getY() && a.getY()+32 > b.getY()) || (b.getY() < a.getY() && b.getY()+16 > a.getY()))
                       ) {
                        bulletsIterator.remove();
                        a.kill();
                        score = score + 10;     // increasing the score once the alien is killed
                        b.kill();   // "killing" the bullet so it's not painted before it's been removed
                    }
                }
            }

            // painting the bullet if it is "alive"
            if (b.isAlive) {
                b.paint(g);
            }
        }

        // looping through each alien to see if it has collided with the player
        for (Alien a : aliensArray) {
            // checking to see if any of the (alive) aliens have collided with the player
            if (a.isAlive) {
                if (
                        // the player's dimensions are 54 x 32
                        ( (a.getX() < player.getX() && a.getX()+50 > player.getX()) || (player.getX() < a.getX() && player.getX()+54 > a.getX()) )
                        && 
                        ( (a.getY() < player.getY() && a.getY()+32 > player.getY()) || (player.getY() < a.getY() && player.getY()+32 > a.getY()))
                   ) {
                    player.kill();   // "killing" the player
                    return;
                }
            }
        }

        // moving the player dx pixels - should be 0 if no key is being pressed 
        player.move(dx); 

        // iterating through each sprite, moving it, and calling its paint method 
        for (Alien a : aliensArray) {
            a.move(right);
            
            // only painting the alien if it is alive
            if (a.isAlive) {
                a.paint(g);
            }
        }

        // calling the Player's paint method 
        player.paint(g);
    }

    // application's paint method
    public void paint(Graphics g) {
        // making sure that the graphics are initialised before painting
        if (isGraphicsInitialised) {
            g = strategy.getDrawGraphics(); 
            // draw a black rectangle on the whole canvas 
            g.setColor(Color.BLACK); 
            g.fillRect(0, 0, 800, 600);
            Font f = new Font("Times", Font.PLAIN, 50); 
            g.setColor(Color.WHITE); 

            // calculating score 
            score = Alien.aliensKilled * 10;

            // displaying score and best score 
            String scorebar = "Score: " + score + "   Best: " + bestScore;
            g.drawString(scorebar, 50, 50);
            

            // if the game is not over, playing the game. else, displaying a gameover screen.
            if (player.isAlive) {
                // checking if all the aliens are dead, and if so, spawning a new wave
                boolean noAliens = true;
                for (Alien a : aliensArray) {
                    if (a != null && a.isAlive) {
                        noAliens = false;
                        break;
                    }
                }
                if (noAliens) {
                    newWave();
                }
                gameplay(g);
            }
            else {
                if (score > bestScore) {
                    bestScore = score;
                }
                String scores = "Score: " + score + "  Best Score: " + bestScore;
                g.drawString("GAME OVER", 400, 300);
                g.drawString(scores, 400, 350);
                g.drawString("Press any key to continue", 400, 400);
            }

            strategy.show();
        }
    }

    // method to spawn a new wave of aliens
    public void newWave() {
        // deleting any existing bullets so the aliens don't spawn on top of them
        bullets.clear();

        // speeding up the aliens
        Alien.speedUp();

        // initial x and y coordinates for the aliens
        int alienx = 200; 
        int alieny = 25;
        int column = 0;     // keeps track of which column the alien is in 
        for (int i = 0; i < NUMALIENS; i++) {
            aliensArray[i] = new Alien(alienImage, altAlienImage);
            aliensArray[i].setPosition(alienx, alieny);

            alienx += 60;
            column++;

            // go onto a new line every 5 aliens
            if (column >= 6) {
                column = 0;
                alienx = 200; 
                alieny += 60;
            }
        }
    }

    // application entry point 
    public static void main(String[] args) {
        InvadersApplication ia = new InvadersApplication(); 
    }
}
