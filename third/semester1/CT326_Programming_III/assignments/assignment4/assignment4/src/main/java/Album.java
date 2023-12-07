// Name: Andrew Hayes
// Student ID: 21321503

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class to represent an Album object and its tracks
 */
public class Album {
    private String artist;          // the name of the artist
    private String title;           // the title of the album
    private String pathToCover;     // the path to the cover image of the album
    private String pathToTrackList; // the path to the track list text file for the album
    private ArrayList<Track> tracks = new ArrayList<>();    // arraylist of all the tracks on the album

    /**
     * constructor for Album objects
     * @param artist String of the artist's name
     * @param title String of the title of the album
     * @param pathToCover String of the filepath to the cover image
     * @param pathToTrackList String of the filepath to the list of tracks on the album
     */
    public Album(String artist, String title, String pathToCover, String pathToTrackList) throws IOException {
        this.artist = artist;
        this.title = title;
        this.pathToCover = pathToCover;
        this.pathToTrackList = pathToTrackList;

        // reading the trackList file and adding a Track object to the ArrayList for each line in the file
        BufferedReader reader = new BufferedReader(new FileReader(pathToTrackList));    // not catching FileNotFoundException as entire method should fail if the trackList file is not accessible
        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            tracks.add(new Track(columns[0].strip(), columns[1].strip(), columns[2].strip())); // trimming whitespace from the ends of each column read in
        }
    }

    /**
     * getter for the artist's name
     * @return String artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * setter for the artist's name
     * @param artist String of the artist's name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * getter for the title
     * @return String title of the album
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for the title
     * @param title String title of the album
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for pathToCover
     * @return String path to the cover image
     */
    public String getPathToCover() {
        return pathToCover;
    }

    /**
     * setter for pathToCover
     * @param pathToCover String of the filepath to the cover image
     */
    public void setPathToCover(String pathToCover) {
        this.pathToCover = pathToCover;
    }

    /**
     * getter for trackList
     * @return String of the filepath to the trackList
     */
    public String getPathToTrackList() {
        return pathToTrackList;
    }

    /**
     * setter for trackList
     * @param pathToTrackList String of the filepath to the trackLists
     */
    public void setPathToTrackList(String pathToTrackList) {
        this.pathToTrackList = pathToTrackList;
    }

    /**
     * getter for tracks
     * @return Array of Track objects
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * setter for tracks
     * @param tracks array of Track objects
     */
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
