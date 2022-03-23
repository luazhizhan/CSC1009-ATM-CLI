package Screen;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Account.Card;

public class CardPrompt implements ScreenState {
    private String prompt;

    public CardPrompt() {
        prompt = "\n" + line + "\nPlease Enter Your Card Number:\nEnter \"q\" to quit."
                + "\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public void getCardNumber(Scanner in, Card card) {
        try {
            String cardNum = in.nextLine();

            if (cardNum.compareToIgnoreCase("q") == 0) {
                System.out.println("Quit");
                System.exit(0);
            }
            card.setCardNumber(cardNum);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            in.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Invalid card number! Please try again.");
            in.nextLine(); // Clear scanner buffer
        }
    }
}
