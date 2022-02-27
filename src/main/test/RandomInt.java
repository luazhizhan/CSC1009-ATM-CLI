package src.main.test;

import java.util.concurrent.ThreadLocalRandom;



public class RandomInt {

    // Note: nextInt upper bound is exclusive, so we add 1 to make the range include both bounds.
    public static int RandomIntInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    // For self
    public static void main(String[] args) {
        
        int min = 0;
        int max = 10000;

        long startTime = System.nanoTime();
        
        for (int i = 0; i < 10000; i++) {
            int randomNum = RandomIntInRange(min, max);
            if(randomNum < 0 || randomNum > 10000)
                System.out.println(randomNum + " is out of bounds");
        }

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Program totalTime: " + totalTime + "ns");
        System.out.println("Program totalTime: " + totalTime / 1000000 + " ms");
    }
}
