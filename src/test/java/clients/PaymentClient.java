package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.payment.MakePaymentResponseModel;
import utilities.CommonRequestSpec;
import utilities.EndpointConfig;
import utilities.ResponseUtils;

public class PaymentClient {
    public static MakePaymentResponseModel makePayment(String accessToken) {
        String makePaymentEndpoint = EndpointConfig.getEndpoint("payment", "makePayment");

        Response response = RestAssured.given()
                .spec(CommonRequestSpec.authRequestSpecBuilder(accessToken))
                .post(makePaymentEndpoint);

        return ResponseUtils.deserializeResponse(response, MakePaymentResponseModel.class);
    }
}
