package models.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemRequestModel {
    @JsonProperty("product_id")
    private String productId;
    private int quantity;
}
