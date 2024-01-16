package models.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCartResponseModel {
    private int statusCode;
    @JsonProperty("cart_id")
    private String cartId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("created_at")
    private String createdAt;
}
