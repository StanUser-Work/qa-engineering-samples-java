package samples.api;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import samples.api.models.Booking;

/** Thin REST Assured wrapper around the Restful-Booker endpoints the tests use. */
public class BookingApiClient {

    public static final String BASE_URL = "https://restful-booker.herokuapp.com";

    // Restful-Booker answers 418 unless Accept is exactly "application/json",
    // so plain header values are used instead of ContentType.JSON (which sends a list of JSON types).
    private RequestSpecification request() {
        return given()
                .baseUri(BASE_URL)
                .config(RestAssured.config().encoderConfig(
                        EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .contentType("application/json")
                .accept("application/json");
    }

    public Response createBooking(Booking booking) {
        return request().body(booking).post("/booking");
    }

    public Response getBooking(int id) {
        return request().get("/booking/{id}", id);
    }

    public Response deleteBooking(int id, String token) {
        return request().cookie("token", token).delete("/booking/{id}", id);
    }

    public Response createToken(String username, String password) {
        return request().body(Map.of("username", username, "password", password)).post("/auth");
    }
}
