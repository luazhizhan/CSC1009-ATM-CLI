package Screen;

import org.junit.jupiter.api.Test;

import Account.Card;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CardPromptTest {
    private static final String VISA = "4628947672604457";
    private static final String MASTER_CARD = "5584697415528310";

    @Test
    public void success() {
        ScreenState cardPrompt = new CardPrompt();
        ScreenStateContext stateContext = new ScreenStateContext();
        cardPrompt.printScreen(stateContext);

        Card card = new Card();

        // Set scanner input value VISA
        System.setIn(new ByteArrayInputStream(VISA.getBytes()));
        Scanner in = new Scanner(System.in);
        ((CardPrompt) cardPrompt).getCardNumber(in, card);
        assertEquals(VISA, card.getCardNumber());
        in.close(); // Clear scanner buffer

        // Set scanner input value MASTER_CARD
        card = new Card();
        System.setIn(new ByteArrayInputStream(MASTER_CARD.getBytes()));
        in = new Scanner(System.in);
        ((CardPrompt) cardPrompt).getCardNumber(in, card);
        assertEquals(MASTER_CARD, card.getCardNumber());
        in.close();
    }

    @Test
    public void failureInvalidCardNumber() {
        ScreenState cardPrompt = new CardPrompt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Card card = new Card();
        System.setIn(new ByteArrayInputStream("abcdefg".getBytes()));
        Scanner in = new Scanner(System.in);
        ((CardPrompt) cardPrompt).getCardNumber(in, card);
        assertTrue(outContent.toString().contains("Invalid Account Card Number Format!"));

        System.setIn(new ByteArrayInputStream("123456789".getBytes()));
        ((CardPrompt) cardPrompt).getCardNumber(in, card);
        assertTrue(outContent.toString().contains("Invalid Account Card Number Format!"));
        in.close();
    }

}
