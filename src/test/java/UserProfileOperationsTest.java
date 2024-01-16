import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.PropertyUtils;
import utilities.RandomEmailGenerator;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserProfileOperationsTest {
    @Test
    public void testUserProfileCreationAndPartialUpdate() {
        // Read base URL from the property file
        String baseUrl = PropertyUtils.getProperty("base.url");
        baseURI = baseUrl;

        // Generate a random email using the utility function
        String randomEmail = RandomEmailGenerator.generateRandomEmail();

        // User Signup
        String signupRequestBody = String.format("{\n" +
                "    \"email\": \"%s\",\n" +
                "    \"password\": \"password123\"\n" +
                "}", randomEmail);

        Response signupResponse = given()
                .contentType(ContentType.JSON)
                .body(signupRequestBody)
                .post("/api/auth/signup");

        assertThat(signupResponse.getStatusCode(), equalTo(201));

        String accessToken = signupResponse.jsonPath().getString("data.session.access_token");

        // Step 2: Create User Profile
        String firstName = "James";
        String lastName = "Jenny";
        String address = "1st cross, church street, London";
        String mobileNumber = "1234567890";

        String createProfileRequestBody = String.format("{\n" +
                "    \"first_name\": \"%s\",\n" +
                "    \"last_name\": \"%s\",\n" +
                "    \"address\": \"%s\",\n" +
                "    \"mobile_number\": \"%s\"\n" +
                "}", firstName, lastName, address, mobileNumber);

        Response createProfileResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(createProfileRequestBody)
                .post("/api/profile");

        assertThat(createProfileResponse.getStatusCode(), equalTo(201));
        assertThat(createProfileResponse.jsonPath().getString("first_name"), equalTo(firstName));
        assertThat(createProfileResponse.jsonPath().getString("last_name"), equalTo(lastName));
        assertThat(createProfileResponse.jsonPath().getString("address"), equalTo(address));
        assertThat(createProfileResponse.jsonPath().getString("mobile_number"), equalTo(mobileNumber));

        // Step 3: Partially Update User Profile using PATCH
        String newFirstName = "Kane";
        String newLastName = "Jennier";

        String partialUpdateRequestBody = String.format("{\n" +
                "    \"first_name\": \"%s\",\n" +
                "    \"last_name\": \"%s\"\n" +
                "}", newFirstName, newLastName);

        Response partialUpdateResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(partialUpdateRequestBody)
                .patch("/api/profile");

        assertThat(partialUpdateResponse.getStatusCode(), equalTo(200));
        assertThat(partialUpdateResponse.jsonPath().getString("first_name"), equalTo(newFirstName));
        assertThat(partialUpdateResponse.jsonPath().getString("last_name"), equalTo(newLastName));
        assertThat(partialUpdateResponse.jsonPath().getString("mobile_number"), equalTo(mobileNumber));
    }
}
