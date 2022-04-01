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

import Data.AccountData;
import Data.CurrencyData;
import Data.Data;
import Data.TransactionData;
import Model.Account.Account;
import Model.Currency.Currency;
import Model.Transaction.Transaction;
import Model.Transaction.TransferTransaction;

public class TransferTest {
    private Account account;
    private Data<Currency> currencyData = null;
    private Data<Transaction> txnData;
    private Data<Account> accData;
    private String toAccountId;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        txnData = new TransactionData();
        currencyData = new CurrencyData();
        accData = new AccountData((CurrencyData) currencyData);
        account = accData.getDataById("6454856238");
        toAccountId = "6458795246";
    }

    @Test
    public void success() {
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        // To accountId, amount
        String input = toAccountId + System.getProperty("line.separator") + "135";
        // message
        input += System.getProperty("line.separator") + "Hello World.";

        // Set scanner input value
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        Transaction txn = txns.get(0);
        TransferTransaction tfsTxn = ((TransferTransaction) txn);
        Account toAccount = accData.getDataById(toAccountId);

        // Validate amount transffered, received and new balance of both acocunts
        assertEquals(amt, txn.getAmount());
        assertEquals(new BigDecimal("444575"), toAccount.getAvailableBalance());
        assertEquals(new BigDecimal("32865"), account.getAvailableBalance());

        // Validate from and to account id
        assertEquals(TransferTransaction.Type.SENT, tfsTxn.isReceivedOrSent(account.getId()));
        assertEquals(TransferTransaction.Type.RECEIVED, tfsTxn.isReceivedOrSent(toAccount.getId()));
        assertEquals("Hello World.", tfsTxn.getMessage());
        in.close();
    }

    @Test
    public void successDecimalAmount() {
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        // To accountId, amount
        String input = toAccountId + System.getProperty("line.separator") + "51.44";
        // message
        input += System.getProperty("line.separator") + "Hello World.";

        // Set scanner input value
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        Transaction txn = txns.get(0);
        TransferTransaction tfsTxn = ((TransferTransaction) txn);
        Account toAccount = accData.getDataById(toAccountId);

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
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // "quit"
        String input = "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        assertEquals(BigDecimal.ZERO, amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        in.close();

        // To accountId, "quit"
        input = toAccountId + System.getProperty("line.separator") + "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        in = new Scanner(System.in);
        amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        Account toAccount = accData.getDataById(toAccountId);
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
        amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        toAccount = accData.getDataById(toAccountId);
        assertEquals(BigDecimal.ZERO, amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertEquals(new BigDecimal(444440), toAccount.getAvailableBalance());
        in.close();
    }

    @Test
    public void failureSameAccountNumber() {
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = account.getId(); // same account id
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);

        // transfer to same account
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize); // number of transactions remains the same
        assertEquals(new BigDecimal(33000), account.getAvailableBalance()); // balance should be the same
        assertTrue(outContent.toString().contains("Cannot transfer to own account!"));
        in.close();
    }

    @Test
    public void failureNegativeAmount() {
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = toAccountId + System.getProperty("line.separator") + "-123";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Amount must be > 0!"));
        in.close();
    }

    @Test
    public void failureInvalidAccountStatus() throws IOException {
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        String frozenAccoutId = "6453388410";
        String closedAccountId = "6459568547";
        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = frozenAccoutId + System.getProperty("line.separator") + "55";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
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
        amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
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
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String input = toAccountId + System.getProperty("line.separator") + "6000";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Transfer amount exceeded transfer limit!"));
        in.close();
    }

    @Test
    public void failureExceedAvailableBalance() {
        ViewState transfer = new Transfer();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(transfer);

        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        int initialSize = txns.size();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        account.setTransferLimit(new BigDecimal(50000));

        String input = toAccountId + System.getProperty("line.separator") + "45000";
        input += System.getProperty("line.separator") + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        BigDecimal amt = ((Transfer) transfer).getTransferAmt(in, account, accData, txnData);
        txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        assertNull(amt);
        assertEquals(txns.size(), initialSize);
        assertEquals(new BigDecimal(33000), account.getAvailableBalance());
        assertTrue(outContent.toString().contains("Amount exceeded available balance!"));
        in.close();
    }
}
