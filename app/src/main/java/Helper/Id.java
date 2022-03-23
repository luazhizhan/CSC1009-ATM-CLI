package Helper;

import java.util.UUID;

public class Id {
    public static String generateUUID() {
        return String.join("", UUID.randomUUID().toString().split("-"));
    }
}
