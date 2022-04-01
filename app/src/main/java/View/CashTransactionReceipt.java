package View;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Model.Account.Account;
import Model.Transaction.CashTransaction;
import Model.Transaction.CashTransaction.TransactionType;

/**
 * Print Cash Transaction Receipt screen class
 */
public class CashTransactionReceipt implements ViewState {
    private String prompt;
    private CashTransaction.TransactionType type;
    private String header;

    public CashTransactionReceipt(CashTransaction.TransactionType type) {

        // Print title according to cash TransactionType
        if (type.compareTo(TransactionType.DEPOSIT) == 0) {
            header = "Cash Deposit Receipt";
        } else {
            header = "Cash Withdraw Receipt";
        }

        prompt = "\n" + line + "\n" + header
                + "\n\nDo you want a printed receipt\n1.  Print Receipt\n2.  No\n" + line;
        this.type = type;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * User input whether they need a receipt or not
     * Print available balance if they don't need one.
     * 
     * @param in
     * @param account
     * @param notesArray
     * @param amt
     * @return
     */
    public boolean getSelectedOption(Scanner in, Account account, int[] notesArray, BigDecimal amt) {
        try {
            int option = in.nextInt();

            // Invalid option
            if (option < 1 || option > 2) {
                System.out.println("\n" + line + "\nNo such option available! Please try again.\n"
                        + line + "\n" + prompt);
                return false;
            }

            // Format currency
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String balanceStr = formatter.format(account.getAvailableBalance());

            if (option == 1) { // Print receipt
                String amtStr = formatter.format(amt);
                System.out.println("\n" + line + "\n" + header + "\n");
                int[] denominations = account.getCurrency().getBanknotes();
                String currencyAcronym = account.getCurrency().getCurrencyAcronym();
                for (int i = 0; i < notesArray.length; i++) {
                    System.out.println(
                            String.format("%d %s note(s): %d", denominations[i], currencyAcronym, notesArray[i]));
                }

                // Print according to cash transaction type
                if (type.compareTo(TransactionType.DEPOSIT) == 0) {
                    System.out.println(String.format("Amount deposited: %s", amtStr));
                } else {
                    System.out.println(String.format("Amount withdrawn: %s", amtStr));
                }
                System.out.println(String.format("Available balance: %s", balanceStr));

            } else { // Print available balance only
                System.out.println("\n" + line + "\nAvailable balance: " + balanceStr);
            }
            System.out.println("\nThank You For Banking With Us!\n" + line);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(ViewState.invalidInput + "\n" + prompt);
            return false;
        }
    }
}
