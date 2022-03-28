package Screen;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Account;
import Account.Card;
import DataSource.DataSource;

public class PinPrompt implements ScreenState {
    private String prompt;
    private int tries;

    public PinPrompt() {
        prompt = "\n" + line + "\nPlease enter your PIN\nEnter 0 to exit.\n" + line;
        tries = 0;
    }

    public int getTries() {
        return tries;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Account getPinNumber(Scanner in, Card card, DataSource<Account> ds) {
        try {
            int pinNumber = in.nextInt();

            // Terminal program
            if (pinNumber == 0) {
                System.out.println("Exit");
                System.exit(0);
            }

            if (card.checkPinNumber(pinNumber) == false) {
                this.tries += 1;

                // Terminal program after 3 tries of incorrect PIN
                switch (this.tries) {
                    case 3:
                        System.out.println(
                                "\n" + line + "\nToo Many Attempts!\nPlease Contact Bank For Assistance!\n" + line);
                        System.exit(0); // Terminal program
                    default:
                        System.out.println("\n" + line + "\nIncorrect PIN!\nPlease re-enter your PIN!\n" + line);
                        return null;
                }
            }
            return ds.getDataById(card.getAccountId());
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
