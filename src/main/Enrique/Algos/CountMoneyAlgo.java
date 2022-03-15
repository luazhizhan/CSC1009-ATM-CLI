package src.main.Enrique.Algos;

import src.main.Enrique.Helpers.Tuple;

// I cannot emphasise how crap this algo is.
// Not sure if it can account for any number given variable banknote arrays.
// Current reasons why this is broken as hell and sucks:
// Current algo doesn't account for current number of notes in system.
// Current algo gets a close number, not exact. Therefore- broken
public class CountMoneyAlgo {

    private static final int DEFAULT_VALUE = 10000;
    private static int[] table;

    public static void setUpTable(int[] notes) {
        table = new int[DEFAULT_VALUE + 1];
        table[0] = 0;
        for (int i = 1; i <= DEFAULT_VALUE; i++) {
            table[i] = DEFAULT_VALUE + 1;
        }
    }

    // function to count and print currency notes
    // notes = values to use for counting
    // value = value to match.
    public static Tuple<Boolean, int[]> countCurrency(int[][] notes, int value) {
        // int[] notes = new int[]{ 2000, 500, 200, 100, 50, 20, 10, 5, 1 };
        int[] noteCounter = new int[notes[0].length];

        for (int i = 0; i < notes[0].length; i++) {
            // Skip values that are too large
            System.out.println(notes[0][i]);
            if (notes[0][i] <= value) {
                noteCounter[i] = value / notes[0][i];
                // noteCounter[i] = (value - value % notes[0][i]) / notes[0][i];
                System.out.println("post divide" + noteCounter[i]);
                if (noteCounter[i] > notes[1][i])
                    noteCounter[i] = notes[1][i];
                value -= noteCounter[i] * notes[0][i];
            }
            System.out.println(notes[0][i] + " " + noteCounter[i] + " remaining value: " + value);
        }

        if (value != 0) {
            for (int i = 0; i < notes[0].length; i++) {
                if (noteCounter[i] == notes[1][i]) {
                    System.out.println("Out of notes!");
                }
            }
            System.out.println("Algo is bad.");
        }

        System.out.println("noteCounter");
        for (int i = 0; i < noteCounter.length; i++) {
            System.out.println(noteCounter[i]);
        }
        System.out.println("end noteCounter");

        return new Tuple<Boolean, int[]>(true, noteCounter);
    }
}