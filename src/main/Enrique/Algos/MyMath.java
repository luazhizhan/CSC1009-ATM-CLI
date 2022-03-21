package src.main.Enrique.Algos;

public class MyMath {
    public static int LCM(int[] values) {
        int[] myArray = { 25, 50, 125, 625 };
        int min, max, x, lcm = 0;

        for (int i = 0; i < myArray.length; i++) {
            for (int j = i + 1; j < myArray.length - 1; j++) {
                if (myArray[i] > myArray[j]) {
                    min = myArray[j];
                    max = myArray[i];
                } else {
                    min = myArray[i];
                    max = myArray[j];
                }
                for (int k = 0; k < myArray.length; k++) {
                    x = k * max;
                    if (x % min == 0) {
                        lcm = x;
                    }
                }
            }
        }
        System.out.println("LCM of the given array of numbers : " + lcm);
        return lcm;
    }

    // public static int LCM(int a, int b) {
    // if (a == 0 || b == 0)
    // return -1;
    // return a;
    // }

    public static int LCM(int a, int b) {
        return (a * b) / GCF(a, b);
    }

    public static int GCF(int a, int b) throws IllegalArgumentException {
        try {
            if (a == 0 || b == 0) {
                throw new IllegalArgumentException("Input cannot be negative.");
            }
        } catch (IllegalArgumentException e) {
            // TODO: handle exception
        }
        if (b == 0) {
            return a;
        } else {
            return (GCF(b, a % b));
        }
    }
}
