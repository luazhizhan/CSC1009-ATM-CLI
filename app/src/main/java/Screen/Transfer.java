package Screen;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Account.InvalidAccountException;
import DataSource.DataSource;
import Transaction.Transaction;
import Transaction.TransferTransaction;

public class Transfer implements ScreenState {

    private String prompt;

    public Transfer() {
        prompt = "\n" + line + "\nCash Deposit\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public BigDecimal getTransferAmt(Scanner in, Account account, DataSource<Account> accDataSource,
            DataSource<Transaction> txnDataSource) {
        try {
            System.out.println("\n" + line + "\nPlease enter beneficary account number." +
                    "\nEnter 0 to go back to options screen.\n" + line);
            String toAccountId = in.nextLine();

            // Return to option screen if 0
            if (toAccountId.compareToIgnoreCase("0") == 0)
                return BigDecimal.ZERO;

            // Restart if account number is the same as transferring account number
            if (account.getId().compareToIgnoreCase(toAccountId) == 0) {
                System.out.println("\n" + line + "\n"
                        + "Cannot transfer to own account!\n" + line);
                return null;
            }

            // Restart if account not found
            Account toAccount = accDataSource.getDataById(toAccountId);
            if (toAccount == null) {
                System.out.println("\n" + line + "\n"
                        + "Account not found!\n" + line);
                return null;
            }

            System.out.println("\n" + line + "\nPlease enter amount to transfer." +
                    "\nEnter 0 to go back to options screen.\n" + line);
            String amtStr = in.nextLine();

            // Return to option screen if 0
            if (amtStr.compareToIgnoreCase("0") == 0)
                return BigDecimal.ZERO;

            BigDecimal amt = new BigDecimal(amtStr);

            if (amt.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("\n" + line + "\n"
                        + "Amount must be > 0!\n" + line);
                return null;
            }

            System.out.println("\n" + line + "\nPlease enter your message." +
                    "\nEnter 0 to go back to options screen.\n" + line);
            String message = in.nextLine();

            // Return to option screen if 0
            if (message.compareToIgnoreCase("0") == 0)
                return BigDecimal.ZERO;

            // Validate against receiving account status, transfer limit and
            // available balance to perform the transfer
            toAccount.checkReceivingAccountStatus();
            account.transferAvailableBalance(amt);
            toAccount.addAvailableBalance(amt);

            // Create record of transaction
            Transaction txn = new TransferTransaction(account.getId(), toAccount.getId(), amt);
            ((TransferTransaction) txn).setMessage(message);
            txnDataSource.add(txn);

            return amt;
        } catch (IllegalArgumentException | InvalidAccountException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }

}
