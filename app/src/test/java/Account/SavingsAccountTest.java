package Account;

import Helper.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SavingsAccountTest {
    private String id;
    private String customerId;
    private String name;
    private AccountStatus status;
    private BigDecimal availableBalance; // available balance
    private BigDecimal withdrawLimit; // withdrawal limit local
    private BigDecimal transferLimit; // transfer limit local
    private BigDecimal overseasWithdrawLimit; // withdrawal limit overseas
    private BigDecimal overseasTransferLimit; // transfer limit overseas
    private final int DEFAULT_LIMIT = 5000;
    private BigDecimal interestRate;

    @BeforeEach
    public void setUp() {
        id = "6454856238";
        customerId = "3314572";
        name = "";
        status = AccountStatus.NORMAL;
        availableBalance = new BigDecimal(10000);
        withdrawLimit = new BigDecimal(DEFAULT_LIMIT);
        transferLimit = new BigDecimal(DEFAULT_LIMIT);
        overseasWithdrawLimit = new BigDecimal(DEFAULT_LIMIT);
        overseasTransferLimit = new BigDecimal(DEFAULT_LIMIT);
        interestRate = new BigDecimal("0.0005");
    }

    @Test
    public void createSuccess() {
        Account account = new SavingsAccount(id, customerId, name, status);
        assertEquals(id, account.getId());
        assertEquals(customerId, account.getCustomerId());
        assertEquals(name, account.getName());
        assertEquals(status, account.getStatus());
        assertEquals(withdrawLimit, account.getWithdrawLimit());
        assertEquals(transferLimit, account.getTransferLimit());
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());
        assertEquals(interestRate, ((SavingsAccount) account).getInterestRate());

        id = Id.generateUUID();
        customerId = "3376259";
        name = "test";
        status = AccountStatus.NORMAL;
        withdrawLimit = new BigDecimal(1000);
        transferLimit = new BigDecimal(1000);
        overseasWithdrawLimit = new BigDecimal(1000);
        overseasTransferLimit = new BigDecimal(1000);
        interestRate = new BigDecimal("0.0010");

        account.setId(id);
        account.setCustomerId(customerId);
        account.setName(name);
        account.setStatus(status);
        account.setWithdrawLimit(withdrawLimit);
        account.setTransferLimit(transferLimit);
        account.setOverseasWithdrawLimit(overseasWithdrawLimit);
        account.setOverseasTransferLimit(overseasTransferLimit);
        ((SavingsAccount) account).setInterestRate(interestRate);

        // Test if attributes were changed
        assertEquals(id, account.getId());
        assertEquals(customerId, account.getCustomerId());
        assertEquals(name, account.getName());
        assertEquals(status, account.getStatus());
        assertEquals(withdrawLimit, account.getWithdrawLimit());
        assertEquals(transferLimit, account.getTransferLimit());
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());
        assertEquals(interestRate, ((SavingsAccount) account).getInterestRate());
    }

    @Test
    public void successSetAvailableBalance() {
        Account account = new SavingsAccount(id, customerId, name, status);
        availableBalance = new BigDecimal(1000000);
        account.setAvailableBalance(availableBalance);
        assertEquals(availableBalance, account.getAvailableBalance());
    }

    @Test
    public void failureIllegalAvailableBalance() {
        availableBalance = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setAvailableBalance(availableBalance));
        assertEquals("Available Balance must be above zero.", exception.getMessage());

    }

    @Test
    public void failureCheckAgainstAvailableBalance() {
        availableBalance = new BigDecimal(1000);
        Account account = new SavingsAccount(id, customerId, name, status);
        account.setAvailableBalance(availableBalance);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.checkAgainstAvailableBalance(new BigDecimal(1001)));
        assertEquals("Amount exceeded available balance!", exception.getMessage());
    }

    @Test
    public void failureIllegalWithdrawLimit() {
        withdrawLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setWithdrawLimit(withdrawLimit));
        assertEquals("Withdraw limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureCheckAgainstWithdrawLimit() {
        withdrawLimit = new BigDecimal(1000);
        Account account = new SavingsAccount(id, customerId, name, status);
        account.setWithdrawLimit(withdrawLimit);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.checkAgainstWithdrawLimit(new BigDecimal(2000)));
        assertEquals("Withdraw amount exceeded withdraw limit!", exception.getMessage());
    }

    @Test
    public void failureIllegalTransferLimit() {
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setTransferLimit(transferLimit));
        assertEquals("Transfer limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureIllegalOverseasWithdrawLimit() {
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setOverseasWithdrawLimit(transferLimit));
        assertEquals("Oversea withdrawal limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureIllegalOverseasTransferLimit() {
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setOverseasTransferLimit(transferLimit));
        assertEquals("Oversea transfer limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureIllegalInterestRate() {
        interestRate = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setInterestRate(interestRate));
        assertEquals("Interest Rate cannot be below or equal to zero.", exception.getMessage());
    }
}
