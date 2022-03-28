package Screen;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Transaction.CashTransaction;
import Transaction.CashTransaction.TransactionType;

public class CashTransactionReceipt implements ScreenState {
    private String prompt;
    private CashTransaction.TransactionType type;
    private String header;

    public CashTransactionReceipt(CashTransaction.TransactionType type) {
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

    public boolean getSelectedOption(Scanner in, Account account, int[] notesPair, BigDecimal amt) {
        try {
            int option = in.nextInt();
            if (option < 1 || option > 2) {
                System.out.println("\n" + line + "\nNo such option available! Please try again.\n"
                        + line + "\n" + prompt);
                return false;
            }
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String balanceStr = formatter.format(account.getAvailableBalance());

            if (option == 1) {
                String amtStr = formatter.format(amt);
                System.out.println("\n" + line + "\n" + header + "\n");
                for (int i = 0; i < notesPair.length; i++) {
                    System.out.println(String.format("$10 dollars note(s): %d", notesPair[i]));
                }

                // Print according to cash transaction type
                if (type.compareTo(TransactionType.DEPOSIT) == 0) {
                    System.out.println(String.format("Amount deposited: %s", amtStr));
                } else {
                    System.out.println(String.format("Amount withdrawn: %s", amtStr));
                }
                System.out.println(String.format("Available balance: %s", balanceStr));
            } else {
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