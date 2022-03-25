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
    protected BigDecimal availableBalance; //available balance
    private BigDecimal holdBalance; //balance on hold
    private BigDecimal withdrawLimit; //withdrawal limit local
    private BigDecimal transferLimit; //transfer limit local
    private BigDecimal overseasWithdrawLimit; //withdrawal limit overseas
    private BigDecimal overseasTransferLimit; //transfer limit overseas
    protected final int DEFAULT_LIMIT = 5000;
    private BigDecimal interestRate;

    @BeforeEach
    public void setUp() {
        id = "6454856238";
        customerId = "3314572";
        name = "";
        status = AccountStatus.NORMAL;
        availableBalance = new BigDecimal(10000);
        holdBalance = new BigDecimal(20);
        withdrawLimit = new BigDecimal(DEFAULT_LIMIT);
        transferLimit = new BigDecimal(DEFAULT_LIMIT);
        overseasWithdrawLimit = new BigDecimal(DEFAULT_LIMIT);
        overseasTransferLimit = new BigDecimal(DEFAULT_LIMIT);
    }

    @Test
    public void createSuccess(){
        Account account = new SavingsAccount(id, customerId, name, status);
        assertEquals(id, account.getId());
        assertEquals(customerId, account.getCustomerId());
        assertEquals(name, account.getName());
        assertEquals(status, account.getStatus());
        assertEquals(withdrawLimit, account.getWithdrawLimit());
        assertEquals(transferLimit, account.getTransferLimit());
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());

        id = Id.generateUUID();
        customerId = "3376259";
        name = "test";
        status = AccountStatus.NORMAL;
        withdrawLimit = new BigDecimal(1000);
        transferLimit = new BigDecimal(1000);
        overseasWithdrawLimit = new BigDecimal(1000);
        overseasTransferLimit = new BigDecimal(1000);
        account.setId(id);
        account.setCustomerId(customerId);
        account.setName(name);
        account.setStatus(status);
        //Test if attributes were changed
        assertEquals(id, account.getId());
        assertEquals(customerId, account.getCustomerId());
        assertEquals(name, account.getName());
        assertEquals(status, account.getStatus());
        assertEquals(withdrawLimit, account.getWithdrawLimit());
        assertEquals(transferLimit, account.getTransferLimit());
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());
    }
    @Test
    public void successSetAvailableBalance(){
        Account account = new SavingsAccount(id, customerId, name, status);
        availableBalance = new BigDecimal(1000000);
        account.setAvailableBalance(availableBalance);
        assertEquals(availableBalance, account.getAvailableBalance());
    }
    @Test
    public void failureIllegalAvailableBalance(){
        availableBalance = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setAvailableBalance(availableBalance));
        assertEquals("Available Balance must be above zero.", exception.getMessage());


    }

    @Test
    public void failureCheckAvailableBalance(){
        availableBalance = new BigDecimal(1000);
        Account account = new SavingsAccount(id, customerId, name, status);
        account.setAvailableBalance(availableBalance);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.checkAvailableBalance(new BigDecimal(1000)));
        assertEquals("Withdraw amount exceeded available balance!", exception.getMessage());
    }

    @Test
    public void successSetWithdrawLimit(){
        Account account = new SavingsAccount(id, customerId, name, status);
        withdrawLimit = new BigDecimal(1000000);
        account.setWithdrawLimit(withdrawLimit);
        assertEquals(withdrawLimit, account.getWithdrawLimit());
    }
    @Test
    public void failureIllegalWithdrawLimit(){
        withdrawLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setWithdrawLimit(withdrawLimit));
        assertEquals("Withdraw limit must be above zero.", exception.getMessage());
    }

    @Test
    public void failureCheckWithdrawLimit(){
        withdrawLimit = new BigDecimal(1000);
        Account account = new SavingsAccount(id, customerId, name, status);
        account.setWithdrawLimit(availableBalance);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> account.checkWithdrawLimit(new BigDecimal(2000)));
        assertEquals("Withdraw amount exceeded withdraw limit!", exception.getMessage());
    }

    @Test
    public void successSetTransferLimit(){
        Account account = new SavingsAccount(id, customerId, name, status);
        transferLimit = new BigDecimal(1000000);
        account.setTransferLimit(transferLimit);
        assertEquals(transferLimit, account.getTransferLimit());
    }
    @Test
    public void failureIllegalTransferLimit(){
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setTransferLimit(transferLimit));
        assertEquals("Transfer limit must be above zero.", exception.getMessage());
    }
    @Test
    public void successSetOverseasWithdrawLimit(){
        Account account = new SavingsAccount(id, customerId, name, status);
        overseasWithdrawLimit = new BigDecimal(1000000);
        account.setOverseasWithdrawLimit(overseasWithdrawLimit);
        assertEquals(overseasWithdrawLimit, account.getOverseasWithdrawLimit());
    }
    @Test
    public void failureIllegalOverseasWithdrawLimit(){
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setOverseasWithdrawLimit(transferLimit));
        assertEquals("Oversea withdrawal limit must be above zero.", exception.getMessage());
    }
    @Test
    public void successSetOverseasTransferLimit(){
        Account account = new SavingsAccount(id, customerId, name, status);
        overseasTransferLimit = new BigDecimal(1000000);
        account.setOverseasTransferLimit(overseasTransferLimit);
        assertEquals(overseasTransferLimit, account.getOverseasTransferLimit());
    }
    @Test
    public void failureIllegalOverseasTransferLimit(){
        transferLimit = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setOverseasTransferLimit(transferLimit));
        assertEquals("Oversea transfer limit must be above zero.", exception.getMessage());
    }

    @Test
    public void successSInterestRate(){
        SavingsAccount account = new SavingsAccount(id, customerId, name, status);
        interestRate = new BigDecimal(1000000);
        account.setOverseasTransferLimit(interestRate);
        assertEquals(interestRate, account.getInterestRate());
    }
    @Test
    public void failureIllegalInterestRate(){
        interestRate = new BigDecimal(-1);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount(id, customerId, name, status).setInterestRate(interestRate));
        assertEquals("Interest Rate cannot be below or equal to zero.", exception.getMessage());
    }
}
