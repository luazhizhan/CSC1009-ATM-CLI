package Screen;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Transaction.Transaction;
import Transaction.TransferTransaction;

public class TransferTransactionReceipt implements ScreenState {
    private String prompt;

    public TransferTransactionReceipt() {
        prompt = "\n" + line +
                "\n\nDo you want a printed receipt\n" +
                "1.  Print Receipt\n2.  No\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public boolean getSelectedOption(Scanner in, Account account, DataSource<Transaction> ds, BigDecimal amt) {
        try {
            // Get latest transaction, which is the transfer transaction
            Transaction txn = ((TransactionDataSource) ds)
                    .getDataByAccountId(account.getId()).get(0);
            int option = in.nextInt();
            if (option < 1 || option > 2) {
                System.out.println("\n" + line + "\nNo such option available! Please try again.\n"
                        + line + "\n" + prompt);
                return false;
            }
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
            System.out.println(ScreenState.invalidInput + "\n" + prompt);
            return false;
        }
    }
}
