package Screen;

import Account.Account;
import Customer.Customer;
import DataSource.DataSource;
import DataSource.AccountDataSource;
import DataSource.CustomerDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountScreenTest {
    Customer customer;
    Account account;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        DataSource<Account> accountDataSource = new AccountDataSource(); // Create Datasource objects to read from CSV
                                                                         // and get customer and account object
        DataSource<Customer> customerDataSource = new CustomerDataSource();
        customer = customerDataSource.getDataById("4000100");
        account = accountDataSource.getDataById("6457319546");
    }

    @Test
    public void success() {
        // Pipe System.Out content into outContent
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Instantiate screen and print it out
        AccountScreen accountScreen = new AccountScreen(customer, account);
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(accountScreen);
        String information = outContent.toString();
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

}
