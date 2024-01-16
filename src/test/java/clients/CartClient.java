package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.cart.AddItemRequestModel;
import models.cart.AddItemResponseModel;
import models.cart.CreateCartResponseModel;
import models.product.ProductListResponseModel;
import utilities.EndpointConfig;
import utilities.ResponseUtils;

public class CartClient {
    public static CreateCartResponseModel createCart(String accessToken) {
        String createCartEndpoint = EndpointConfig.getEndpoint("cart", "createCart");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .post(createCartEndpoint);

        return ResponseUtils.deserializeResponse(response, CreateCartResponseModel.class);
    }

    public static AddItemResponseModel addItemToCart(String accessToken, ProductListResponseModel.ProductModel productModel,
                                                     int quantity, String cartID) {
        String createCartEndpoint = EndpointConfig.getEndpoint("cart", "addCartItem");
        createCartEndpoint = createCartEndpoint.replace("{CART_ID}", cartID);

        AddItemRequestModel addItemRequestModel = AddItemRequestModel.builder()
                .productId(productModel.getId())
                .quantity(quantity)
                .build();

        // Add item to cart
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(addItemRequestModel)
                .post(createCartEndpoint);

        return ResponseUtils.deserializeResponse(response, AddItemResponseModel.class);
    }
}
