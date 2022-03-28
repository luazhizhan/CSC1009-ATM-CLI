package Screen;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Atm.Atm;
import Atm.InsufficientNotesException;
import DataSource.DataSource;
import Helper.Tuple;
import Transaction.CashTransaction;
import Transaction.Transaction;

public class Withdraw implements ScreenState {
    private String prompt;

    public Withdraw() {
        prompt = "\n" + line + "\nCash Withdrawal\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Tuple<BigDecimal, int[]> getWithdrawalAmount(Scanner in, Atm atm, Account account,
            DataSource<Transaction> ds) {
        try {

            System.out.println("\n" + line + "\nPlease Enter Amount to Withdraw in Multiples of 10."
                    + "\nEnter 0 to go back to option screen.\n" + line);
            int amtInt = in.nextInt();
            if (amtInt == 0) {
                return new Tuple<BigDecimal, int[]>(BigDecimal.ZERO, new int[0]);
            }

            BigDecimal amt = new BigDecimal(amtInt);

            // Validate amount with ATM notes, withdraw limit and
            // available balance
            // atm.checkWithdrawAmount(amt);
            account.checkAgainstWithdrawLimit(amt);
            account.checkAgainstAvailableBalance(amt);

            // Withdraw from ATM and account
            Tuple<BigDecimal, int[]> withdrawResult = atm.withdraw(amt);
            account.subtractAvailableBalance(amt);

            // Create record of transaction
            Transaction txn = new CashTransaction(account.getId(), amt, atm.getId(),
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
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
