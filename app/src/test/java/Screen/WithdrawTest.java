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
    private DataSource<Country> countryDataSource = null;
    private DataSource<Currency> currencyDataSource = null;
    private Country singapore;
    private Currency sgd;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        try {
            countryDataSource = new CountryDataSource();
            currencyDataSource = new CurrencyDataSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        singapore = countryDataSource.getDataById("SGP");
        sgd = currencyDataSource.getDataById("SGD");
        atm = new Atm(singapore, sgd);
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL,
                currencyDataSource.getDataById("SGD"));
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

        atm = new Atm(singapore, sgd, new int[] { 3, 3 }); // 180 dollars
        // Require 4 50 dollars note.
        System.setIn(new ByteArrayInputStream("200".getBytes()));
        Scanner in = new Scanner(System.in);
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Insufficient notes remaining in ATM to dispense this amount."));
        assertNull(withdrawResult);
        in.close();

        atm = new Atm(singapore, sgd, new int[] { 0, 0 });
        System.setIn(new ByteArrayInputStream("50".getBytes()));
        in = new Scanner(System.in);
        withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Insufficient notes remaining in ATM to dispense this amount."));
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
        assertTrue(outContent.toString().contains("minimum withdraw limit is"));
        assertNull(withdrawResult);
        in.close();

        System.setIn(new ByteArrayInputStream("77".getBytes()));
        in = new Scanner(System.in);
        withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
        assertTrue(outContent.toString().contains("Insufficient notes"));
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
        assertTrue(outContent.toString().contains("Amount exceeded available balance!"));
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
