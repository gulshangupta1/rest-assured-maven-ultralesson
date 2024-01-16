package models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupResponseModel {
    private int statusCode;
    private Data data;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private Session session;
    }

    @lombok.Data
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Session {
        @JsonProperty("access_token")
        private String accessToken;
    }
}