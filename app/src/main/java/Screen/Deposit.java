package Screen;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Atm.Atm;
import DataSource.DataSource;
import Helper.Pair;
import Transaction.CashTransaction;
import Transaction.Transaction;

/**
 * Get deposit amount and process it screen class
 */
public class Deposit implements ScreenState {

    private String prompt;

    public Deposit() {
        prompt = "\n" + line + "\nCash Deposit\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Get deposit amount from user and process it
     * 
     * @param in
     * @param atm
     * @param account
     * @param ds
     * @return Pair<Integer>(numOf10DollarsNotes, numOf50DollarsNotes)
     */
    public Pair<Integer> getDepositAmt(Scanner in, Atm atm, Account account, DataSource<Transaction> ds) {
        try {

            // Number of 10 dollar notes
            System.out.println("\n" + line + "\nPlease enter number of 10 dollars notes." +
                    "\nEnter < 0 to go back options screen.\n" + line);
            int numOf10DollarNotes = in.nextInt();
            if (numOf10DollarNotes < 0) { // Return to main option screen if below zero
                return new Pair<Integer>(0, 0);
            }

            // Number of 50 dollar notes
            System.out.println("\n" + line + "\nPlease enter number of 50 dollars notes:" +
                    "\nEnter < 0 to go back options screen.\n" + line);
            int numOf50DollarNotes = in.nextInt();
            if (numOf50DollarNotes < 0) { // Return to main option screen if below zero
                return new Pair<Integer>(0, 0);
            }

            // Deposit to ATM and account
            BigDecimal amt = atm.deposit(numOf10DollarNotes, numOf50DollarNotes);
            account.addAvailableBalance(amt);

            // Create record of transaction
            Transaction txn = new CashTransaction(account.getId(), amt, atm.getId(),
                    CashTransaction.TransactionType.DEPOSIT);
            ds.add(txn);

            return new Pair<Integer>(numOf10DollarNotes, numOf50DollarNotes);

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }

}
