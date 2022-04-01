package View;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.Data;
import Model.Account.Account;
import Model.Account.Card;

/**
 * Get account pin from user screen class
 */
public class PinPrompt implements ViewState {
    private String prompt;
    private int tries;

    public PinPrompt() {
        prompt = "\n" + line + "\nPlease enter your PIN\nEnter 0 to exit.\n" + line;
        tries = 0;
    }

    /**
     * Get number of tries performed by user
     * 
     * @return
     */
    protected int getTries() {
        return tries;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Get pin number from user and validates it
     * 
     * @param in
     * @param card
     * @param ds
     * @return Account object
     */
    public Account getPinNumber(Scanner in, Card card, Data<Account> ds) {
        try {
            int pinNumber = in.nextInt();

            // Terminate program
            if (pinNumber == 0) {
                System.out.println("Exit");
                System.exit(0);
            }

            if (card.checkPinNumber(pinNumber) == false) {
                this.tries += 1; // Increase retries count

                // Terminal program after 3 tries of incorrect PIN
                switch (this.tries) {
                    case 3: // Terminate program
                        System.out.println(
                                "\n" + line + "\nToo Many Attempts!\nPlease Contact Bank For Assistance!\n" + line);
                        System.exit(0);
                    default:
                        System.out.println("\n" + line + "\nIncorrect PIN!\nPlease re-enter your PIN!\n" + line);
                        return null;
                }
            }
            return ds.getDataById(card.getAccountId());
        } catch (NoSuchElementException e) {
            System.out.println(ViewState.invalidInput);
            return null;
        }
    }
}
