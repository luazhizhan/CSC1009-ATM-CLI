package View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Data.CountryData;
import Data.CurrencyData;
import Data.Data;
import Data.TransactionData;
import Helper.Tuple;
import Model.Account.Account;
import Model.Account.AccountStatus;
import Model.Account.CurrentAccount;
import Model.Atm.Atm;
import Model.Country.Country;
import Model.Currency.Currency;
import Model.Transaction.CashTransaction;
import Model.Transaction.Transaction;

public class WithdrawTest {
    private Atm atm;
    private Account account;
    private Data<Transaction> txnDataSource;
    private Data<Country> countryDataSource = null;
    private Data<Currency> currencyDataSource = null;
    private Country singapore;
    private Currency sgd;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        countryDataSource = new CountryData();
        currencyDataSource = new CurrencyData();
        singapore = countryDataSource.getDataById("SGP");
        sgd = currencyDataSource.getDataById("SGD");
        atm = new Atm(singapore, sgd);
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL,
                currencyDataSource.getDataById("SGD"));
        account.setAvailableBalance(new BigDecimal(10000));
        ((CurrentAccount) account).setWithdrawLimit(new BigDecimal(1000));
        ((CurrentAccount) account).setOverDraftLimit(new BigDecimal(100));
        txnDataSource = new TransactionData();
    }

    @Test
    public void success() {
        ViewState withdraw = new Withdraw();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrintScreen(withdraw);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("120".getBytes()));
        Scanner in = new Scanner(System.in);

        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        List<Transaction> txns = ((TransactionData) txnDataSource).getDataByAccountId(account.getId());
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
        ViewState withdraw = new Withdraw();

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
        ViewState withdraw = new Withdraw();

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
        ViewState withdraw = new Withdraw();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1010".getBytes()));
        Scanner in = new Scanner(System.in);
        Tuple<BigDecimal, int[]> withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account,
                txnDataSource);
        assertTrue(outContent.toString().contains("Withdraw amount exceeded"));
        assertNull(withdrawResult);
        in.close();
    }

    @Test
    public void failureExceedAvailableBalance() {
        account.setAvailableBalance(new BigDecimal(300));
        ViewState withdraw = new Withdraw();

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
        ViewState withdraw = new Withdraw();

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
