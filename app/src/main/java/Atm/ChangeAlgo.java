package Atm;

import Helper.Tuple;

class ChangeAlgo {

    public static Tuple<Boolean, int[]> MakeChange(int[] denominations, int[] limits, int value) {

        if (limits.length != denominations.length) {
            // We have an error. Sizes do not match. Print error and exit.
            System.err.println("Denominations and Limits list must be of the same size.");
            System.exit(1);
        }

        int[] values = new int[denominations.length];

        for (int i = denominations.length - 1; i >= 0; i--) {
            if (value == 0)
                values[i] = 0;
            else {
                int denomination = denominations[i];
                int sub = value / denomination;
                int current = limits[i];

                sub = Math.min(current, sub);
                value -= denomination * sub; // updating the value.
                values[i] = sub; // Set to sub to get the bills used.
            }
        }

        if (value != 0) {
            return new Tuple<Boolean, int[]>(false, values);
        }

        // Everything works. Return the values.
        return new Tuple<Boolean, int[]>(true, values);

    }
}