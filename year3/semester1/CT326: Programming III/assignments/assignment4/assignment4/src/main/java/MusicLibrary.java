// Name: Andrew Hayes
// Student ID: 21321503

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Application class for the Music Library application
 */
public class MusicLibrary extends JFrame {
    private String libraryDirectory = "resources/";         // directory that the library files are in
    private String libraryFileName =  "music_library.txt";  // name of the music libary file
    private ArrayList<Album> albums = new ArrayList<>();
    private ActionListener actionListener;                  // action listener for the buttons in this application

    /**
     * constructor for the Music Library application
     */
    public MusicLibrary() {
        super("Music Library");    // set window title

        // make JFrame fill the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        // using border layout to position JPanels easily
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // panel to show the list of albums
        JPanel albumPanel = new JPanel();

        // action listener to handle what happens when buttons are clicked
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // if the actionCommand was to show the albumPanel (i.e., "back" button was pressed on the trackPanel)
                if (event.getActionCommand().equals("showAlbumPanel")) {
                    // removing everything from the container
                    container.removeAll();

                    // cleaning screen
                    container.revalidate();
                    container.repaint();

                    // adding albumPanel to container
                    container.add(albumPanel, BorderLayout.CENTER);
                }
                // else if the actionCommand was anything other than showAlbumPanel, i.e. it was an album being clicked, hide it and display the tracklist of the selected album
                else {
                    // hiding the albumPanel
                    container.remove(albumPanel);

                    // cleaning screen
                    container.revalidate();
                    container.repaint();

                    // displaying album contents (trackPanel)
                    container.add(generateTrackPanel(albums.get(Integer.parseInt(event.getActionCommand()))), BorderLayout.CENTER);
                }
            }
        };

        // loading the albums into the arraylist
        try {
            albums = loadAlbums(libraryDirectory + libraryFileName);
        }
        // display error message if the music library file could not be read successfully
        catch (IOException e) {
            albumPanel.add(new JLabel("The music library file could not be read successfully!!!"));
        }

        // iterating over each album and adding it to the GUI interface as a button
        for (int i =  0; i < albums.size(); i++) {  // using iteration instead of for-each so that we can set the actionCommand of the button to the value of i
            Album album = albums.get(i);

            // attempting to create a button with the album cover as the icon
            try {
                // reading in image, scaling it, turning it into an icond, and adding it to the album button
                Image coverImage = ImageIO.read(new File(libraryDirectory + album.getPathToCover())).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(coverImage);
                JButton button = new JButton(album.getTitle(), icon);   // including album title in button to make it easier to use
                // JButton button = new JButton(icon);                  // would give same appearancec as in assignment spec

                // make clicking the button use an action command that references the album's position in the arraylist
                button.setActionCommand(String.valueOf(i));
                button.addActionListener(actionListener);

                albumPanel.add(button);
            }
            // if the album cover cannot be found, just put a text button in its place
            catch (IOException e) {
                JButton button = new JButton(album.getTitle());

                // make clicking the button use an action command that references the album's position in the arraylist
                button.setActionCommand(String.valueOf(i));
                button.addActionListener(actionListener);

                albumPanel.add(button);
            }
        }

        // display the album panel
        container.add(albumPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * method to load all the Albums into memory as an ArrayList of Albums
     * @param libraryPath String representing the file path to the music_library.txt file
     * @throws IOException if reading the albums in from the library file and/or instantiating them fails
     */
    public ArrayList<Album> loadAlbums(String libraryPath) throws IOException {
        // it's not really necessary that libraryPath be passed to this method from a design perspective, as they are both members of the MusicLibrary object class
        // however, this makes the method more re-usable if we ever want to load albums from outside of this class

        ArrayList<Album> albums = new ArrayList<>();    // ArrayList of Album objects to be returned

        // instantiating an Album object for each line in the library file
        BufferedReader reader = new BufferedReader(new FileReader(libraryPath));    // not catching FileNotFoundException as entire method should fail if the library file is not accessible
        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");

            // creating the album object and adding it to the ArrayList
            albums.add(new Album(columns[0].trim(), columns[1].trim(), columns[2].trim(), libraryDirectory + columns[3].trim())); // trimming whitespace from the ends of each column read in
        }

        return albums;
    }

    /**
     * method to generate a JPanel containing a JTable of all the tracks for a given Album
     * @param album the Album for which we are generating a JTable of tracks
     * @return a JPanel containing a JTable of all the tracks for a given Album
     */
    public JPanel generateTrackPanel(Album album) {
        JPanel trackPanel = new JPanel(new BorderLayout()); // using border layout so the table can be easily aligned above the "back" button

        ArrayList<Track> tracks = album.getTracks();

        // columns to be displayed in JTable
        String[][] data = new String[tracks.size()][3];

        // filling up columns to be displayed in JTable
        for (int i = 0; i < tracks.size(); i++) {
            // track number, track title, and track duration
            data[i] = new String[]{tracks.get(i).getTrackNumber(), tracks.get(i).getTitle(), tracks.get(i).getDuration()};
        }

        // adding the JTable of tracks to the center of the panel
        String[] columnNames = {"No.", "Track Title", "Length"};
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);    // disable user interaction with the table so they can't edit it
        trackPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton button = new JButton("Back");

        // make clicking the button use an action command that references the album's position in the arraylist
        button.addActionListener(actionListener);
        button.setActionCommand("showAlbumPanel");

        // adding the back button to the bottom of the panel, underneath the table
        trackPanel.add(button, BorderLayout.SOUTH);

        return trackPanel;
    }

    /**
     * main method
     * @param args (none)
     */
    public static void main(String[] args) {
        MusicLibrary application = new MusicLibrary();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
