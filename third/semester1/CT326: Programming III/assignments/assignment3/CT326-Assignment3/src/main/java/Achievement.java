import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

/**
 * class representing a player's achievement
 */
public class Achievement {
    private String achievementName;
    private String description;
    private LocalDate dateOfAward;

    /**
     * constructor for the Achievement class
     * @param achievementName a String representation of the name of the Achievement
     * @param description a String describing the Achievement
     * @param dateOfAward a LocalDate representing the date that the Achievement was awarded
     */
    public Achievement(String achievementName, String description, LocalDate dateOfAward) {
        this.achievementName = achievementName;
        this.description = description;
        this.dateOfAward = dateOfAward;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Achievement) {
            Achievement toCompare = (Achievement) obj;

            if (!achievementName.equals(toCompare.achievementName)) {
                return false;
            }
            if (!description.equals(toCompare.description)) {
                return false;
            }
            if (!dateOfAward.equals(toCompare.dateOfAward)) {
                return false;
            }

            return true;
        }
        else { return false; }
    }

    /**
     * getter method for the achievementName field
     * @return a String representation of the name of the Achievement
     */
    public String getAchievementName() { return achievementName; }

    /**
     * setter method for the achievementName field
     * @param achievementName a String representation of the name of the Achievement
     */
    public void setAchievementName(String achievementName) { this.achievementName = achievementName; }

    /**
     * getter method for the description field
     * @return a String describing the Achievement
     */
    public String getDescription() { return description; }

    /**
     * setter method for the description field
     * @param description a String describing the Achievement
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * getter method for the dateOfAward field
     * @return a LocalDate representing the date that the Achievement was awarded
     */
    public LocalDate getDateOfAward() { return dateOfAward; }

    /**
     * setter method for the dateOfAward field
     * @param dateOfAward a LocalDate representing the date that the Achievement was awarded
     */
    public void setDateOfAward(LocalDate dateOfAward) { this.dateOfAward = dateOfAward; }
}
