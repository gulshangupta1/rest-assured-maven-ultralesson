package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.product.ProductListResponseModel;
import utilities.EndpointConfig;
import utilities.ResponseUtils;

public class ProductClient {
    public static ProductListResponseModel getProductsList(String accessToken) {
        String productListEndpoint = EndpointConfig.getEndpoint("product", "getProductsList");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .get(productListEndpoint);

        return ResponseUtils.deserializeResponse(response, ProductListResponseModel.class);
    }
}
