package View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Data.CardsData;
import Data.Data;
import Model.Account.Card;

public class CardPromptTest {
    private static final String VISA = "4071666471445613";
    private static final String MASTER_CARD = "5424053513915781";
    private Data<Card> cardDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        cardDataSource = new CardsData();
    }

    @Test
    public void success() {
        ViewState cardPrompt = new CardPrompt();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrintScreen(cardPrompt);

        // Set scanner input value VISA
        System.setIn(new ByteArrayInputStream(VISA.getBytes()));
        Scanner in = new Scanner(System.in);
        Card card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);
        assertEquals(VISA, card.getCardNumber());
        in.close(); // Clear scanner buffer

        // Set scanner input value MASTER_CARD
        System.setIn(new ByteArrayInputStream(MASTER_CARD.getBytes()));
        in = new Scanner(System.in);
        card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);
        assertEquals(MASTER_CARD, card.getCardNumber());
        in.close();
    }

    @Test
    public void failureInvalidCardNumber() {
        ViewState cardPrompt = new CardPrompt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("abcdefg".getBytes()));
        Scanner in = new Scanner(System.in);
        Card card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);
        assertNull(card);
        assertTrue(outContent.toString().contains("Invalid Account Card Number Format!"));
        in.close();

        System.setIn(new ByteArrayInputStream("123456789".getBytes()));
        in = new Scanner(System.in);
        card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);
        assertNull(card);
        assertTrue(outContent.toString().contains("Invalid Account Card Number Format!"));
        in.close();
    }

    @Test
    public void failureCardNotFound() {
        ViewState cardPrompt = new CardPrompt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Valid MasterCard that does not exist in the system
        System.setIn(new ByteArrayInputStream("5108212607219814".getBytes()));
        Scanner in = new Scanner(System.in);
        Card card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);
        assertNull(card);
        assertTrue(outContent.toString().contains("Card not found on the system!"));

        // Valid Visa that does not exist in the system
        System.setIn(new ByteArrayInputStream("4718258769126946".getBytes()));
        card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);
        assertNull(card);
        assertTrue(outContent.toString().contains("Card not found on the system!"));
        in.close();
    }
}
