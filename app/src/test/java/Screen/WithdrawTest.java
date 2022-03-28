package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.AccountStatus;
import Account.CurrentAccount;
import Atm.Atm;
import DataSource.CountryDataSource;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Helper.Tuple;
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
    private DataSource<Transaction> txnDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        atm = new Atm();
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL, "SGP");
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

        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        Transaction txn = txns.get(0);
        assertEquals(new BigDecimal(120), txn.getAmount());
        assertEquals(CashTransaction.TransactionType.WITHDRAW, ((CashTransaction) txn).getType());
        assertEquals(2, withdrawResult.y[0]);
        assertEquals(2, withdrawResult.y[1]);
        assertEquals(new BigDecimal(9880), account.getAvailableBalance());
        in.close();
    }

    @Test
    public void failureInsufficientNotes() {
        ScreenState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            atm = new Atm(new CountryDataSource().getDataById("SGP"), new int[] { 3, 3 }); // 180 dollars
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Require 4 50 dollars note.
        System.setIn(new ByteArrayInputStream("200".getBytes()));
        Scanner in = new Scanner(System.in);
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Insufficient notes."));
        assertNull(withdrawResult);
        in.close();

        try {
            atm = new Atm(new CountryDataSource().getDataById("SGP"), new int[] { 0, 0 });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.setIn(new ByteArrayInputStream("50".getBytes()));
        in = new Scanner(System.in);
        withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("No notes left."));
        assertNull(withdrawResult);
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
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Amount should be above 0"));
        assertNull(withdrawResult);
        in.close();

        System.setIn(new ByteArrayInputStream("77".getBytes()));
        in = new Scanner(System.in);
        withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Amount must be multiplier of 10"));
        assertNull(withdrawResult);
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
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Withdraw amount exceeded withdraw limit!"));
        assertNull(withdrawResult);
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
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Withdraw amount exceeded available balance"));
        assertNull(withdrawResult);
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
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(withdrawResult);
        in.close();

        System.setIn(new ByteArrayInputStream("three hundred".getBytes()));
        in = new Scanner(System.in);
        withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(withdrawResult);
        in.close();
    }
}
