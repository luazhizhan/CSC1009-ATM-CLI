package Screen;

import org.junit.jupiter.api.Test;

import Account.Card;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
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

        // TODO test system exit?
    }

}
