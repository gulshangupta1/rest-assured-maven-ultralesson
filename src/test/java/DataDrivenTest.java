import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.DataLoader;
import utilities.PropertyUtils;
import utilities.RandomEmailGenerator;

import static org.testng.Assert.assertEquals;

public class DataDrivenTest {
    @Test
    public void testProfileOperations() {
        // Read base URL from the property file
        RestAssured.baseURI = PropertyUtils.getProperty("base.url");

        // Generate a random email using the utility function
        String randomEmail = RandomEmailGenerator.generateRandomEmail();

        // Signup to get an access token
        Response signupResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"email\": \"" + randomEmail + "\", \"password\": \"12345678\"}")
                .post("/api/auth/signup");

        String accessToken = signupResponse.jsonPath().getString("data.session.access_token");

        // Aliasing the profileData fixture
        String firstName = DataLoader.getData("profileData", "first_name");
        String lastName = DataLoader.getData("profileData", "last_name");
        String address = DataLoader.getData("profileData", "address");
        String mobileNumber = DataLoader.getData("profileData", "mobile_number");

        // Create profile
        Response createProfileResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body("{\"first_name\": \"" + firstName + "\", \"last_name\": \"" + lastName + "\", \"address\": \"" + address + "\", \"mobile_number\": \"" + mobileNumber + "\"}")
                .post("/api/profile");

        assertEquals(createProfileResponse.getStatusCode(), 201);
        assertEquals(createProfileResponse.jsonPath().getString("first_name"), firstName);
        assertEquals(createProfileResponse.jsonPath().getString("last_name"), lastName);
        assertEquals(createProfileResponse.jsonPath().getString("address"), address);
        assertEquals(createProfileResponse.jsonPath().getString("mobile_number"), mobileNumber);
    }
}
