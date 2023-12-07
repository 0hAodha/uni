import java.awt.*;
import javax.swing.*;

public class Cave extends JFrame implements Runnable {
    private static final Dimension WindowSize = new Dimension(800, 800); 
    private static boolean isGraphicsInitialised = false; 
    private boolean map[][][] = new boolean[200][200][2];

    public Cave() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // randomly deciding whether each position in the map is a wall (true) or floor (false)
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                // setting the square to wall with a probability of 60%
                map[i][j][0] = Math.random() < 0.6;
            }
        }

        // display the window. centred on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); 
        int x = screensize.width/2  - WindowSize.width/2; int y = screensize.height/2 - WindowSize.height/2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);

        // create a new thread & start it 
        Thread t = new Thread(this);
        t.start();

        isGraphicsInitialised = true;
    }

    public void run() {
        if (isGraphicsInitialised) {
            // looping 5 times to show the initial state also, but re-running the cave procedure 4 times
            for (int i = 0; i <= 4; i++){
                // repainting 
                try {
                    this.repaint();
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace(); 
                } 
            }
        }
    }

    public void paint (Graphics g) {
        // draw a white rectangle on the whole canvas 
        g.setColor(Color.BLACK); 
        g.fillRect(0, 0, WindowSize.width, WindowSize.height);

        // looping through map and drawing a white square if floor (true)
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                // drawing a white square if true
                if (map[i][j][0]) {
                    g.setColor(Color.WHITE); 
                    g.fillRect(i * 4, j * 4, 4, 4);
                }
            }
        }

        // looping through array to  make clumping happen
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                // count the wall neighbours of cell [x][y]
                int numWallNeighbours = 0;

                // looping through neighbour cells
                for (int xx = -1 ; xx <= 1; xx++) {
                    for (int yy = -1; yy <= 1; yy++) {
                        if (xx != 0 || yy != 0) {
                            // check cell [x+xx][y+yy]()
                            // if x+xx or y+yy is out of bounds, getting the inverse of it modulo 200
                            int newX = (x+xx >= 0) ? (x+xx) % 200 : 200 + x+xx; 
                            int newY = (y+yy >= 0) ? (y+yy) % 200 : 200 + y+yy; 

                            // if the neighbour is a wall, increasing the count
                            if (map[newX][newY][0]) {
                                numWallNeighbours++;
                            }
                        }
                    }
                }

                // defining each cell that has at least 5 neighbouring wall cells as a wall itself
                if (numWallNeighbours >= 5) {
                    map[x][y][1] = true;
                }
                // otherwise defining it as a floor
                else {
                    map[x][y][1] = false;
                }
            }
        }

        // moving back buffer to front
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                map[x][y][0] = map[x][y][1];
            }
        }

    }

    public static void main(String[] args) {
        Cave cave = new Cave();
    }
}
