import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.PropertyUtils;

import static org.hamcrest.MatcherAssert.assertThat;

public class ValidateProductsNoAuthTest {
    @Test
    public void testGetProductsWithoutAuthHeader() {
        // Read base URL from the property file
        String baseUrl = PropertyUtils.getProperty("base.url");
        RestAssured.baseURI = baseUrl;

        // Make GET request without Auth header
        Response response = RestAssured.given().get("api/products");

        // Non-BDD Assertions using Hamcrest
        assertThat(response.getStatusCode(), Matchers.is(400));
        assertThat(response.jsonPath().getString("message"), Matchers.equalTo("Authorization header is missing."));
    }
}
