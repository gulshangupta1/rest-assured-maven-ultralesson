package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ResponseUtils {
    public static <T> T deserializeResponse(Response response, Class<T> responseType) {
        ObjectMapper objectMapper = new ObjectMapper();

        Object jsonResponse = response.getBody().jsonPath().get();

        T responseObject;
        if (jsonResponse instanceof Map) {
            responseObject = objectMapper.convertValue(jsonResponse, responseType);
        } else if (jsonResponse instanceof List) {
            responseObject = objectMapper.convertValue(Map.of("products", jsonResponse), responseType);
        } else {
            throw new IllegalArgumentException("Unsupported JSON structure");
        }

        // Set status code if responseType has a "statusCode" field
        if (responseType.getDeclaredFields() != null) {
            for (Field field : responseType.getDeclaredFields()) {
                if (field.getName().equals("statusCode")) {
                    field.setAccessible(true);
                    try {
                        field.set(responseObject, response.getStatusCode());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        field.setAccessible(false);
                    }
                }
            }
        }

        return responseObject;
    }
}
