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

public class WithdrawTest {
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
        ScreenState withdraw = new Withdraw();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(withdraw);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("120".getBytes()));
        Scanner in = new Scanner(System.in);

        Pair<Integer> notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        Transaction txn = txns.get(0);
        assertEquals(new BigDecimal(120), txn.getAmount());
        assertEquals(CashTransaction.TransactionType.WITHDRAW, ((CashTransaction) txn).getType());
        assertEquals(2, notes.first());
        assertEquals(2, notes.second());
        assertEquals(new BigDecimal(9880), account.getAvailableBalance());
        assertEquals(298, atm.getNumOf10DollarsNotes());
        assertEquals(298, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureInsufficientNotes() {
        ScreenState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        atm = new Atm(3, 3); // 180 dollars
        // Require 4 50 dollars note.
        System.setIn(new ByteArrayInputStream("200".getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Insufficient notes."));
        assertNull(notes);
        assertEquals(3, atm.getNumOf10DollarsNotes());
        assertEquals(3, atm.getNumOf50DollarsNotes());
        in.close();

        atm = new Atm(0, 0);
        System.setIn(new ByteArrayInputStream("50".getBytes()));
        in = new Scanner(System.in);
        notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("No notes left."));
        assertNull(notes);
        assertEquals(0, atm.getNumOf10DollarsNotes());
        assertEquals(0, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureInvalidWithdrawAmount() {
        ScreenState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("-10".getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Amount should be above 0"));
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();

        System.setIn(new ByteArrayInputStream("77".getBytes()));
        in = new Scanner(System.in);
        notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Amount must be multiplier of 10"));
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureExceedWithdrawLimit() {
        ScreenState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1010".getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Withdraw amount exceeded withdraw limit!"));
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureExceedAvailableBalance() {
        account.setAvailableBalance(new BigDecimal(300));
        ScreenState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("500".getBytes()));
        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Withdraw amount exceeded available balance"));
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ScreenState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("~!abc".getBytes()));

        Scanner in = new Scanner(System.in);
        Pair<Integer> notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(notes);
        in.close();

        System.setIn(new ByteArrayInputStream("three hundred".getBytes()));
        in = new Scanner(System.in);
        notes = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(notes);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
        in.close();
    }
}
