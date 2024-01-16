package models.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class MakePaymentResponseModel {
    private int statusCode;
    private String message;
    @JsonProperty("amount_paid")
    private double amountPaid;
}
