package utilities;

import java.util.UUID;

public class RandomEmailGenerator {
    public static String generateRandomEmail() {
        return UUID.randomUUID().toString() + "@gmail.com";
    }
}
