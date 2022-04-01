package View;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.Data;
import Data.TransactionData;
import Model.Account.Account;
import Model.Transaction.Transaction;
import Model.Transaction.TransferTransaction;

/**
 * Print Transfer Transaction Receipt view class
 */
public class TransferTransactionReceipt implements ViewState {
    private String prompt;

    public TransferTransactionReceipt() {
        prompt = "\n" + line +
                "\n\nDo you want a printed receipt\n" +
                "1.  Print Receipt\n2.  No\n" + line;
    }

    @Override
    public void print(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * User input whether they need a receipt or not
     * Print available balance if they don't need one.
     * 
     * @param in
     * @param account
     * @param ds
     * @param amt
     * @return
     */
    public boolean getSelectedOption(Scanner in, Account account, Data<Transaction> ds, BigDecimal amt) {
        try {
            // Get latest transaction, which is the transfer transaction
            Transaction txn = ((TransactionData) ds)
                    .getDataByAccountId(account.getId()).get(0);
            int option = in.nextInt();

            // Invalid options
            if (option < 1 || option > 2) {
                System.out.println("\n" + line + "\nNo such option available! Please try again.\n"
                        + line + "\n" + prompt);
                return false;
            }

            // Format currency
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String balanceStr = formatter.format(account.getAvailableBalance());
            System.out.println(line + "\nTransfer Receipt\n");

            if (option == 1) { // Print receipt
                String amtStr = formatter.format(amt);
                System.out.println(String.format("To Account Id: %s", ((TransferTransaction) txn).getToAccountId()));
                System.out.println(String.format("Amount sent: %s", amtStr));
                System.out.println(String.format("Available balance: %s", balanceStr));
            } else { // Print balance only
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
