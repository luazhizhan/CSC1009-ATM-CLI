package View;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.Data;
import Helper.Tuple;
import Model.Account.Account;
import Model.Atm.Atm;
import Model.Atm.InsufficientNotesException;
import Model.Transaction.CashTransaction;
import Model.Transaction.Transaction;

/**
 * Cash withdraw screen class
 */
public class Withdraw implements ViewState {
    private String prompt;

    public Withdraw() {
        prompt = "\n" + line + "\nCash Withdrawal\n" + line;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    public Tuple<BigDecimal, int[]> getWithdrawalAmount(Scanner in, Atm atm, Account account,
            Data<Transaction> ds) {
        try {

            System.out.println("\n" + line + "\nPlease Enter Amount to Withdraw in Multiples of 10."
                    + "\nEnter 0 to go back to option screen.\n" + line);
            int amtInt = in.nextInt();

            // Return to main options
            if (amtInt == 0) {
                return new Tuple<BigDecimal, int[]>(BigDecimal.ZERO, new int[0]);
            }

            BigDecimal atmInputAmount = new BigDecimal(amtInt);
            BigDecimal convertedAccountAmount = BigDecimal.ZERO;
            // Currency mismatch. For withdrawal purposes,
            // convert from atm currency to account, and respect atm limits.
            if (atm.getCurrency() != account.getCurrency()) {
                convertedAccountAmount = atmInputAmount
                        .multiply(atm.getCurrency().findExchangeRate(account.getCurrency()).getRate());
            }

            // Validate amount against ATM notes, withdraw limit and available balance
            atm.checkAgainstATMWithdrawLimit(atmInputAmount);
            // Use converted amount if it's non-zero
            account.checkAgainstAvailableBalance(
                    convertedAccountAmount.compareTo(BigDecimal.ZERO) == 0 ? atmInputAmount : convertedAccountAmount);

            // Withdraw from ATM and account
            Tuple<BigDecimal, int[]> withdrawResult = atm.withdraw(atmInputAmount);
            // Withdraw converted amount if it's non-zero
            account.withdrawAvailableBalance(
                    convertedAccountAmount.compareTo(BigDecimal.ZERO) == 0 ? atmInputAmount : convertedAccountAmount);

            // Create record of transaction
            Transaction txn = new CashTransaction(account.getId(), atmInputAmount, atm.getId(),
                    CashTransaction.TransactionType.WITHDRAW);
            ds.add(txn);

            return withdrawResult;
        } catch (InsufficientNotesException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ViewState.invalidInput);
            return null;
        }
    }
}
