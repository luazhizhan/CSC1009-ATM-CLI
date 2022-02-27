package src.main.java;



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
    int[][] dispenserAmounts;

    // Instead of location, give it the currency code imo. Can pass that on ATM init.
    public CashDispenser(String loc) {
        
        // Very gross but im tired atm, looks bad but it'll work
        dispenserAmounts = new int[2][CurrencyDatabase.banknotes.get(loc).getBanknotes().length];
        dispenserAmounts[0] = CurrencyDatabase.banknotes.get(loc).getBanknotes();

        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            dispenserAmounts[1][i] = INITIAL_COUNT;
        }
    }

    // Dispense this value.
    public void dispenseMoney(int value)
    {
        int[] dispenseCounts = GreedyFirstAlgo.countCurrency(value, dispenserAmounts[0]);

        // Print notes
        System.out.println("Currency Count ->");
        for (int i = 0; i < dispenserAmounts[0].length; i++) {
            if (dispenseCounts[i] != 0) {
                System.out.println(dispenserAmounts[0][i] + ": " + dispenseCounts[i]);
            }
        }
        // count -= billsRequired; // update the count of bills
    }

    // Can this machine dispense the given amount- has sufficient bills?
    public boolean canDispense(int amount)
    {
        int billsRequired = amount / 20; // number of $20 bills required

        return true;
        // if (count >= billsRequired)
        //     return true; // enough bills available
        // else 
        //     return false; // not enough bills available
    }

}

