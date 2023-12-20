import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;

public class GameOfLife extends JFrame implements Runnable, MouseListener {
    private static final Dimension WindowSize = new Dimension(800, 800); 
    private BufferStrategy strategy;
    private static boolean isGraphicsInitialised = false; 
    private boolean gameState[][] = new boolean[40][40];

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
                Thread.sleep(20); 
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
            g.setColor(Color.WHITE); 
            g.fillRect(0, 0, 800, 800);

            // looping through the gameState array
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {

                    // drawing a black square if true
                    if (gameState[i][j]) {
                        g.setColor(Color.BLACK); 
                        g.fillRect(i * 20, j * 20, 20, 20);
                    }
                }
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
        // setting boolean corresponding to the square that was clicked to true
        gameState[(int) Math.floor(e.getX() / 20)][(int) Math.floor(e.getY() / 20)] = true;
    }
}
