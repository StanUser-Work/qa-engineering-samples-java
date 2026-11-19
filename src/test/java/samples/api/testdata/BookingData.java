package samples.api.testdata;

import java.time.LocalDate;
import java.util.UUID;

import samples.api.models.Booking;

/** Test data for Restful-Booker. Each booking gets a unique last name to avoid clashes on the shared demo API. */
public final class BookingData {

    /** Public admin credentials from the Restful-Booker documentation. */
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password123";

    private BookingData() {
    }

    public static Booking newBooking() {
        LocalDate checkin = LocalDate.now().plusDays(30);
        return new Booking(
                "Test",
                "Qa-" + UUID.randomUUID().toString().substring(0, 8),
                150,
                true,
                new Booking.BookingDates(checkin.toString(), checkin.plusDays(3).toString()),
                "Breakfast");
    }
}
