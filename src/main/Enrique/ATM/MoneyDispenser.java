package src.main.Enrique.ATM;

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

        CountMoneyAlgo.setUpTable(dispenserAmounts[0]);

        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] = INITIAL_COUNT;
        }

        // Testing
        System.out.println("CashDispenser initialised.");
        printStatus();
    }

    // Dispense this value.
    // Current algo doesn't account for current number of notes in system.
    // Current algo gets a close number, not exact.
    // In essence its crap, and temporary so I can scaffold the rest of the code.
    public Boolean dispenseMoney(int value) {
        try {
            if (value % 10 != 0)
                throw new NumberFormatException("Input value is not a multiple of 10!");
        } catch (Exception e) {
            // TODO: handle exception
        }
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
        return true;
    }

    public void printStatus() {
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            System.out.println(dispenserAmounts[0][i] + ": " + dispenserAmounts[1][i]);
        }
    }

    public static void main(String[] args) {
        MoneyDispenser disp = new MoneyDispenser("SINGAPORE");

        boolean flag = false;

        Scanner reader = new Scanner(System.in);

        do {
            try {
                flag = false;
                System.out.println("Enter deposit amount: ");
                BigDecimal withdrawValue = reader.nextBigDecimal();
                account1.deposit(withdrawValue);

            } catch (NumberFormatException e) {
                reader.next();
                flag = true;
                System.out.println(e.getMessage());
                // e.printStackTrace();
            }
        } while (flag == true);

        disp.dispenseMoney(17636);
        disp.printStatus();
    }

    // // Can this machine dispense the given amount- has sufficient bills?
    // public boolean canDispense(int amount) {
    // // int billsRequired = amount / 20; // number of $20 bills required

    // return true;
    // // if (count >= billsRequired)
    // // return true; // enough bills available
    // // else
    // // return false; // not enough bills available
    // }

}
