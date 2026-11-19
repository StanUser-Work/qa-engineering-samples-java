package samples.api;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import samples.api.models.Booking;

/** Thin REST Assured wrapper around the Restful-Booker endpoints the tests use. */
public class BookingApiClient {

    public static final String BASE_URL = "https://restful-booker.herokuapp.com";

    private RequestSpecification request() {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
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
