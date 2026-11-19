package samples.api.models;

/** Response of POST /booking: the new id and the stored booking. */
public record CreatedBooking(int bookingid, Booking booking) {
}
