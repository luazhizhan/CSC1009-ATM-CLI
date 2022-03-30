package Screen;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Atm.Atm;
import Atm.InsufficientNotesException;
import DataSource.DataSource;
import Helper.Pair;
import Transaction.CashTransaction;
import Transaction.Transaction;

/**
 * Cash withdraw screen class
 */
public class Withdraw implements ScreenState {
    private String prompt;

    public Withdraw() {
        prompt = "\n" + line + "\nCash Withdrawal\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Get withdraw amount from user and process it
     * 
     * @param in
     * @param atm
     * @param account
     * @param ds
     * @return Pair<Integer>(numOf10DollarsNotes, numOf50DollarsNotes)
     */
    public Pair<Integer> getWithdrawalAmount(Scanner in, Atm atm, Account account, DataSource<Transaction> ds) {
        try {

            System.out.println("\n" + line + "\nPlease Enter Amount to Withdraw in Multiples of 10."
                    + "\nEnter 0 to go back to option screen.\n" + line);
            int amtInt = in.nextInt();

            // Return to main options
            if (amtInt == 0) {
                return new Pair<Integer>(0, 0);
            }

            BigDecimal amt = new BigDecimal(amtInt);

            // Validate amount against ATM notes, withdraw limit and available balance
            atm.checkWithdrawAmount(amt);
            account.checkAgainstWithdrawLimit(amt);
            account.checkAgainstAvailableBalance(amt);

            // Withdraw from ATM and account
            Pair<Integer> notesPair = atm.withdraw(amt);
            account.withdrawAvailableBalance(amt);

            // Create record of transaction
            Transaction txn = new CashTransaction(account.getId(), amt, atm.getId(),
                    CashTransaction.TransactionType.WITHDRAW);
            ds.add(txn);

            return notesPair;
        } catch (InsufficientNotesException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
