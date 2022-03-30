package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.AccountStatus;
import Account.CurrentAccount;
import Atm.Atm;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Helper.Pair;
import Transaction.CashTransaction;
import Transaction.Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class DepositTest {
    private Atm atm;
    private Account account;
    private static DataSource<Transaction> txnDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        atm = new Atm();
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL);
        account.setAvailableBalance(new BigDecimal(10000));
        ((CurrentAccount) account).setWithdrawLimit(new BigDecimal(1000));
        ((CurrentAccount) account).setOverDraftLimit(new BigDecimal(100));
        txnDataSource = new TransactionDataSource();
    }

    @Test
    public void success() {
        ScreenState deposit = new Deposit();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(deposit);

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        String input = "1" + System.getProperty("line.separator") + "2";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnDataSource);
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());

        // Deposit 110
        Transaction txn = txns.get(0);
        assertEquals(new BigDecimal(110), txn.getAmount());
        assertEquals(CashTransaction.TransactionType.DEPOSIT, ((CashTransaction) txn).getType());

        // Validate dispense notes
        assertEquals(1, notes.first()); // 10 dollars notes
        assertEquals(2, notes.second()); // 50 dollars notes

        // validate new balance of account
        assertEquals(new BigDecimal(10110), account.getAvailableBalance());

        // validate new balance of notes in the ATM
        assertEquals(301, atm.getNumOf10DollarsNotes());
        assertEquals(302, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureInvalidDepositAmount() {
        ScreenState deposit = new Deposit();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        String input = "0" + System.getProperty("line.separator") + "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Please deposit at least one note"));
        assertNull(notes); // nothing to be dispense

        // validate new balance of notes in the ATM
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ScreenState deposit = new Deposit();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        String input = "abc" + System.getProperty("line.separator") + "3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        input = "3" + System.getProperty("line.separator") + "3.3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        in = new Scanner(System.in);
        notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(notes);
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();

    }
}
