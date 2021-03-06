package View;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.Data;
import Model.Account.Card;

/**
 * Prompt card number input view class
 */
public class CardPrompt implements ViewState {
    private String prompt;

    public CardPrompt() {
        prompt = "\n" + line + "\nPlease enter your card number.\nEnter 0 to exit."
                + "\n" + line;
    }

    @Override
    public void print(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Get user's card number
     * 
     * @param in
     * @param ds
     * @return Card object
     */
    public Card getCardNumber(Scanner in, Data<Card> ds) {
        try {
            String cardNum = in.nextLine();

            // Exit program if 0
            if (cardNum.compareToIgnoreCase("0") == 0) {
                System.out.println("Exit");
                System.exit(0); // Terminate program
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
            System.out.println(ViewState.invalidInput);
            return null;
        }
    }
}
