package samples.api.models;

/** A Restful-Booker booking, as sent to and returned by the API. */
public record Booking(
        String firstname,
        String lastname,
        int totalprice,
        boolean depositpaid,
        BookingDates bookingdates,
        String additionalneeds) {

    public record BookingDates(String checkin, String checkout) {
    }
}
