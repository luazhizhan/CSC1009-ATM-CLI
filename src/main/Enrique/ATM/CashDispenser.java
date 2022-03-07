package src.main.Enrique.ATM;

import src.main.Enrique.Algos.CountMoneyAlgo;
import src.main.Enrique.Helpers.Tuple;

// Dispenses $. Do bills first.
public class CashDispenser {

    // Depending on ATM location, load appropriate bill values. Location infers currency used, and thus which configuration of banknotes to handle.
    // if location == Singapore,
    // bill types = 2, 5, 10, 50, 100, 1000
    // If location == EU?
    // types = 5, 10, 20, 50, 100, 200, 500
    // etc.
    
   // the default initial number of bills in the cash dispenser, arbitary value, temporary.
   private final int INITIAL_COUNT = 500;

    // Key - banknote face value
    // Value - Total number of that banknote type remaining in the ATM.
    // int[0] - banknote face value
    // int[1] - number of banknotes
    int[][] dispenserAmounts;

    // Instead of location, give it the currency code imo. Can pass that on ATM init.
    public CashDispenser(String loc) {
        
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
        Tuple<Boolean, int[]> dispensed = CountMoneyAlgo.countCurrency(dispenserAmounts[0], value);
        
        // Signal that the dispenser cannot dispense the input amount
        if(dispensed.x == false) return false;

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
        CashDispenser disp = new CashDispenser("SINGAPORE");
        disp.dispenseMoney(13636);
        disp.printStatus();
    }



    // // Can this machine dispense the given amount- has sufficient bills?
    // public boolean canDispense(int amount) {
    //     // int billsRequired = amount / 20; // number of $20 bills required

    //     return true;
    //     // if (count >= billsRequired)
    //     //     return true; // enough bills available
    //     // else 
    //     //     return false; // not enough bills available
    // }

}

