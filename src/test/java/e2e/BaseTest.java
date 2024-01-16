package e2e;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utilities.PropertyUtils;

public class BaseTest {
    @BeforeClass
    public void setup() {
        // Read base URL from config.properties
        String baseUrl = PropertyUtils.getProperty("base.url");
        RestAssured.baseURI = baseUrl;
    }
}
