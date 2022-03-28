package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.AccountStatus;
import Account.CurrentAccount;
import Account.SavingsAccount;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Transaction.Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class TransactionHistoryTest {
    private Account account;
    private static DataSource<Transaction> txnDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL, "SGP");
        txnDataSource = new TransactionDataSource();
    }

    @Test
    public void success() {
        ScreenState txnHistory = new TransactionHistory();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(txnHistory);

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print 5 transaction
        String input = "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        ((TransactionHistory) txnHistory).printTxnHistory(in, account.getId(), txnDataSource);
        String out = outContent.toString();
        in.close();

        // Should print out latest 5 transactions
        assertTrue(out.contains("27bf075bc87c4795abe83cdd911ec0d8"));
        assertTrue(out.contains("e19acfc3657243aeaa2e5b176f055635"));
        assertTrue(out.contains("6035c158e7794f5eb3570801d298e156"));
        assertTrue(out.contains("6035c158e7794f5eb3570801d298e156"));
        assertTrue(out.contains("9d973348cf5b4f669c2b811107dfc8eb"));

        // 6th transaction should not be insinde
        assertFalse(out.contains("d72ff38fc005450696fe9f208f3728f7"));

        // Content of 1st transaction
        assertTrue(out.contains("Date: 15 Mar 2022 06:19am"));
        assertTrue(out.contains("Type: RECEIVED"));
        assertTrue(out.contains("From account Id: 6458457236"));
        assertTrue(out.contains("Message:"));
        assertTrue(out.contains("Amount: $90.00"));

        // Content of 5th transaction
        assertTrue(out.contains("Date: 13 Mar 2022 05:19am"));
        assertTrue(out.contains("Type: SENT"));
        assertTrue(out.contains("To account Id: 6452546388"));
        assertTrue(out.contains("Message: For meal"));
        assertTrue(out.contains("Amount: $90.00"));
    }

    @Test
    public void successPrintAllTxns() {
        ScreenState txnHistory = new TransactionHistory();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(txnHistory);

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Print 20 transaction
        String input = "" + System.getProperty("line.separator") + ""
                + System.getProperty("line.separator") + ""
                + System.getProperty("line.separator") + "";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        ((TransactionHistory) txnHistory).printTxnHistory(in, account.getId(), txnDataSource);
        String out = outContent.toString();
        in.close();

        // Should print 20 transactions
        assertTrue(out.contains("27bf075bc87c4795abe83cdd911ec0d8"));
        assertTrue(out.contains("e19acfc3657243aeaa2e5b176f055635"));
        assertTrue(out.contains("6035c158e7794f5eb3570801d298e156"));
        assertTrue(out.contains("6035c158e7794f5eb3570801d298e156"));
        assertTrue(out.contains("9d973348cf5b4f669c2b811107dfc8eb"));

        assertTrue(out.contains("d72ff38fc005450696fe9f208f3728f7"));
        assertTrue(out.contains("2c56355bce9840f89818dd3335e3c7da"));
        assertTrue(out.contains("1ce00246ae914c7987d118fb5854e2c1"));
        assertTrue(out.contains("7af1c58fbd014d86b2e7faec01958a97"));
        assertTrue(out.contains("3f3da36e33ca455190fa6214de87ad85"));

        assertTrue(out.contains("41275c16bead4e90af09d1de693a2284"));
        assertTrue(out.contains("416a6d0077b1470b93baeaa03f064ca1"));
        assertTrue(out.contains("92f1d617b44f4227a868a7b055b5b695"));
        assertTrue(out.contains("3bd334f1552c4c1783138755ca2c98da"));
        assertTrue(out.contains("d934f8622d8a44c6b254e8a2836d9774"));

        assertTrue(out.contains("7ca782d21f4a4312bb626cfb2724e4f8"));
        assertTrue(out.contains("a468ebbd8ffd4c76899df83e2b0d101b"));
        assertTrue(out.contains("51fd689cdfdf48989451019b6903c711"));
        assertTrue(out.contains("5e6c6b085c19494f82678890abd906b4"));
        assertTrue(out.contains("3b4cd670bac54f458a87c774406df7ac"));

        // Content of 16th transaction
        assertTrue(out.contains("Date: 09 Mar 2022 03:19am"));
        assertTrue(out.contains("Type: CASH WITHDRAW"));
        assertTrue(out.contains("Amount: $40.00"));

        // Content of 17th transaction
        assertTrue(out.contains("Date: 08 Mar 2022 07:19am"));
        assertTrue(out.contains("Type: CASH DEPOSIT"));
        assertTrue(out.contains("Amount: $30.00"));
    }

    @Test
    public void successNoTxns() {
        account = new SavingsAccount("6452255402", "3314572", "Tom", AccountStatus.NORMAL, "SGP");
        ScreenState txnHistory = new TransactionHistory();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Scanner in = new Scanner(System.in);
        ((TransactionHistory) txnHistory).printTxnHistory(in, account.getId(), txnDataSource);
        in.close();

        String out = outContent.toString();
        assertTrue(out.contains("No transactions found"));
    }
}
