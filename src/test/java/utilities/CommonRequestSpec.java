package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class CommonRequestSpec {
    public static RequestSpecification authRequestSpecBuilder(String accessToken) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
    }
}
