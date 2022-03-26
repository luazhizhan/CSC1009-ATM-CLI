package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.Card;
import Account.CurrentAccount;
import DataSource.AccountDataSource;
import DataSource.DataSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class PinPromptTest {
    private Card card;
    private static final int PIN = 123456;
    private static final String ACCOUNT_ID = "6454856238";
    private DataSource<Account> accountDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        card = new Card();
        card.setPinNumber(PIN);
        card.setAccountId(ACCOUNT_ID);
        accountDataSource = new AccountDataSource();
    }

    @Test
    public void success() {

        ScreenState pinPrompt = new PinPrompt();
        ScreenStateContext stateContext = new ScreenStateContext();
        pinPrompt.printScreen(stateContext);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream(String.valueOf(PIN).getBytes()));
        Scanner in = new Scanner(System.in);

        Account acc = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        assertEquals(ACCOUNT_ID, acc.getId());
        assertEquals(new BigDecimal(33000), acc.getAvailableBalance());
        assertTrue(acc instanceof CurrentAccount);
        in.close();
    }

    @Test
    public void failureIncorrectPin() {
        ScreenState pinPrompt = new PinPrompt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("234567".getBytes()));
        Scanner in = new Scanner(System.in);
        ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        assertTrue(outContent.toString().contains("Incorrect PIN!"));
        assertEquals(1, ((PinPrompt) pinPrompt).getTries());
        in.close();

        System.setIn(new ByteArrayInputStream("234567".getBytes()));
        in = new Scanner(System.in);
        ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        assertTrue(outContent.toString().contains("Incorrect PIN!"));
        assertEquals(2, ((PinPrompt) pinPrompt).getTries());
        in.close();

        System.setIn(new ByteArrayInputStream("234567".getBytes()));
        in = new Scanner(System.in);
        ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        in.close();

        // Prove that program has exited
        assertFalse(true);
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
        Account acc = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(acc);
        in.close();

        System.setIn(new ByteArrayInputStream("5g6".getBytes()));
        in = new Scanner(System.in);
        acc = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(acc);
        in.close();

        System.setIn(new ByteArrayInputStream("74343g".getBytes()));
        in = new Scanner(System.in);
        acc = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(acc);
        in.close();
    }
}
