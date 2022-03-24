package Screen;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Atm.Atm;
import Atm.InsufficientNotesException;
import Helper.Pair;

public class Withdraw implements ScreenState {
    private String prompt;

    public Withdraw() {
        prompt = "\n" + line + "\nCash Withdrawal\n" + line + "\nPlease Enter Amount to Withdraw in Multiples of 10. "
                + "Enter 0 to go back to option screen.\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Pair<Integer> getWithdrawalAmount(Scanner in, Atm atm, Account account) {
        try {
            int amtInt = in.nextInt();
            if (amtInt == 0) {
                return new Pair<Integer>(0, 0);
            }

            BigDecimal amt = new BigDecimal(amtInt);

            // Validate amount with ATM notes, withdraw limit and
            // avaliable balance
            atm.checkWithdrawAmount(amt);
            account.checkWithdrawLimit(amt);
            account.checkAvaliableBalance(amt);

            // Withdraw from ATM and account
            Pair<Integer> notesPair = atm.withdraw(amt);
            account.subtractAvaliableBalance(amt);

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
