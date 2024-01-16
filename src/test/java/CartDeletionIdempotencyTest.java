import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.PropertyUtils;
import utilities.RandomEmailGenerator;

import static org.hamcrest.MatcherAssert.assertThat;

public class CartDeletionIdempotencyTest {
    @Test
    public void verifyCartDeletionAndIdempotency() {
        // Read base URL from the property file
        String baseUrl = PropertyUtils.getProperty("base.url");
        RestAssured.baseURI = baseUrl;

        // Generate a random email using the utility function
        String randomEmail = RandomEmailGenerator.generateRandomEmail();

        String signupRequestBody = String.format("{\n" +
                "    \"email\": \"%s\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}", randomEmail);

        // Signup to get an access token
        Response signupResponse = RestAssured.given()
                .contentType("application/json")
                .body(signupRequestBody)
                .post("api/auth/signup");

        String accessToken = signupResponse.jsonPath().getString("data.session.access_token");

        // Cart Creation
        Response cartCreationResponse = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .post("/api/cart");

        String cartId = cartCreationResponse.jsonPath().getString("cart_id");

        // Delete the Created Cart (Initial Deletion)
        Response cartDeletionResponse = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .delete("/api/cart/" + cartId);

        // Validate initial Cart Deletion
        assertThat(cartDeletionResponse.getStatusCode(), Matchers.equalTo(204));

        // Re-attempt to delete the same cart (Idempotency check)
        Response cartDeletionIdempotentResponse = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .delete("api/cart/" + cartId);

        // Validate Idempotent Cart Deletion
        assertThat(cartDeletionIdempotentResponse.getStatusCode(), Matchers.equalTo(204));

        // Attempt to retrieve the deleted cart
        Response cartRetrievalResponse = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .get("/api/cart");

        // Validate Cart Retrieval Response
        assertThat(cartRetrievalResponse.getStatusCode(), Matchers.equalTo(200));
        assertThat(cartRetrievalResponse.jsonPath().getString("message"), Matchers.equalTo("No cart found"));
    }
}
