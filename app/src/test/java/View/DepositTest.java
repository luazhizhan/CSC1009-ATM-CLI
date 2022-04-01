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

public class DepositTest {
    private Atm atm;
    private Account account;
    private static Data<Transaction> txnData;
    private Data<Country> countryData = null;
    private Data<Currency> currencyData = null;
    private Country singapore;
    private Currency sgd;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        countryData = new CountryData();
        currencyData = new CurrencyData();
        singapore = countryData.getDataById("SGP");
        sgd = currencyData.getDataById("SGD");
        atm = new Atm(singapore, sgd);
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL,
                currencyData.getDataById("SGD"));
        account.setAvailableBalance(new BigDecimal(10000));
        ((CurrentAccount) account).setWithdrawLimit(new BigDecimal(1000));
        ((CurrentAccount) account).setOverDraftLimit(new BigDecimal(100));
        txnData = new TransactionData();
    }

    @Test
    public void success() {
        ViewState deposit = new Deposit();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(deposit);

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        String input = "1" + System.getProperty("line.separator") + "2";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Tuple<BigDecimal, int[]> withdrawResult = ((Deposit) deposit).getDepositAmt(in, atm, account, txnData);
        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());

        // Deposit 110
        Transaction txn = txns.get(0);
        assertEquals(new BigDecimal(110), txn.getAmount());
        assertEquals(CashTransaction.TransactionType.DEPOSIT, ((CashTransaction) txn).getType());
        assertEquals(1, withdrawResult.y[0]);
        assertEquals(2, withdrawResult.y[1]);
        assertEquals(new BigDecimal(10110), account.getAvailableBalance());
        // validate new balance of notes in the ATM
        assertEquals(301, atm.getBills()[0]);
        assertEquals(302, atm.getBills()[1]);
        in.close();
    }

    @Test
    public void failureInvalidDepositAmount() {
        ViewState deposit = new Deposit();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        String input = "0" + System.getProperty("line.separator") + "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Tuple<BigDecimal, int[]> notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnData);
        assertTrue(outContent.toString().contains("Please deposit at least one note"));
        assertNull(notes); // nothing to be dispense

        // validate new balance of notes in the ATM
        assertEquals(300, atm.getBills()[0]);
        assertEquals(300, atm.getBills()[1]);
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ViewState deposit = new Deposit();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        String input = "abc" + System.getProperty("line.separator") + "3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        Tuple<BigDecimal, int[]> notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnData);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(notes);
        in.close();

        // Set scanner input values
        // System.getProperty("line.separator") to stimulate multiple inputs
        input = "3" + System.getProperty("line.separator") + "3.3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        in = new Scanner(System.in);
        notes = ((Deposit) deposit).getDepositAmt(in, atm, account, txnData);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        assertNull(notes);
        in.close();

    }
}
