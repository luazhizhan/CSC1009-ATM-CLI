package src.main.Enrique.Helpers;

import java.util.concurrent.ThreadLocalRandom;

// Misc methods purely for testing.
// Using ThreadLocalRandom for now as it seems reliable enough.
public class RandHelpers {

    // Assumes that ThreadLocalRandom.current().nextInt generates
    // independent and uniformly distributed numbers.
    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    // Assumes that ThreadLocalRandom.current().nextInt generates
    // independent and uniformly distributed numbers between 0 and (i + 1).
    // Uses an inclusive upper bound.
    public static int randomInt(int upperBound) {
        return ThreadLocalRandom.current().nextInt(upperBound + 1);
    }

    // Assumes that ThreadLocalRandom.current().nextInt generates
    // independent and uniformly distributed numbers between 0 and (i + 1).
    // Uses a lower and upper bound, both inclusive.
    public static int randomInt(int lowerBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }

    // Shuffles an array of elements. Knuth/FYS shuffle.
    // TODO: Benchmark me
    public static <T> void shuffle(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int n = ThreadLocalRandom.current().nextInt(i + 1);
            Swap(arr, i, n);
        }
    }

    // Generic method for swapping elements in an array using indexes.
    public static <T> void Swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}
