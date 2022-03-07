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
        table = new int[DEFAULT_VALUE+1];
        table[0] = 0;
        for(int i = 1; i <= DEFAULT_VALUE; i++) {
            table[i] = DEFAULT_VALUE+1;
        }
    }
    
    // function to count and print currency notes
    // notes = values to use for counting
    // value = value to match.
    public static Tuple<Boolean, int[]> countCurrency(int[] notes, int value)
    {
        // int[] notes = new int[]{ 2000, 500, 200, 100, 50, 20, 10, 5, 1 };
        int[] noteCounter = new int[notes.length];

        for (int i = 0; i < notes.length; i++) {
            // Skip values that are too large
            if(noteCounter[i] > value) continue;
            
            if (value >= notes[i]) {
                noteCounter[i] = value % notes[i];
                value = value - noteCounter[i] * notes[i];
            }
        }

        for (int i = 0; i < notes.length; i++) {
            for (int j = 1; j < value + 1; j++) {
                if(notes[i] > value) continue;
            }
        }

        // for(int note : notes) {
        //     for(int amt = 1; amt <= value ; amt++) {
        //         if(amt >= note) {
        //             table[amt] = Math.min(table[amt], 1 + table[amt - note]);
        //         }
        //     }
        // }
        // int minCoinsReq = table[value] != value+1 ? table[value] : -1 ; 
        // System.out.println("Minimum Coins required : " + minCoinsReq);


        
        return new Tuple<Boolean,int[]>(true, noteCounter);
    }
}