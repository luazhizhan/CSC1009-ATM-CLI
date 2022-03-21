package src.main.Enrique.ATM;

import java.util.Arrays;

import src.main.Enrique.Algos.CountMoneyAlgo;
import src.main.Enrique.Helpers.Tuple;

// Dispenses $.
public class MoneyDispenser {

    // Depending on ATM location, load appropriate bill values. Location infers
    // currency used, and thus which configuration of banknotes to handle.
    // Pair currency to country.

    // the default initial number of bills in the cash dispenser. An arbitary value.
    private static final int INITIAL_COUNT = 500;

    // int[0] - banknote face value
    // int[1] - number of banknotes
    private int[][] dispenserAmounts;

    // Instead of location, give it the currency code imo. Can pass that on ATM
    // init.
    public MoneyDispenser(String loc) {

        // Very gross but im tired atm, looks bad but it'll work
        dispenserAmounts = new int[2][CurrencyDatabase.banknotes.get(loc).getBanknotes().length];
        dispenserAmounts[0] = CurrencyDatabase.banknotes.get(loc).getBanknotes();

        // CountMoneyAlgo.setUpTable(dispenserAmounts[0]);

        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] = INITIAL_COUNT;
        }

        // Testing
        System.out.println("CashDispenser initialised.");
        printStatus();
    }

    // Get the smallest value this ATM can dispense.
    private int minDispenseValue() {
        int smallest = Integer.MAX_VALUE;
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            if (dispenserAmounts[1][i] < 1)
                continue;

            if (dispenserAmounts[0][i] < smallest)
                smallest = dispenserAmounts[0][i];
        }
        return smallest;
    }

    // Dispense this value.
    // Current algo doesn't account for current number of notes in system.
    // Current algo gets a close number, not exact.
    // In essence its crap, and temporary so I can scaffold the rest of the code.
    public Boolean dispenseMoney(int value) throws IllegalArgumentException {
        try {
            int minValue = minDispenseValue();
            if (value < minValue)
                throw new IllegalArgumentException("Input value must be at least " + minValue + "!");

            Tuple<Boolean, int[]> dispensed = CountMoneyAlgo.countCurrency(dispenserAmounts, value);

            // Signal that the dispenser cannot dispense the input amount
            if (dispensed.x == false)
                return false;

            // Remove notes from dispenser
            for (int i = 0; i < dispenserAmounts[0].length; i++) {
                dispenserAmounts[1][i] -= dispensed.y[i];
            }
            // count -= billsRequired; // update the count of bills

            // Dispense successful
        } catch (IllegalArgumentException e) {
            // TODO: handle exception
        }
        return true;
    }

    public void printStatus() {
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            System.out.println(dispenserAmounts[0][i] + ": " + dispenserAmounts[1][i]);
        }
    }

}
