import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * JUnit tests to test the serialization ability of the Player class
 */
public class TestSerialization {
    // assuming that the instruction "Use a test-driven development approach to implement the serialization of Player objects" only applies to the serialization, and not the other functionality

    /**
     * method that runs after each test and deletes the achievements.csv and players.ser files
     */
    @AfterEach
    void deleteFiles() {
        File achievementsFile = new File("achievements.csv");
        achievementsFile.delete();

        File playersFile = new File("player.ser");
        playersFile.delete();
    }


    /**
     * test that creates 5 player objects, serializes them, and compares them to the deserialized objects
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    void serializeFivePlayers() throws IOException, ClassNotFoundException {
        // deliberately not catching potential IOExceptions - we want the tests to fail if these are thrown
        FileOutputStream  players_file = new FileOutputStream("players.ser");
        ObjectOutputStream players_out = new ObjectOutputStream(players_file);
        ObjectInputStream players_in = new ObjectInputStream(new FileInputStream("players.ser"));

        ArrayList<Player> players = new ArrayList<>();  // array of players to be serialized

        // creating some unique players
        // player 0
        players.add(new Player("0", "player0", Country.IE, LocalDate.now(),
                new ArrayList<>() {{
                    add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6)));
                }}
        ));

        // player 1
        players.add(new Player("1", "player1", Country.JP, LocalDate.now(),
                new ArrayList<>() {{
                    add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6)));
                    add(new Achievement("Another Award", "An award for something else", LocalDate.of(1996, 7, 6)));
                }}
        ));

        // player 2
        players.add(new Player("2", "player2", Country.UA, LocalDate.now(),
                new ArrayList<>() {{
                    add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6)));
                    add(new Achievement("Another Award", "An award for something else", LocalDate.of(1996, 7, 6)));
                    add(new Achievement("Yet Another Award", "An award for something else entirely", LocalDate.of(2023, 8, 6)));
                }}
        ));

        // player 3
        players.add(new Player("3", "player3", Country.AD, LocalDate.now(),
                new ArrayList<>() {{
                    add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6)));
                    add(new Achievement("Another Award", "An award for something else", LocalDate.of(1996, 7, 6)));
                    add(new Achievement("Yet Another Award", "An award for something else entirely", LocalDate.of(2023, 8, 6)));
                    add(new Achievement("A fourth award", "An award that comes after three previous awards", LocalDate.of(2022, 8, 3)));
                }}
        ));

        // player 4
        players.add(new Player("4", "player4", Country.DE, LocalDate.now(),
                new ArrayList<>() {{
                    add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6)));
                    add(new Achievement("Another Award", "An award for something else", LocalDate.of(1996, 7, 6)));
                    add(new Achievement("Yet Another Award", "An award for something else entirely", LocalDate.of(2023, 8, 6)));
                    add(new Achievement("A fourth award", "An award that comes after three previous awards", LocalDate.of(2022, 8, 3)));
                    add(new Achievement("The final award", "The last award anyone will ever get", LocalDate.of(2030, 12, 25)));
                }}
        ));

        for (Player player : players) {
            players_out.writeObject(player);
        }

        // looping over each player and comparing it to the de-serialized player
        // doing this in a separate loop to separate writing and reading
        for (Player player : players) {
            // deliberately not catching exceptions here - we want the test to fail if exceptions are thrown
            Player dplayer = (Player) players_in.readObject();
            assertEquals(player, dplayer);
        }

        players_out.close();
        players_file.close();
    }

    /**
     * test the equals() method of the Player class
     */
    @Test
    void testPlayerDotEquals() {
        // creating two identical players
        Player player1 = new Player("0", "player0", Country.IE, LocalDate.now(),
            new ArrayList<>(){{ add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6))); }}
        );
        Player player2 = new Player("0", "player0", Country.IE, LocalDate.now(),
                new ArrayList<>(){{ add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6))); }}
        );

        // assertEquals uses the objects equals() method, so it suffices for this test
        assertEquals(player1, player2);
    }

    /**
     * test the equals() method of the Achievement class
     */
    @Test
    void testAchievementDotEquals() {
        Achievement achievement1 = new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6));
        Achievement achievement2 = new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6));

        assertEquals(achievement1, achievement2);
    }

    /**
     * test serializing & de-serializing just one player
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    void testSerializePlayer() throws IOException, ClassNotFoundException {
        // deliberately not catching potential IOExceptions - we want the tests to fail if these are thrown
        FileOutputStream  players_file = new FileOutputStream("players.ser");
        ObjectOutputStream players_out = new ObjectOutputStream(players_file);
        ObjectInputStream players_in = new ObjectInputStream(new FileInputStream("players.ser"));

        Player player = new Player("0", "player0", Country.IE, LocalDate.now(),
                new ArrayList<>(){{ add(new Achievement("Something Award", "An award for something that was especially something", LocalDate.of(2004, 7, 6))); }}
        );

        // serializing player object, de-serializing it, and comparing them
        players_out.writeObject(player);
        Player dplayer = (Player) players_in.readObject();
        players_out.close();
        players_file.close();

        assertEquals(player, dplayer);
    }
}

