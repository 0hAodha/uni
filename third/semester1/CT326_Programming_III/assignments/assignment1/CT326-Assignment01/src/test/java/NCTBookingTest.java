// Andrew Hayes, ID: 21321503
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;

class NCTBookingTest {
    // tests ability to instantiate an NCTBooking
    @Test
    void testCreateNCTBooking() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-1234", testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));
        assertNotNull(booking);
    }

    // tests ability to query the test centre of a booking
    @Test
    void testGetTestCentre() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-1234", testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));

        // value returned by getTestCentre() should be the value set when the booking was created
        assertEquals(testCentre, booking.getTestCentre());
    }

    // tests ability to query the vehicle registration for a booking
    @Test
    void testGetVehicleRegistrationNumber() {
        String vehicleRegistrationNumber = "221-RN-1234";
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking(vehicleRegistrationNumber, testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));

        // value returned by getVehicleRegistrationNumber() should be the value that was set when the booking was created (in this case)
        assertEquals(vehicleRegistrationNumber, booking.getVehicleRegistrationNumber());
    }

    // tests ability to edit the vehicle registration number of a booking
    @Test
    void testSetVehicleRegistrationNumber() {
        String vehicleRegistrationNumber = "221-RN-1234";
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking(vehicleRegistrationNumber, testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));

        // changing the value of vehicleRegistrationNumber and testing that the value returned by getVehicleRegistrationNumber is equal to the updated value
        String newVehicleRegistrationNumber = "00-D-4321";
        booking.setVehicleRegistrationNumber(newVehicleRegistrationNumber);

        // value returned by getVehicleRegistrationNumber() should be the value that was set in the last edit
        assertEquals(newVehicleRegistrationNumber, booking.getVehicleRegistrationNumber());
    }

    // tests ability to instantiate an NCTBooking without specifying a date
    @Test
    void testCreateNCTBookingWithoutDate() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-123", testCentre);
        assertNotNull(booking);
    }

    // tests ability to query the date and time of a booking
    @Test
    void testGetDatetime() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        LocalDateTime datetime = LocalDateTime.of(2024, 1, 1, 9, 0, 0);
        NCTBooking booking = new NCTBooking("221-RN-1234", testCentre, datetime);

        // queried datetime should be equal to the datetime set on initialisation
        assertEquals(datetime, booking.getDatetime());
    }

    // tests ability to set the date and time of a booking via an external API
    @Test
    void testSetDatetime() {
       // stub implementation of the NCTBookingSlotWebservice for testing purposes
       NCTBookingSlotWebservice testService = new NCTBookingSlotWebservice() {
           @Override
           public LocalDateTime getBookingDateTime(TestCentre testCentre) {
               return LocalDateTime.of(2024, 1, 1, 9, 0, 0);
           }
       };

        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-123", testCentre);
        LocalDateTime datetime = testService.getBookingDateTime(testCentre);
        booking.setDatetime(datetime);

        assertEquals(datetime, booking.getDatetime());
    }

    // tests that bookings with a date and time in the past throw a user-defined exception
    @Test
    void testCreateNCTBookingWithDataInThePast() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        assertThrows(
                DateInPastException.class,
                () -> new NCTBooking("221-RN-123", testCentre, LocalDateTime.of(1970, 1, 1, 9, 0, 0))
        );
    }

    // tests that attempting to set the datetime of a booking to a time in the past throws a user-defined exception
    @Test
    void testSetDatetimeInPast() {
        // stub implementation of the NCTBookingSlotWebservice for testing purposes
        NCTBookingSlotWebservice testService = new NCTBookingSlotWebservice() {
            @Override
            public LocalDateTime getBookingDateTime(TestCentre testCentre) {
                return LocalDateTime.of(1970, 1, 1, 9, 0, 0);
            }
        };

        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-123", testCentre);
        LocalDateTime datetime = testService.getBookingDateTime(testCentre);

        assertThrows(
                DateInPastException.class,
                () -> booking.setDatetime(datetime)
        );

    }

    // tests that bookings have a unique booking ID number generated when they're created
    @Test
    void testUniqueIDGenerated() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-123", testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));
        assertNotNull(booking.getBookingID());
    }

    // attempts to test if booking IDs are not unique by comparing 10,000 of them (note that this does not prove uniqueness, just will disprove it in certain situations)
    @Test
    void testBookingIDsUnique() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");

        NCTBooking[] bookings = new NCTBooking[10_000];
        for (int i = 0; i < 10_000; i ++) {
            bookings[i] = new NCTBooking("221-RN-123", testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));
        }

        // looping over the bookings and comparing each one to every other one
        for (int i = 0; i < 10_000; i ++) {
            for (int j = 0; j < 10_000; j ++) {
                if (i != j) {
                    assertNotEquals(bookings[i].getBookingID(), bookings[j].getBookingID());
                }
            }
        }
    }

    // tests that output from toString() method matches a regex for the expected output
    @Test
    void testToString() {
        TestCentre testCentre = new TestCentre("Test Centre 1", "10 Downing Street");
        NCTBooking booking = new NCTBooking("221-RN-123", testCentre, LocalDateTime.of(2024, 1, 1, 9, 0, 0));

        // the only "regular-expressiony" part of the below regex is "[0-9]+" which just matches one or more decimal digits
        assertTrue(booking.toString().matches(
                """
                        Booking ID Number: [0-9]+
                        Registration Number: 221-RN-123
                        Centre: Test Centre 1
                        Address: 10 Downing Street
                        Date & Time: On Monday, 1 January 2024 at 09:00"""
        ));
    }
}