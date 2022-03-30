package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.AccountStatus;
import Account.CurrentAccount;
import Atm.Atm;
import Country.Country;
import Currency.Currency;
import DataSource.CountryDataSource;
import DataSource.CurrencyDataSource;
import DataSource.DataSource;
import Transaction.CashTransaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class CashTransactionReceiptTest {
    private Atm atm;
    private Account account;
    private int[] notes;
    private BigDecimal amt;

    private DataSource<Country> countryDataSource = null;
    private DataSource<Currency> currencyDataSource = null;
    private Country singapore;
    private Currency sgd;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        countryDataSource = new CountryDataSource();
        currencyDataSource = new CurrencyDataSource();
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL,
                currencyDataSource.getDataById("SGD"));
        account.setAvailableBalance(new BigDecimal(30000));
        singapore = countryDataSource.getDataById("SGP");
        sgd = currencyDataSource.getDataById("SGD");
        notes = new int[] { 0, 0 };
        atm = new Atm(singapore, sgd);
        notes = new int[] { 2, 2 };
        amt = atm.deposit(notes);
    }

    // private void setNotesPairAndAmt(int numOf10DollarNotes, int
    // numOf50DollarsNotes) {
    // notes = new int[] { numOf10DollarNotes, numOf50DollarsNotes };
    // amt = atm.calculateNotesAmount(notes);
    // }

    @Test
    public void successDeposit() throws IOException {
        ScreenState receipt = new CashTransactionReceipt(CashTransaction.TransactionType.WITHDRAW);
        ScreenStateContext stateContext = new ScreenStateContext();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        stateContext.setAndPrintScreen(receipt);
        String contentString = outContent.toString();
        assertTrue(contentString.contains("Cash Withdraw Receipt"));
        outContent.close();

        // Read output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print receipt
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        contentString = outContent.toString();
        assertTrue(result);
        assertTrue(contentString.contains("$10 dollars note(s):"));
        assertFalse(contentString.contains("$50 dollars note(s):"));
        assertTrue(contentString.contains("Amount withdrawn:"));
        assertTrue(contentString.contains("Available balance:"));
        assertTrue(contentString.contains("Thank You For Banking With Us!"));
        in.close();
        outContent.close();

        // Clear and read output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print balance only
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        in = new Scanner(System.in);
        result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        contentString = outContent.toString();
        assertTrue(result);
        assertFalse(contentString.contains("$10 dollars note(s):"));
        assertTrue(contentString.contains("Available balance:"));
        assertTrue(contentString.contains("Thank You For Banking With Us!"));
        in.close();
    }

    @Test
    public void successWithdraw() throws IOException {
        ScreenState receipt = new CashTransactionReceipt(CashTransaction.TransactionType.DEPOSIT);
        ScreenStateContext stateContext = new ScreenStateContext();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        stateContext.setAndPrintScreen(receipt);
        String contentString = outContent.toString();
        assertTrue(contentString.contains("Cash Deposit Receipt"));
        outContent.close();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set and read System.out content
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print receipt
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        contentString = outContent.toString();
        assertTrue(result);
        assertTrue(contentString.contains("$10 dollars note(s):"));
        assertFalse(contentString.contains("$50 dollars note(s):"));
        assertTrue(contentString.contains("Amount deposited:"));
        assertTrue(contentString.contains("Available balance:"));
        assertTrue(contentString.contains("Thank You For Banking With Us!"));
        in.close();
        outContent.close();

        // Clear and read output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print balance only
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        in = new Scanner(System.in);
        result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        contentString = outContent.toString();
        assertTrue(result);
        assertFalse(contentString.contains("$10 dollars note(s):"));
        assertTrue(contentString.contains("Available balance:"));
        assertTrue(contentString.contains("Thank You For Banking With Us!"));
        in.close();
    }

    @Test
    public void failureNoSuchOption() {
        ScreenState receipt = new CashTransactionReceipt(CashTransaction.TransactionType.DEPOSIT);

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("-77".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("No such option available! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("8".getBytes()));
        in = new Scanner(System.in);
        result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("No such option available! Please try again."));
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ScreenState receipt = new CashTransactionReceipt(CashTransaction.TransactionType.DEPOSIT);

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("abc".getBytes()));

        Scanner in = new Scanner(System.in);
        boolean result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("5g6".getBytes()));
        in = new Scanner(System.in);
        result = ((CashTransactionReceipt) receipt).getSelectedOption(in, account, notes, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }
}
