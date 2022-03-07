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

    // public static void main(String[] args) {
        
    //     int min = 0;
    //     int max = 10000;

    //     long startTime = System.nanoTime();
        
    //     for (int i = 0; i < 10000; i++) {
    //         int randomNum = RandomIntInRange(min, max);
    //         if(randomNum < 0 || randomNum > 10000)
    //             System.out.println(randomNum + " is out of bounds");
    //     }

    //     long endTime   = System.nanoTime();
    //     long totalTime = endTime - startTime;
    //     System.out.println("Program totalTime: " + totalTime + "ns");
    //     System.out.println("Program totalTime: " + totalTime / 1000000 + " ms");
    // }
}
