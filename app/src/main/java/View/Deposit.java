package View;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.Data;
import Helper.Tuple;
import Model.Account.Account;
import Model.Atm.Atm;
import Model.Transaction.CashTransaction;
import Model.Transaction.Transaction;

/**
 * Get deposit amount and process it view class
 */
public class Deposit implements ViewState {

    private String prompt;

    public Deposit() {
        prompt = "\n" + line + "\nCash Deposit\n" + line;
    }

    @Override
    public void print(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Get deposit amount from user and process it
     * 
     * @param in
     * @param atm
     * @param account
     * @param ds
     * @return Tuple<BigDecimal, int[]>(total, depositedBillsArray)
     */
    public Tuple<BigDecimal, int[]> getDepositAmt(Scanner in, Atm atm, Account account, Data<Transaction> ds) {
        try {

            int[] currencyDenominations = account.getCurrency().getBanknotes();
            int[] depositAmounts = new int[currencyDenominations.length];
            int counter = 0;
            for (int i = 0; i < currencyDenominations.length; i++) {
                System.out.println(
                        "\n" + line + "\nPlease enter number of "
                                + currencyDenominations[i] + " dollars notes." +
                                "\nEnter < 0 to go back options view.\n" + line);
                depositAmounts[i] = in.nextInt();
                counter += depositAmounts[i];
            }
            if (counter < 0) { // Return to main option view if below zero
                return new Tuple<BigDecimal, int[]>(BigDecimal.ZERO, new int[0]);
            }

            // Deposit to ATM and account
            BigDecimal depositedAmount = atm.deposit(depositAmounts);
            BigDecimal convertedDepositedAmount = BigDecimal.ZERO;
            // Currency mismatch. For withdrawal purposes,
            // convert from atm currency to account, and respect atm limits.
            if (atm.getCurrency() != account.getCurrency()) {
                convertedDepositedAmount = depositedAmount
                        .multiply(atm.getCurrency().findExchangeRate(account.getCurrency()).getRate());
            }
            account.addAvailableBalance(
                    convertedDepositedAmount.compareTo(BigDecimal.ZERO) == 0 ? depositedAmount
                            : convertedDepositedAmount);

            // Create record of transaction
            Transaction txn = new CashTransaction(account.getId(), depositedAmount, atm.getId(),
                    CashTransaction.TransactionType.DEPOSIT);
            ds.add(txn);

            return new Tuple<BigDecimal, int[]>(depositedAmount, depositAmounts);

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ViewState.invalidInput);
            return null;
        }
    }

}
