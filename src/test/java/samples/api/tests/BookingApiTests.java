package samples.api.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import samples.api.BookingApiClient;
import samples.api.models.Booking;
import samples.api.models.CreatedBooking;
import samples.api.testdata.BookingData;

@Tag("api")
class BookingApiTests {

    private final BookingApiClient api = new BookingApiClient();
    private final List<Integer> createdIds = new ArrayList<>();

    /** Removes bookings the test created, so the shared demo API is left clean. */
    @AfterEach
    void cleanUp() {
        if (!createdIds.isEmpty()) {
            String token = getToken();
            createdIds.forEach(id -> api.deleteBooking(id, token));
        }
    }

    @Test
    @DisplayName("POST /booking creates a booking, and GET /booking/{id} returns exactly the same data")
    void createBooking_thenGetById_returnsSameData() {
        Booking booking = BookingData.newBooking();

        Response createResponse = api.createBooking(booking);

        assertEquals(200, createResponse.statusCode(), "create status");
        CreatedBooking created = createResponse.as(CreatedBooking.class);
        createdIds.add(created.bookingid());
        assertAll(
                () -> assertTrue(created.bookingid() > 0, "a new booking id is returned"),
                () -> assertEquals(booking, created.booking(), "the response echoes the sent booking"));

        Response getResponse = api.getBooking(created.bookingid());

        assertEquals(200, getResponse.statusCode(), "get status");
        assertTrue(getResponse.contentType().startsWith("application/json"), "JSON content type");
        assertEquals(booking, getResponse.as(Booking.class), "the stored booking matches what was sent");
    }

    @Test
    @DisplayName("An authorized user deletes a booking, after which GET /booking/{id} returns 404")
    void deleteBooking_withValidToken_removesBooking() {
        Response createResponse = api.createBooking(BookingData.newBooking());
        assertEquals(200, createResponse.statusCode(), "precondition: booking is created");
        int id = createResponse.as(CreatedBooking.class).bookingid();

        Response deleteResponse = api.deleteBooking(id, getToken());

        // Restful-Booker answers 201 Created on a successful delete (its documented behavior).
        assertEquals(201, deleteResponse.statusCode(), "delete status");
        assertEquals(404, api.getBooking(id).statusCode(), "the deleted booking is no longer available");
    }

    private String getToken() {
        Response response = api.createToken(BookingData.ADMIN_USERNAME, BookingData.ADMIN_PASSWORD);
        assertEquals(200, response.statusCode(), "auth status");
        String token = response.jsonPath().getString("token");
        assertNotNull(token, "a token is issued (reason: " + response.jsonPath().getString("reason") + ")");
        assertFalse(token.isBlank(), "the token is not empty");
        return token;
    }
}
