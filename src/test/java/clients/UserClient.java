package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.SignupRequestModel;

public class UserClient {
    public static Response signup(String email, String password) {
        SignupRequestModel signupRequestModel = SignupRequestModel.builder()
                .email(email)
                .password(password)
                .build();

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(signupRequestModel)
                .post("/api/auth/signup");
    }
}
