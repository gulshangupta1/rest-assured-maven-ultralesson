package models.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductListResponseModel {
    private int statusCode;
    private List<ProductModel> products;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductModel {
        @JsonProperty("created_at")
        private String createdAt;
        private String name;
        private String description;
        private String price;
        private String quantity;
        private String id;
        @JsonProperty("category_id")
        private String categoryId;
    }
}
