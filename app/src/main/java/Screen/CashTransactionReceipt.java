package Screen;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Helper.Pair;
import Transaction.CashTransaction;
import Transaction.CashTransaction.TransactionType;

/**
 * Print Cash Transaction Receipt screen class
 */
public class CashTransactionReceipt implements ScreenState {
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
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * User input whether they need a receipt or not
     * Print available balance if they don't need one.
     * 
     * @param in
     * @param account
     * @param notesPair
     * @param amt
     * @return
     */
    public boolean getSelectedOption(Scanner in, Account account, Pair<Integer> notesPair, BigDecimal amt) {
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
                System.out.println(String.format("$10 dollars note(s): %d", notesPair.first()));
                System.out.println(String.format("$50 dollars note(s): %d", notesPair.second()));

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
            System.out.println(ScreenState.invalidInput + "\n" + prompt);
            return false;
        }
    }
}
