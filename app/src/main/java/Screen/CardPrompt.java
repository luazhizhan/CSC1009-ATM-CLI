package Screen;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Card;
import DataSource.DataSource;

public class CardPrompt implements ScreenState {
    private String prompt;

    public CardPrompt() {
        prompt = "\n" + line + "\nPlease enter your card number.\nEnter 0 to exit."
                + "\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Card getCardNumber(Scanner in, DataSource<Card> ds) {
        try {
            String cardNum = in.nextLine();

            // Exit program if 0
            if (cardNum.compareToIgnoreCase("0") == 0) {
                System.out.println("Exit");
                System.exit(0);
            }

            // Validate card number
            Card.validateCardNumber(cardNum);

            // Check if card exist in the system.
            Card card = ds.getDataById(cardNum);
            if (card == null) {
                System.out.println("\n" + line + "\n"
                        + "Card not found on the system! " +
                        "Please enter your card number.\n" + line);
            }

            return card;
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
