import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.PropertyUtils;
import utilities.RandomEmailGenerator;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidateAPIResponseAttributesTest {
    @Test
    public void validateAPIResponseAttributes() {
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
                .header("Content-Type", "application/json")
                .body(signupRequestBody)
                .post("/api/auth/signup");

        String accessToken = signupResponse.jsonPath().getString("data.session.access_token");

        // Fetch products and validate response
        Response productsResponse = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/api/products");

        Headers responseHeaders = productsResponse.getHeaders();
        long responseTime = productsResponse.getTime();

        // Validate the Content-Type header in the response
        assertThat(responseHeaders.getValue("Content-Type"), equalTo("application/json; charset=utf-8"));

        // Validate response time
        assertThat(responseTime, lessThan(3000L));

        // Validate status and status text
        assertThat(productsResponse.getStatusCode(), equalTo(200));
        assertThat(productsResponse.getStatusLine(), containsString("OK"));
    }
}
