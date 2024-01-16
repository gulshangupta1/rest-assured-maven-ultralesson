import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.PropertyUtils;
import utilities.RandomEmailGenerator;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetProductsPaginatedTest {
    @Test
    public void testGetPaginatedProducts() {
        // Read base URL from the property file
        String baseUrl = PropertyUtils.getProperty("base.url");
        RestAssured.baseURI = baseUrl;

        // Generate a random email using the utility function
        String randomString = RandomEmailGenerator.generateRandomEmail();

        // Construct the request body using the generated email
        String requestBody = String.format("{\n" +
                "    \"email\": \"%s\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}", randomString);

        // Signup to get an access_token
        Response signResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("api/auth/signup");

        JsonPath jsonPath = signResponse.jsonPath();
        String accessToken = jsonPath.getString("data.session.access_token");

        // Make a GET request with pagination
        Response productsResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("page", 1)
                .queryParam("limit", 2)
                .get("/api/products");

        // Validate the response
        jsonPath = productsResponse.jsonPath();
        int productCount = jsonPath.getList("$").size();

        // Validating the status code
        assertThat(productsResponse.getStatusCode(), Matchers.equalTo(200));
        // Validating the length of returned products array with limited value
        assertThat(productCount, Matchers.equalTo(2));
    }
}
