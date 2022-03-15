package src.main.Enrique.Helpers;

import java.util.concurrent.ThreadLocalRandom;

// Misc methods purely for testing.
public class RandHelpers {

    public static int RandomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    // Note: Bounds are inclusive.
    public static int RandomIntInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
