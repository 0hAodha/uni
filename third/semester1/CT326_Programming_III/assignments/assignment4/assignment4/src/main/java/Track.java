// Name: Andrew Hayes
// Student ID: 21321503

/**
 * class to represent a Track on an Album
 * not really necessary for this assignmenet, could've just stored tracks as Strings or something, but i feel that this is a more "correct" OOP-like approach
 */
public class Track {
    private String trackNumber; // storing as String as we never manipulate it arithmetically, and allows non-integer numberings such as A1 and B1 for A-sides and B-sides
    private String title;       // title of track
    private String duration;    // duration can also be stored as String as we never manipulate it. if manipulation was being done, java.time.Duration would be a better choice

    /**
     * constructor for Track objects
     * @param trackNumber the number of the track as a String
     * @param title the name of the track/song
     * @param duration the length of the song as a String
     */
    public Track(String trackNumber, String title, String duration) {
        this.trackNumber = trackNumber;
        this.title = title;
        this.duration = duration;
    }

    /**
     * getter method for the number of the track
     * @return the number of the track as a String
     */
    public String getTrackNumber() {
        return trackNumber;
    }

    /**
     * setter method for the number of the track
     * @param trackNumber a String representing the number of the track
     */
    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * getter method for the title of the track
     * @return the name of the track, as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter method for the title of the track
     * @param title a String object representing the name of the track
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter method for the duration of the track
     * @return the duration of the track, as a String object
     */
    public String getDuration() {
        return duration;
    }

    /**
     * setter method for the duration of the track
     * @param duration a String object representing the duration of the track
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
