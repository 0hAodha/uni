// Andrew Hayes, ID: 21321503
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class NCTBooking {
    private static final AtomicLong NEXT_ID = new AtomicLong(0);

    private String vehicleRegistrationNumber;
    private TestCentre testCentre;
    private LocalDateTime datetime;
    private final long bookingID = NEXT_ID.getAndIncrement();

    public NCTBooking(String vehicleRegistrationNumber, TestCentre testCentre, LocalDateTime datetime) throws DateInPastException {
        // if datetime is in the future, initialising NCTBooking, otherwise throwing DateInPastException
        if (datetime.isAfter(LocalDateTime.now())) {
            this.vehicleRegistrationNumber = vehicleRegistrationNumber;
            this.testCentre = testCentre;
            this.datetime = datetime;
        }
        else {
            throw new DateInPastException("Passed datetime is in the past!");
        }
    }

    public NCTBooking(String vehicleRegistrationNumber, TestCentre testCentre) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.testCentre = testCentre;
    }

    public TestCentre getTestCentre() { return testCentre; }

    public String getVehicleRegistrationNumber() { return vehicleRegistrationNumber; }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) { this.vehicleRegistrationNumber = vehicleRegistrationNumber; }

    public long getBookingID() { return bookingID; }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return  "Booking ID Number: " + bookingID + "\n" +
                "Registration Number: " + vehicleRegistrationNumber + "\n" +
                "Centre: " + testCentre.getName() + "\n" +
                "Address: " + testCentre.getAddress() + "\n" +
                "Date & Time: On " + datetime.format(dateFormatter) + " at " + datetime.format(timeFormatter);
    }

    public void setDatetime(LocalDateTime datetime) throws DateInPastException {
        // if datetime is in the future, setting datetime, otherwise throwing exception
        if (datetime.isAfter(LocalDateTime.now())) {
            this.datetime = datetime;
        }
        else {
            throw new DateInPastException(datetime.toString() + "is in the past!");
        }
    }

    public LocalDateTime getDatetime() { return datetime; }
}
