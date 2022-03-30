package Helper;

import java.util.UUID;

/**
 * Helper class to standardised generation of UUID
 */
public class Id {
    public static String generateUUID() {
        return String.join("", UUID.randomUUID().toString().split("-"));
    }
}
