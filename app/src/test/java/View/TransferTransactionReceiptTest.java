package View;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Data.CurrencyData;
import Data.Data;
import Data.TransactionData;
import Model.Account.Account;
import Model.Account.AccountStatus;
import Model.Account.CurrentAccount;
import Model.Account.SavingsAccount;
import Model.Currency.Currency;
import Model.Transaction.Transaction;
import Model.Transaction.TransferTransaction;

/**
 * TransferTransactionReceiptTest
 */
public class TransferTransactionReceiptTest {
    private Account account;
    private Account toAccount;
    private Transaction txn;
    private Data<Transaction> ds;
    private BigDecimal amt;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private Data<Currency> currencyData = null;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        currencyData = new CurrencyData();
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL,
                currencyData.getDataById("SGD"));
        account.setAvailableBalance(new BigDecimal(30000));
        toAccount = new SavingsAccount("6458795246", "3314575", "Tim", AccountStatus.NORMAL,
                currencyData.getDataById("SGD"));
        toAccount.setAvailableBalance(new BigDecimal(20000));

        amt = new BigDecimal("500");
        txn = new TransferTransaction(account.getId(), toAccount.getId(), amt);

        ds = new TransactionData();
        ds.add(txn);
    }

    @Test
    public void success() throws IOException {
        ViewState receipt = new TransferTransactionReceipt();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(receipt);

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print receipt
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean result = ((TransferTransactionReceipt) receipt).getSelectedOption(in, account, ds, amt);
        String contentString = outContent.toString();
        assertTrue(result);
        assertTrue(contentString.contains("To Account Id: " + toAccount.getId()));
        assertTrue(contentString.contains("Amount sent: $500"));
        assertTrue(contentString.contains("Available balance: "
                + formatter.format(account.getAvailableBalance())));
        assertTrue(contentString.contains("Thank You For Banking With Us!"));
        in.close();
        outContent.close();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print balance only
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        in = new Scanner(System.in);
        result = ((TransferTransactionReceipt) receipt).getSelectedOption(in,
                account, ds, amt);
        contentString = outContent.toString();
        assertTrue(result);
        assertTrue(contentString.contains("Available balance: "
                + formatter.format(account.getAvailableBalance())));
        assertTrue(contentString.contains("Thank You For Banking With Us!"));
        in.close();
        outContent.close();
    }

    @Test
    public void failureNoSuchOption() {
        ViewState receipt = new TransferTransactionReceipt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("-77".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean result = ((TransferTransactionReceipt) receipt).getSelectedOption(in, account, ds, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("No such option available! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("8".getBytes()));
        in = new Scanner(System.in);
        result = ((TransferTransactionReceipt) receipt).getSelectedOption(in, account, ds, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("No such option available! Please try again."));
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ViewState receipt = new TransferTransactionReceipt();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("abc".getBytes()));

        Scanner in = new Scanner(System.in);
        boolean result = ((TransferTransactionReceipt) receipt).getSelectedOption(in, account, ds, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("5g6".getBytes()));
        in = new Scanner(System.in);
        result = ((TransferTransactionReceipt) receipt).getSelectedOption(in, account, ds, amt);
        assertFalse(result);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }
}
