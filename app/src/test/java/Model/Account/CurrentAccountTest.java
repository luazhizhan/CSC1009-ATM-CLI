package Model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Data.CurrencyData;
import Data.Data;
import Helper.Id;
import Model.Currency.Currency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrentAccountTest {
    private String id;
    private String customerId;
    private String name;
    private AccountStatus status;
    private BigDecimal availableBalance; // available balance
    private BigDecimal withdrawLimit; // withdrawal limit local
    private BigDecimal transferLimit; // transfer limit local
    private BigDecimal overseasWithdrawLimit; // withdrawal limit overseas
    private BigDecimal overseasTransferLimit; // transfer limit overseas
    private BigDecimal overdraftLimit;
    private Data<Currency> currencyData = null;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        id = "6454856238";
        customerId = "3314572";
        name = "";
        status = AccountStatus.NORMAL;
        availableBalance = new BigDecimal(10000);
        withdrawLimit = new BigDecimal(Account.DEFAULT_LIMIT);
        transferLimit = new BigDecimal(Account.DEFAULT_LIMIT);
        overseasWithdrawLimit = new BigDecimal(Account.DEFAULT_LIMIT);
        overseasTransferLimit = new BigDecimal(Account.DEFAULT_LIMIT);
        currencyData = new CurrencyData();
    }

    @Test
    public void createSuccess() {
        Account account = new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"));
        assertEquals(id, account.getId());
        assertEquals(customerId, account.getCustomerId());
        assertEquals(name, account.getName());
        assertEquals(status, account.getStatus());
        assertEquals(withdrawLimit, account.getWithdrawLimit());
        assertEquals(transferLimit, account.getTransferLimit());
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());

        // Update account
        id = Id.generateUUID();
        customerId = "3376259";
        name = "test";
        status = AccountStatus.NORMAL;
        availableBalance = new BigDecimal(5000);
        withdrawLimit = new BigDecimal(1000);
        transferLimit = new BigDecimal(1000);
        overseasWithdrawLimit = new BigDecimal(1000);
        overseasTransferLimit = new BigDecimal(1000);
        account.setId(id);
        account.setAvailableBalance(availableBalance);
        account.setCustomerId(customerId);
        account.setName(name);
        account.setStatus(status);
        account.setWithdrawLimit(withdrawLimit);
        account.setTransferLimit(transferLimit);
        account.setOverseasWithdrawLimit(overseasWithdrawLimit);
        account.setOverseasTransferLimit(overseasTransferLimit);

        // Test if attributes were changed
        assertEquals(id, account.getId());
        assertEquals(customerId, account.getCustomerId());
        assertEquals(name, account.getName());
        assertEquals(status, account.getStatus());
        assertEquals(availableBalance, account.getAvailableBalance());
        assertEquals(withdrawLimit, account.getWithdrawLimit());
        assertEquals(transferLimit, account.getTransferLimit());
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());
    }

    @Test
    public void failureIllegalAvailableBalance() {
        // DEFAULT overdraft limit is 5000
        overdraftLimit = new BigDecimal(-5001);
        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"))
                        .setAvailableBalance(overdraftLimit));
        assertEquals("Exceeded Overdraft Limit!", exception2.getMessage());
    }

    @Test
    public void failureCheckAgainstAvailableBalance() {
        availableBalance = new BigDecimal(1000);
        Account account = new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"));
        account.setAvailableBalance(availableBalance);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.checkAgainstAvailableBalance(new BigDecimal(Account.DEFAULT_LIMIT + 1000 + 1)));
        assertEquals("Amount exceeded available balance!", exception.getMessage());
    }

    @Test
    public void failureIllegalWithdrawLimit() {
        withdrawLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"))
                        .setWithdrawLimit(withdrawLimit));
        assertEquals("Withdraw limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureCheckAgainstWithdrawLimit() {
        withdrawLimit = new BigDecimal(1000);
        Account account = new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"));
        account.setWithdrawLimit(withdrawLimit);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.checkAgainstWithdrawLimit(new BigDecimal(2000)));
        assertEquals("Withdraw amount exceeded withdraw limit!", exception.getMessage());
    }

    @Test
    public void failureIllegalTransferLimit() {
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"))
                        .setTransferLimit(transferLimit));
        assertEquals("Transfer limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureIllegalOverseasWithdrawLimit() {
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"))
                        .setOverseasWithdrawLimit(transferLimit));
        assertEquals("Oversea withdrawal limit must be above zero.", exception.getMessage());
    }

    @Test
    public void successSetOverdraftLimit() {
        CurrentAccount account = new CurrentAccount(id, customerId, name, status,
                currencyData.getDataById("SGD"));
        overdraftLimit = new BigDecimal(1000000);
        account.setOverDraftLimit(overdraftLimit);
        assertEquals(overdraftLimit, account.getOverDraftLimit());
    }

    @Test
    public void failureIllegalOverdraftLimit() {
        overdraftLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CurrentAccount(id, customerId, name, status, currencyData.getDataById("SGD"))
                        .setOverDraftLimit(overdraftLimit));
        assertEquals("Over draft limit must be above zero.", exception.getMessage());
    }
}
