package Atm;

import java.math.BigDecimal;
import java.util.InputMismatchException;

import Currency.Currency;

import Helper.Tuple;

// Dispenses $.
public class MoneyHandler {

    // Depending on ATM location, load appropriate bill values. Location infers
    // currency used, and thus which configuration of banknotes to handle.
    // Pair currency to country.

    // the default initial number of bills in the cash dispenser. An arbitary value.
    private static final int INITIAL_COUNT = 300;

    // int[0] - banknote face value
    // int[1] - number of banknotes
    private int[][] dispenserAmounts;
    private int withdrawMinimum;
    private int withdrawMaximum;

    // Instead of location, give it the currency code imo. Can pass that on ATM
    // init.
    public MoneyHandler(Currency currency) {
        dispenserAmounts = new int[2][currency.getBanknotes().length];
        dispenserAmounts[0] = currency.getBanknotes();

        withdrawMinimum = currency.getWithdrawMinimum();
        withdrawMaximum = currency.getWithdrawMaximum();

        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] = INITIAL_COUNT;
        }
    }

    public MoneyHandler(Currency currency, int[] cashAmounts) throws InputMismatchException {
        dispenserAmounts = new int[2][currency.getBanknotes().length];
        dispenserAmounts[0] = currency.getBanknotes();

        withdrawMinimum = currency.getWithdrawMinimum();
        withdrawMaximum = currency.getWithdrawMaximum();

        try {
            if (dispenserAmounts[0].length != cashAmounts.length) {
                throw new InputMismatchException("Array size must be " + dispenserAmounts[0].length);
            }
            for (int i = 0; i < dispenserAmounts[0].length; i++) {
                dispenserAmounts[1][i] = cashAmounts[i];
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Withdraw this value.
    public Tuple<BigDecimal, int[]> withdraw(int value) throws IllegalArgumentException {
        Tuple<Boolean, int[]> dispensed = ChangeAlgo.MakeChange(dispenserAmounts[0], dispenserAmounts[1], value);
        Tuple<BigDecimal, int[]> returnValue = new Tuple<BigDecimal, int[]>(BigDecimal.ZERO, dispensed.y);
        try {
            if (value < withdrawMinimum)
                throw new IllegalArgumentException("Amount is too low!");
            else if (value > withdrawMaximum)
                throw new IllegalArgumentException("Amount is too high!");

            // Signal that the dispenser cannot dispense the input amount
            if (dispensed.x == false)
                return returnValue;

            // Remove notes from dispenser
            for (int i = 0; i < dispenserAmounts[0].length; i++) {
                dispenserAmounts[1][i] -= dispensed.y[i];
            }
            returnValue = new Tuple<BigDecimal, int[]>(getTotalValue(dispensed.y), dispensed.y);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        // Dispense successful
        return returnValue;
    }

    // Dispense this value.
    public Tuple<Boolean, BigDecimal> deposit(int[] depositAmounts)
            throws InputMismatchException, IllegalArgumentException {

        if (depositAmounts.length != dispenserAmounts[0].length) {
            throw new InputMismatchException("Array size must be " + dispenserAmounts[0].length);
        }

        int totalValue = 0;
        Tuple<Boolean, BigDecimal> returnTuple = new Tuple<Boolean, BigDecimal>(false, BigDecimal.ZERO);
        try {
            int counter = 0;
            for (int i : depositAmounts) {
                if (i < 0)
                    throw new IllegalArgumentException("Number of notes should be >= 0.");
                counter += i;
            }
            if (counter == 0)
                throw new IllegalArgumentException("Please deposit at least one note.");

            // Add notes to dispenser
            for (int i = 0; i < dispenserAmounts[0].length; i++) {
                dispenserAmounts[1][i] += depositAmounts[i];
                totalValue += depositAmounts[i] * dispenserAmounts[0][i];
            }
            totalValue = getTotalValue(depositAmounts);

            returnTuple = new Tuple<Boolean, BigDecimal>(true, new BigDecimal(totalValue));

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return returnTuple;
    }

    // Helper method for computing the total value given an array of notes
    private int getTotalValue(int[] notes) {
        int totalValue = 0;
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            if (i >= notes.length)
                continue;
            totalValue += notes[i] * dispenserAmounts[0][i];
        }
        return totalValue;
    }

}
