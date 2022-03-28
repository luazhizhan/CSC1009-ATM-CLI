package Screen;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Atm.Atm;
import DataSource.DataSource;
import Helper.Tuple;
import Transaction.CashTransaction;
import Transaction.Transaction;

public class Deposit implements ScreenState {

    private String prompt;

    public Deposit() {
        prompt = "\n" + line + "\nCash Deposit\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Tuple<BigDecimal, int[]> getDepositAmt(Scanner in, Atm atm, Account account, DataSource<Transaction> ds) {
        try {

            int[] currencyDenominations = account.getCurrency().getBanknotes();
            int[] depositAmounts = new int[currencyDenominations.length];
            int counter = 0;
            for (int i = 0; i < currencyDenominations.length; i++) {
                System.out.println(
                        "\n" + line + "\nPlease enter number of "
                                + currencyDenominations[i] + " dollars notes." +
                                "\nEnter < 0 to go back options screen.\n" + line);
                depositAmounts[i] = in.nextInt();
                counter += depositAmounts[i];
            }
            if (counter < 0) { // Return to main option screen if below zero
                return new Tuple<BigDecimal, int[]>(BigDecimal.ZERO, new int[0]);
            }

            // Deposit to ATM and account
            BigDecimal amt = atm.deposit(depositAmounts);
            account.addAvailableBalance(amt);

            // Create record of transaction
            Transaction txn = new CashTransaction(account.getId(), amt, atm.getId(),
                    CashTransaction.TransactionType.DEPOSIT);
            ds.add(txn);

            return new Tuple<BigDecimal, int[]>(amt, depositAmounts);

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }

}