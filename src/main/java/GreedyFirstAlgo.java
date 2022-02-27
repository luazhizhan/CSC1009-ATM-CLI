package src.main.java;


// this shouldn't be used in submitted project.
// Use a better algo
// Not sure if it can account for any number given variable banknote arrays.
// Missing:
// Dispenser banknote limits, e.g. 5 50 dollar bills left, cant dispense 6 of em.
public class GreedyFirstAlgo {
 
    // function to count and
    // print currency notes
    public static int[] countCurrency(int amount, int[] notes)
    {
        // int[] notes = new int[]{ 2000, 500, 200, 100, 50, 20, 10, 5, 1 };
        int[] noteCounter = new int[notes.length];
      
        // count notes using Greedy approach
        for (int i = 0; i < notes.length; i++) {
            if (amount >= notes[i]) {
                noteCounter[i] = amount / notes[i];
                amount = amount - noteCounter[i] * notes[i];
            }
        }

        return noteCounter;
    }
}