import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * class to represent a player
 */
public class Player implements Serializable {
    private String id;
    private String username;
    private Country country;
    private LocalDate joinDate;
    private List<Achievement> achievements;
    private static final String serializeAchievementsTo = "achievements.csv";

    /**
     * constructor for the Player class
     * @param id a String representing the player's ID
     * @param username a String representing the player's username
     * @param country  a Country object representing the country to which the player belongs
     * @param joinDate a LocalDate object representing the date that the player joined the team
     * @param achievements a List of Achievement objects awarded to the player
     */
    public Player(String id, String username, Country country, LocalDate joinDate, List<Achievement> achievements) {
        this.id = id;
        this.username = username;
        this.country = country;
        this.joinDate = joinDate;
        this.achievements = achievements;
    }

    /**
     * overridden method to compare two objects for equality
     * @param obj the object to be compared
     * @return boolean - true if the objects have all the same fields equals, false otherwise
     */
    @Override
    public boolean equals(Object obj)  {
        if (obj instanceof Player) {
            Player toCompare = (Player) obj;

            if (!id.equals(toCompare.id)) {
                return false;
            }
            if (!username.equals(toCompare.username)) {
                return false;
            }
            if (!country.equals(toCompare.country)) {
                return false;
            }
            if (!joinDate.equals(toCompare.joinDate)) {
                return false;
            }
            if (!achievements.equals(toCompare.achievements)) {
                return false;
            } // note that if the lists are in different orders, this will return false

            return true;
        }
        else { return false; }
    }

    /**
     * method to deserialize the Player object and to read in its achievements from a CSV file
     * @param out ObjectOutputStream
     * @throws IOException
     */
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        // would be inappropriate to catch IOExceptions here - should be caught in the code attempting to serialize the object
        out.writeObject(id);
        out.writeObject(username);
        out.writeObject(country);
        out.writeObject(joinDate);

        // deliberate design choice here to not use the writeObject method of the Achievement class
        // serializing Achievements in the context of a Player's list of Achievements should be treated differently to serializing, say, a single, isolated Achievement
        // there may be times when we want to serialize an Achievement without reference to a Player, without a CSV file

        // writing out Achievements to CSV file
        FileWriter writer = new FileWriter(serializeAchievementsTo, true);   // append set to true
        for (Achievement achievement : achievements) {
            writer.write(id + "," + achievement.getAchievementName() + "," + achievement.getDescription() + "," + achievement.getDateOfAward().toString() + "\n");
        }
        writer.close();
    }

    /**
     * method to serialize the Player object and to write out its achievements to a CSV file
     * @param in ObjectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // would be inappropriate to catch exceptions here - should be caught in the code attempting to de-serialize the object
        id = (String) in.readObject();
        username = (String) in.readObject();
        country = (Country) in.readObject();
        joinDate = (LocalDate) in.readObject();

        achievements = new ArrayList<>();

        FileReader fileReader = new FileReader(serializeAchievementsTo);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] cols = line.split(",");

            // only adding to list of achievements if the player ids match
            if (cols[0].equals(id)) {
                achievements.add(new Achievement(cols[1], cols[2], LocalDate.parse(cols[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            }
        }
        bufferedReader.close();

    }


    /**
     * getter method for the ID field
     * @return a String representing the player's ID
     */
    public String getId() { return id; }

    /**
     * setter method for the id field
     * @param id String representing the player's ID
     */
    public void setId(String id) { this.id = id; }


    /**
     * getter method for the username field
     * @return a String representing the player's username
     */
    public String getUsername() { return username; }

    /**
     * setter method for the username field
     * @param username String representing the player's username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * getter method for the country field
     * @return a Country object
     */
    public Country getCountry() { return country; }

    /**
     * setter method for the country field
     * @param country a Country object
     */
    public void setCountry(Country country) { this.country = country; }

    /**
     * getter method for the joinDate field
     * @return a LocalDate representing the date when the player joined the team
     */
    public LocalDate getJoinDate() { return joinDate; }

    /**
     * setter method for the joinDate field
     * @param joinDate LocalDate representing the date when the player joined the team
     */
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    /**
     * getter method for the achievements field
     * @return a list of Achievement objects
     */
    public List<Achievement> getAchievements() { return achievements; }

    /**
     * setter method for the achievements field
     * @param achievements a list of Achievement objects
     */
    public void setAchievements(List<Achievement> achievements) { this.achievements = achievements; }
}
