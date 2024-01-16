import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ApiTestingWithRestAssured {
    @Test
    public void fetchAndLogStatusCode() {
        RestAssured.baseURI = "https://www.apicademy.dev/";

        Response response = RestAssured.given().get();

        int statusCode = response.getStatusCode();

        System.out.println("Status Code: " + statusCode);
    }
}
