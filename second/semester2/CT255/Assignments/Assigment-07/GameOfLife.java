import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class GameOfLife extends JFrame implements Runnable, MouseListener {
    private static final Dimension WindowSize = new Dimension(800, 800); 
    private BufferStrategy strategy;
    private static boolean isGraphicsInitialised = false; 
    private boolean gameState[][][] = new boolean[40][40][2];
    private static boolean isPlaying = false;

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // display the window. centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
        int x = screensize.width/2  - WindowSize.width/2; int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);

        createBufferStrategy(2); 
        strategy = getBufferStrategy(); 

        // create a new thread & start it 
        Thread t = new Thread(this);
        t.start();

        // adding mouse listener 
        addMouseListener(this);

        isGraphicsInitialised = true;
    }

    // entry point of the application
    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
    }

    public void run() {
        while (true) {
            // repainting 
            try {
                this.repaint();
                Thread.sleep(200); 
            } catch (InterruptedException e) {
                e.printStackTrace(); 
            } 
        }
    }

    // application's paint method
    public void paint(Graphics g) {
        // making sure that the graphics are initialised before painting
        if (isGraphicsInitialised) {
            g = strategy.getDrawGraphics(); 
            
            // draw a white rectangle on the whole canvas 
            g.setColor(Color.BLACK); 
            g.fillRect(0, 0, WindowSize.width, WindowSize.height);

            // looping through the gameState array
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {

                    // drawing a white square if true
                    if (gameState[i][j][0]) {
                        g.setColor(Color.WHITE); 
                        g.fillRect(i * 20, j * 20, 20, 20);
                    }
                }
            }
            
            if (isPlaying) {
                for (int x = 0; x < 40; x++) {
                    for (int y = 0; y < 40; y++) {
                        // count the live neighbours of cell [x][y][0]
                        int numLiveNeighbours = 0;

                        for (int xx = -1 ; xx <= 1; xx++) {
                            for (int yy = -1; yy <= 1; yy++) {
                                if (xx != 0 || yy != 0) {
                                    // check cell [x+xx][y+yy][0]
                                    // if x+xx or y+yy is out of bounds, getting the inverse of it modulo 40
                                    int newX = (x+xx >= 0) ? (x+xx) % 40 : 40 + x+xx; 
                                    int newY = (y+yy >= 0) ? (y+yy) % 40 : 40 + y+yy; 

                                    if (gameState[newX][newY][0]) {
                                        numLiveNeighbours++;
                                    }
                                }
                            }
                        }

                        // killing the cell if it is alive and has fewer than two live neighbours or greater than 3 live neighbours
                        if (gameState[x][y][0] && (numLiveNeighbours < 2 || numLiveNeighbours > 3)) {
                            gameState[x][y][1] = false;
                        }
                        // bringing the cell back to life it is dead and has exactly 3 live neighbours
                        else if (!gameState[x][y][0] && numLiveNeighbours == 3) {
                            gameState[x][y][1] = true;
                        }
                    }      
                }

                // making the "front buffer" equal to the "back buffer"
                for (int x = 0; x < 40; x++) {
                    for (int y = 0; y < 40; y++) {
                        gameState[x][y][0] = gameState[x][y][1];
                    }
                }
            }
            // if the game is not being played, drawing the buttons
            else {
                Font f = new Font("Times", Font.PLAIN, 12);
                g.setFont(f); 
                FontMetrics fm = getFontMetrics(f); 
                
                // drawing the two buttons
                g.setColor(Color.GREEN); 

                g.fillRect(20, 20, 60, 20);
                g.fillRect(100, 20, 60, 20);

                g.setColor(Color.BLACK);
                g.drawString("Start", 35, 35);
                g.drawString("Random", 105, 35);
            }

            strategy.show();
        }
    }

    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

    // mouse click event
    public void mouseClicked(MouseEvent e) {
        // if (!isPlaying) {
            // if start button was pressed, switching to the playing game state
            if ((e.getX() >= 20 && e.getX() <= 80) && (e.getY() >= 20 && e.getY() <= 40)) {
                isPlaying = true;
            }
            // else if the random button was pressed, calling the random method
            else if ((e.getX() >= 100 && e.getX() <= 160) && (e.getY() >= 20 && e.getY() <= 40)) {
                random();
            }
            // else, setting the clicked square to true (white)
            else {
                // setting boolean corresponding to the square that was clicked to true
                gameState[(int) Math.floor(e.getX() / 20)][(int) Math.floor(e.getY() / 20)][0] = true;
            }
        // }
    }

    // method to generate a random starting state
    public void random() {
        // looping through the gameState array
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                // setting the square to alive with a probability of 1 in 10
                if (((int) (Math.random() * 10)) == 1) {
                    gameState[i][j][0] = true;
                }
            }
        }
    }
}
