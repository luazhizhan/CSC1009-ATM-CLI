package Model.Atm;

import java.math.BigDecimal;
import java.util.InputMismatchException;

import Helper.Tuple;
import Model.Currency.Currency;

// Dispenses $.
public class MoneyHandler {

    // Depending on ATM location, load appropriate bill values. Location infers
    // currency used, and thus which configuration of banknotes to handle.
    // Match currency to country.

    // the default initial number of bills in the cash dispenser. An arbitary value.
    private static final int INITIAL_COUNT = 300;

    // int[0] - banknote face value
    // int[1] - number of banknotes
    private int[][] dispenserAmounts;
    private BigDecimal withdrawMinimum;
    private BigDecimal withdrawMaximum;
    private String currencyCode;

    // Instead of location, give it the currency code imo. Can pass that on ATM
    // init.
    public MoneyHandler(Currency currency) {
        dispenserAmounts = new int[2][currency.getBanknotes().length];
        dispenserAmounts[0] = currency.getBanknotes();

        withdrawMinimum = new BigDecimal(currency.getWithdrawMinimum());
        withdrawMaximum = new BigDecimal(currency.getWithdrawMaximum());
        currencyCode = currency.getCurrencyAcronym();

        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] = INITIAL_COUNT;
        }
    }

    public MoneyHandler(Currency currency, int[] cashAmounts) throws InputMismatchException {
        dispenserAmounts = new int[2][currency.getBanknotes().length];
        dispenserAmounts[0] = currency.getBanknotes();

        withdrawMinimum = new BigDecimal(currency.getWithdrawMinimum());
        withdrawMaximum = new BigDecimal(currency.getWithdrawMaximum());
        currencyCode = currency.getCurrencyAcronym();

        if (dispenserAmounts[0].length != cashAmounts.length) {
            throw new InputMismatchException("Array size must be " + dispenserAmounts[0].length);
        }
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] = cashAmounts[i];
        }
    }

    // Withdraw this value.
    public Tuple<BigDecimal, int[]> withdraw(BigDecimal value)
            throws InsufficientNotesException, IllegalArgumentException {
        // Throw errors if outside of range.
        if (value.compareTo(withdrawMinimum) == -1)
            throw new IllegalArgumentException(
                    "Amount is too low, minimum withdraw limit is " + withdrawMinimum + " " + currencyCode + ".");
        else if (value.compareTo(withdrawMaximum) == 1)
            throw new IllegalArgumentException(
                    "Amount is too high, maximum withdraw limit is " + withdrawMaximum + " " + currencyCode + ".");

        int[] dispensed = makeChange(dispenserAmounts[0], dispenserAmounts[1], value.intValue());
        Tuple<BigDecimal, int[]> returnValue = new Tuple<BigDecimal, int[]>(BigDecimal.ZERO, dispensed);

        // Remove notes from dispenser
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] -= dispensed[i];
        }
        returnValue = new Tuple<BigDecimal, int[]>(new BigDecimal(getTotalValue(dispensed)), dispensed);
        // Dispense successful
        return returnValue;
    }

    // Dispense this value. Return Boolean to indicate success and BigDecimal to
    // reflect the amount deposited.
    public Tuple<Boolean, BigDecimal> deposit(int[] depositAmounts)
            throws InputMismatchException, IllegalArgumentException {
        if (depositAmounts.length != dispenserAmounts[0].length) {
            throw new InputMismatchException(
                    "Input array size must be equal to ATM dispenser array size. Expected size: "
                            + dispenserAmounts[0].length);
        }
        int totalValue = 0;
        Tuple<Boolean, BigDecimal> returnTuple = new Tuple<Boolean, BigDecimal>(false, BigDecimal.ZERO);
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

    // Algorithm for making change, given an array of denominations, limits per each
    // denomination, and a value.
    private int[] makeChange(int[] denominations, int[] limits, int value)
            throws InputMismatchException, InsufficientNotesException {

        // We have an error. Sizes do not match. Print error and exit.
        if (limits.length != denominations.length)
            throw new InputMismatchException("Denominations and Limits list must be of the same size.");

        int[] values = new int[denominations.length];

        for (int i = denominations.length - 1; i >= 0; i--) {
            if (value == 0)
                values[i] = 0;
            else {
                int denomination = denominations[i];
                int sub = value / denomination;
                int current = limits[i];

                sub = Math.min(current, sub);
                // sub = current.min(sub);
                value -= denomination * sub; // updating the value.
                values[i] = sub; // Set to sub to get the bills used.
            }
        }

        if (value != 0) {
            throw new InsufficientNotesException("Insufficient notes remaining in ATM to dispense this amount.");
        }

        // Everything works. Return the values.
        return values;

    }

    public void printRemainingBills() {
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            System.out.println(
                    "Number of " + dispenserAmounts[0][i] + " denomination banknotes left: " + dispenserAmounts[1][i]);
        }
    }

    public int[] getBills() {
        return dispenserAmounts[1];
    }

}
