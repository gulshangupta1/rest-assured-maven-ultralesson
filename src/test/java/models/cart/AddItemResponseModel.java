package models.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddItemResponseModel {
    private int statusCode;
    @JsonProperty("cart_item_id")
    private String cartItemId;
    @JsonProperty("cart_id")
    private String cartId;
    @JsonProperty("product_id")
    private String productId;
    private int quantity;
    private double price;
}
