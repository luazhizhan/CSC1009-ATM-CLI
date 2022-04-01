package View;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.Data;
import Model.Account.Account;
import Model.Account.InvalidAccountException;
import Model.Transaction.Transaction;
import Model.Transaction.TransferTransaction;

/**
 * Bank transfer screen class
 */
public class Transfer implements ViewState {

    private String prompt;

    public Transfer() {
        prompt = "\n" + line + "\nBank Transfer\n" + line;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Get and validate input amount and perform bank transfer
     * 
     * @param in
     * @param account
     * @param accDataSource
     * @param txnDataSource
     * @return amount transferred
     */
    public BigDecimal getTransferAmt(Scanner in, Account account, Data<Account> accDataSource,
            Data<Transaction> txnDataSource) {
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

            // Must be above 0
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
            System.out.println(ViewState.invalidInput);
            return null;
        }
    }

}