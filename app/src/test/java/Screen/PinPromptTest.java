package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Card;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class PinPromptTest {
    private Card card;
    private int pin;

    @BeforeEach
    public void setUp() {
        card = new Card();
        int pin = 123456;
        card.setPinNumber(pin);
    }

    @Test
    public void success() {

        ScreenState pinPrompt = new PinPrompt();
        ScreenStateContext stateContext = new ScreenStateContext();
        pinPrompt.printScreen(stateContext);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);

        System.setIn(new ByteArrayInputStream(String.valueOf(pin).getBytes()));
        ((PinPrompt) pinPrompt).getPinNumber(in, card);
        in.close();
    }

    @Test
    public void failureIncorrectPin() {
        ScreenState pinPrompt = new PinPrompt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("234567".getBytes()));

        Scanner in = new Scanner(System.in);
        ((PinPrompt) pinPrompt).getPinNumber(in, card);
        assertTrue(outContent.toString().contains("Incorrect PIN!"));
        assertEquals(1, ((PinPrompt) pinPrompt).getTries());
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ScreenState pinPrompt = new PinPrompt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("abc".getBytes()));

        Scanner in = new Scanner(System.in);
        ((PinPrompt) pinPrompt).getPinNumber(in, card);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));

        System.setIn(new ByteArrayInputStream("5g6".getBytes()));
        ((PinPrompt) pinPrompt).getPinNumber(in, card);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));

        System.setIn(new ByteArrayInputStream("74343g".getBytes()));
        ((PinPrompt) pinPrompt).getPinNumber(in, card);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }
}
