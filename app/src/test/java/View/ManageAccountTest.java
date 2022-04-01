package View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Data.AccountData;
import Data.CurrencyData;
import Data.CustomerData;
import Data.Data;
import Model.Account.Account;
import Model.Currency.Currency;
import Model.Customer.Customer;

public class ManageAccountTest {
    Customer customer;
    Account account;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        Data<Currency> currencyData = new CurrencyData();
        Data<Account> accountData = new AccountData((CurrencyData) currencyData);
        Data<Customer> customerData = new CustomerData();
        customer = customerData.getDataById("4000100");
        account = accountData.getDataById("6457319546");
    }

    @Test
    public void success() {
        // Instantiate view and print it out
        ManageAccount accountView = new ManageAccount();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrint(accountView);

        // Pipe System.Out content into outContent
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);
        String information = outContent.toString();

        // valid choice
        assertTrue(vaildChoice);

        // Checking for customer information: name, email phone and address
        assertTrue(information.contains(customer.getName()));
        assertTrue(information.contains(customer.getEmail()));
        assertTrue(information.contains(customer.getPhone()));
        assertTrue(information.contains(customer.getAddress().getAddress()));

        // Check account information: balances and limits
        assertTrue(information.contains(account.getId()));
        assertTrue(information.contains(account.getAvailableBalance().toString()));
        assertTrue(information.contains(account.getHoldBalance().toString()));
        assertTrue(information.contains(account.getTransferLimit().toString()));
        assertTrue(information.contains(account.getWithdrawLimit().toString()));
        assertTrue(information.contains(account.getOverseasTransferLimit().toString()));
        assertTrue(information.contains(account.getOverseasWithdrawLimit().toString()));
    }

    @Test
    public void successBackToMain() {
        // Instantiate view
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        String input = "1" + System.getProperty("line.separator") + "5";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);

        // valid choice
        assertTrue(vaildChoice);
    }

    @Test
    public void successChangeLocalWithdrawLimit() {
        // Instantiate view
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        String input = "1" + System.getProperty("line.separator") + "1";
        input += System.getProperty("line.separator") + "123";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);

        // valid choice
        assertTrue(vaildChoice);
        assertEquals(new BigDecimal(123), account.getWithdrawLimit());
    }

    @Test
    public void successChangeLocalTransferLimit() {
        // Instantiate view
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        String input = "1" + System.getProperty("line.separator") + "2";
        input += System.getProperty("line.separator") + "456.22";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);

        // valid choice
        assertTrue(vaildChoice);
        assertEquals(new BigDecimal("456.22"), account.getTransferLimit());
    }

    @Test
    public void successChangeOverseasWithdrawLimit() {
        // Instantiate view
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        String input = "1" + System.getProperty("line.separator") + "3";
        input += System.getProperty("line.separator") + "10.55";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);

        // valid choice
        assertTrue(vaildChoice);
        assertEquals(new BigDecimal("10.55"), account.getOverseasWithdrawLimit());
    }

    @Test
    public void successChangeOverseasTransferLimit() {
        // Instantiate view
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        String input = "1" + System.getProperty("line.separator") + "4";
        input += System.getProperty("line.separator") + "500";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);

        // valid choice
        assertTrue(vaildChoice);
        assertEquals(new BigDecimal("500"), account.getOverseasTransferLimit());
    }

    @Test
    public void failureAccountInvalidOption() {
        // Instantiate view and print it out
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("3".getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);
        in.close();
        // invalid choice
        assertFalse(vaildChoice);

        System.setIn(new ByteArrayInputStream("hhh".getBytes()));
        in = new Scanner(System.in);
        vaildChoice = accountView.getUserChoice(in, customer, account);
        in.close();
        // invalid choice
        assertFalse(vaildChoice);
    }

    @Test
    public void failureLimitsInvalidOption() {
        // Instantiate view and print it out
        ManageAccount accountView = new ManageAccount();

        // Set scanner input value
        String input = "1" + System.getProperty("line.separator") + "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner in = new Scanner(System.in);
        boolean vaildChoice = accountView.getUserChoice(in, customer, account);
        in.close();
        // invalid choice
        assertFalse(vaildChoice);

        input = "1" + System.getProperty("line.separator") + "fgfe";
        in = new Scanner(System.in);
        vaildChoice = accountView.getUserChoice(in, customer, account);
        in.close();
        // invalid choice
        assertFalse(vaildChoice);
    }
}
