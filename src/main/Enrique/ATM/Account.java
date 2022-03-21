package src.main.Enrique.ATM;

import java.math.BigDecimal;
import java.util.UUID;

import src.main.Enrique.Exceptions.InsufficientFundsException;
import src.main.Enrique.Helpers.RandHelpers;

// Dummy account for testing only, use teammates version later
public class Account {

    private String accountHolder;

    public String getAccountHolder() {
        return accountHolder;
    }

    private final String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private final int accountPIN;

    public int getPIN() {
        return accountPIN;
    }

    BigDecimal totalBalance;
    BigDecimal availableBalance;
    BigDecimal HoldBalance;

    BigDecimal overdraftLimit;

    // INITIAL_TOTAL is for testing, temporary, haven't considered if we should have
    // minimum account amounts
    // (Probably a real IRL practice, but I haven't checked).
    // if overdraft values should be stored here or in a Bank class somewhere.

    // the default totalBalance in an account, arbitary value.
    private final static int INITIAL_TOTAL = 0;
    private final static int OVERDRAFT_VALUE = 1000;
    private final static BigDecimal BIGD_MAX = new BigDecimal(2 ^ Integer.MAX_VALUE);

    public Double getAvailableBalance() {
        return availableBalance.doubleValue();
    }

    public Double getTotalBalance() {
        return totalBalance.doubleValue();
    }

    // For testing- we need to get user input and pass it as an argument.
    public Account(String accountHolder) {
        this.accountHolder = accountHolder;
        this.setCardNumber(null);

        accountNumber = UUID.randomUUID().toString();
        // For testing, for setting up an actual account, get user input
        accountPIN = RandHelpers.randomInt(10000, 999999);
        // accountPIN =

        totalBalance = new BigDecimal(INITIAL_TOTAL);
        availableBalance = new BigDecimal(INITIAL_TOTAL);
        HoldBalance = new BigDecimal(0);

        overdraftLimit = new BigDecimal(OVERDRAFT_VALUE);
    }

    public Account(String accountHolder, int PIN) {
        this.accountHolder = accountHolder;
        this.setCardNumber(null);

        accountNumber = UUID.randomUUID().toString();
        accountPIN = PIN;

        totalBalance = new BigDecimal(INITIAL_TOTAL);
        availableBalance = new BigDecimal(INITIAL_TOTAL);
        HoldBalance = new BigDecimal(0);

        overdraftLimit = new BigDecimal(OVERDRAFT_VALUE);
    }

    public void deposit(BigDecimal value) throws ArithmeticException {
        if (availableBalance.add(value).compareTo(BIGD_MAX) == 1)
            throw new ArithmeticException("");
        totalBalance = totalBalance.add(value);
    }

    public void withdraw(BigDecimal value) throws InsufficientFundsException {
        if (value.compareTo(availableBalance) == 1)
            throw new InsufficientFundsException(value.subtract(availableBalance).doubleValue());
        availableBalance = availableBalance.subtract(value);
    }

    // determines whether a user-specified PIN matches PIN in Account
    public boolean validatePIN(int userPIN) {
        if (userPIN == getPIN())
            return true;
        else
            return false;
    }

}
