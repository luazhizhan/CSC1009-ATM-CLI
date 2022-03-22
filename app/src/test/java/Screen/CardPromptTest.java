package Screen;

import org.junit.jupiter.api.Test;

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

        // Set scanner input value VISA
        System.setIn(new ByteArrayInputStream(VISA.getBytes()));
        Scanner in = new Scanner(System.in);
        String cardNum = ((CardPrompt) cardPrompt).getCardNumber(in);
        assertEquals(VISA, cardNum); // Clear scanner buffer
        in.close();

        // Set scanner input value MASTER_CARD
        System.setIn(new ByteArrayInputStream(MASTER_CARD.getBytes()));
        in = new Scanner(System.in);
        cardNum = ((CardPrompt) cardPrompt).getCardNumber(in);
        assertEquals(MASTER_CARD, cardNum);
        in.close();

        // TODO test system exit?
    }

}
