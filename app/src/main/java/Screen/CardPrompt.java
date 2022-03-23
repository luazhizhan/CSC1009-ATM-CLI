package Screen;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Card;

public class CardPrompt implements ScreenState {
    private String prompt;

    public CardPrompt() {
        prompt = "\n" + line + "\nPlease Enter Your Card Number:\nEnter 0 to quit."
                + "\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public boolean getCardNumber(Scanner in, Card card) {
        try {
            String cardNum = in.nextLine();

            if (cardNum.compareToIgnoreCase("0") == 0) {
                System.out.println("Quit");
                System.exit(0);
            }
            card.setCardNumber(cardNum);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return false;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return false;
        }
    }
}
