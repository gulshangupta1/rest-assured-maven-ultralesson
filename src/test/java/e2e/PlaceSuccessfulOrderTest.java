package e2e;

import clients.CartClient;
import clients.PaymentClient;
import clients.ProductClient;
import clients.UserClient;
import io.restassured.response.Response;
import models.auth.SignupResponseModel;
import models.cart.AddItemResponseModel;
import models.cart.CreateCartResponseModel;
import models.payment.MakePaymentResponseModel;
import models.product.ProductListResponseModel;
import org.testng.annotations.Test;
import utilities.RandomEmailGenerator;
import utilities.ResponseUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PlaceSuccessfulOrderTest extends BaseTest {
    @Test
    public void shouldPlaceOrderSuccessfully() {
        String randomEmail = RandomEmailGenerator.generateRandomEmail();
        String password = "12345678";

        Response response = UserClient.signup(randomEmail, password);
        SignupResponseModel signupResponseModel = ResponseUtils.deserializeResponse(response, SignupResponseModel.class);
        String accessToken = signupResponseModel.getData().getSession().getAccessToken();

        assertEquals(signupResponseModel.getStatusCode(), 201);
        assertNotNull(accessToken);

        ProductListResponseModel productListResponseModel = ProductClient.getProductsList(accessToken);
        assertThat(productListResponseModel.getStatusCode(), equalTo(200));

        ProductListResponseModel.ProductModel productModel = productListResponseModel.getProducts().get(0);

        // Creating the cart
        CreateCartResponseModel createCartResponseModel = CartClient.createCart(accessToken);
        assertThat(createCartResponseModel.getStatusCode(), equalTo(201));
        String cartId = createCartResponseModel.getCartId();

        // Add Item to cart
        AddItemResponseModel addItemResponseModel = CartClient.addItemToCart(accessToken, productModel, 10, cartId);
        assertThat(addItemResponseModel.getStatusCode(), equalTo(201));

        // Making payment
        MakePaymentResponseModel makePaymentResponseModel = PaymentClient.makePayment(accessToken);
        assertThat(makePaymentResponseModel.getMessage(), equalTo("payment success"));
    }
}
