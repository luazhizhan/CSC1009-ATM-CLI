package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Currency.Currency;
import DataSource.AccountDataSource;
import DataSource.CurrencyDataSource;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Transaction.Transaction;
import Transaction.TransferTransaction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class TransferTest {
    private Account account;
    private DataSource<Currency> currencyDataSource = null;
    private DataSource<Transaction> txnDataSource;
    private DataSource<Account> accDataSource;
    private String toAccountId;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        txnDataSource = new TransactionDataSource();
        currencyDataSource = new CurrencyDataSource();
        accDataSource = new AccountDataSource((CurrencyDataSource) currencyDataSource);
        account = accDataSource.getDataById("6454856238");
        toAccountId = "6458795246";
    }

    @Test
    public void success() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        // To accountId, amount
        String input = toAccountId + System.getProperty("line.separator") + "135";
        // message
        input += System.getProperty("line.separator") + "Hello World.";

        // Set scanner input value
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        Transaction txn = txns.get(0);
        TransferTransaction tfsTxn = ((TransferTransaction) txn);
        Account toAccount = accDataSource.getDataById(toAccountId);

        assertEquals(amt, txn.getAmount());
        assertEquals(new BigDecimal("444575"), toAccount.getAvailableBalance());
        assertEquals(new BigDecimal("32865"), account.getAvailableBalance());
        assertEquals(TransferTransaction.Type.SENT, tfsTxn.isReceivedOrSent(account.getId()));
        assertEquals(TransferTransaction.Type.RECEIVED, tfsTxn.isReceivedOrSent(toAccount.getId()));
        assertEquals("Hello World.", tfsTxn.getMessage());
        in.close();
    }

    @Test
    public void successDecimalAmount() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        // To accountId, amount
        String input = toAccountId + System.getProperty("line.separator") + "51.44";
        // message
        input += System.getProperty("line.separator") + "Hello World.";

        // Set scanner input value
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        Transaction txn = txns.get(0);
        TransferTransaction tfsTxn = ((TransferTransaction) txn);
        Account toAccount = accDataSource.getDataById(toAccountId);

        assertEquals(amt, txn.getAmount());
        assertEquals(new BigDecimal("444491.44"), toAccount.getAvailableBalance());
        assertEquals(new BigDecimal("32948.56"), account.getAvailableBalance());
        assertEquals(TransferTransaction.Type.SENT, tfsTxn.isReceivedOrSent(account.getId()));
        assertEquals(TransferTransaction.Type.RECEIVED, tfsTxn.isReceivedOrSent(toAccount.getId()));
        assertEquals("Hello World.", tfsTxn.getMessage());
        in.close();
    }

    @Test
    public void successQuit() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // "quit"
        String input = "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertEquals(BigDecimal.ZERO, amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        in.close();

        // To accountId, "quit"
        input = toAccountId + System.getProperty("line.separator") + "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        in = new Scanner(System.in);
        amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        Account toAccount = accDataSource.getDataById(toAccountId);
        assertEquals(BigDecimal.ZERO, amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertEquals(new BigDecimal(444440), toAccount.getAvailableBalance());
        in.close();

        // To accountId, amount
        input = toAccountId + System.getProperty("line.separator") + "322";
        input += System.getProperty("line.separator") + "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        in = new Scanner(System.in);
        amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        toAccount = accDataSource.getDataById(toAccountId);
        assertEquals(BigDecimal.ZERO, amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertEquals(new BigDecimal(444440), toAccount.getAvailableBalance());
        in.close();
    }

    @Test
    public void failureSameAccountNumber() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = account.getId(); // same account id
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Cannot transfer to own account!"));
        in.close();
    }

    @Test
    public void failureNegativeAmout() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = toAccountId + System.getProperty("line.separator") + "-123";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Amount must be > 0!"));
        in.close();
    }

    @Test
    public void failureInvalidAccountStatus() throws IOException {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        String frozenAccoutId = "6453388410";
        String closedAccountId = "6459568547";
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = frozenAccoutId + System.getProperty("line.separator") + "55";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        String out = outContent.toString();
        assertTrue(out.contains("Receiving Account is frozen!"));
        in.close();
        outContent.close();

        input = closedAccountId + System.getProperty("line.separator") + "64.3";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        in = new Scanner(System.in);
        amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        out = outContent.toString();
        assertTrue(out.contains("Receiving Account is closed!"));
        in.close();
        outContent.close();
    }

    @Test
    public void failureExceedTransferLimit() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = toAccountId + System.getProperty("line.separator") + "6000";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Transfer amount exceeded transfer limit!"));
        in.close();
    }

    @Test
    public void failureExceedAvailableBalance() {
        ScreenState transfer = new Transfer();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(transfer);

        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        account.setTransferLimit(new BigDecimal(50000));

        String input = toAccountId + System.getProperty("line.separator") + "45000";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accDataSource, txnDataSource);
        txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Amount exceeded available balance!"));
        in.close();
    }
}
