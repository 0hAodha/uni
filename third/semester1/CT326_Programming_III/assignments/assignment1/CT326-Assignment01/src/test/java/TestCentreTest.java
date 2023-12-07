// Andrew Hayes, ID: 21321503
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;

class TestCentreTest {
    // tests ability to instantiate a TestCentre
    @Test
    void testCreateTestCentre() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        assertNotNull(testCentre);
    }

    // tests the getter for "name" in TestCentre class
    @Test
    void testGetName() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        assertEquals("Test Centre 1", testCentre.getName());
    }

    // tests the getter for "address" in TestCentre class
    @Test
    void testGetAddress() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        assertEquals("10 Downing Street", testCentre.getAddress());
    }
}
