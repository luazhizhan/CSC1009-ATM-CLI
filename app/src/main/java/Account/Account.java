package Account;

import java.math.BigDecimal;

/**
 * Abstract bank account class inherited by Current and Savings bank account
 * classes
 */
public abstract class Account {
    private String id; // Account ID
    private String customerId;
    private String name; // Account nmae
    private AccountStatus status;

    // Protected attribute as setAvailableBalance method
    // is overridden by CurrentAccount subclass
    protected BigDecimal availableBalance;

    private BigDecimal holdBalance; // balance on hold
    private BigDecimal withdrawLimit;
    private BigDecimal transferLimit;
    private BigDecimal overseasWithdrawLimit;
    private BigDecimal overseasTransferLimit;
    protected static final int DEFAULT_LIMIT = 5000; // for accessibility in sub classes

    public Account(String id, String customerId, String name) {
        setId(id);
        setCustomerId(customerId);
        setName(name);
        setStatus(AccountStatus.NORMAL);
        setDefaultLimits();
    }

    public Account(String id, String customerId, String name, AccountStatus status) {
        setId(id);
        setCustomerId(customerId);
        setName(name);
        setStatus(status);
        setDefaultLimits();
    }

    protected void setDefaultLimits() {
        BigDecimal limit = new BigDecimal(DEFAULT_LIMIT);
        setWithdrawLimit(limit);
        setTransferLimit(limit);
        setOverseasWithdrawLimit(limit);
        setOverseasTransferLimit(limit);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    /**
     * Available balance must be above 0
     * 
     * @param availableBalance
     */
    public void setAvailableBalance(BigDecimal availableBalance) {
        if (availableBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Available Balance must be above zero.");
        }
        this.availableBalance = availableBalance;
    }

    /**
     * Check amount argument against account's avaliable balance
     * 
     * @param amount
     */
    public void checkAgainstAvailableBalance(BigDecimal amount) {
        if (amount.compareTo(getAvailableBalance()) > 0) {
            throw new IllegalArgumentException("Amount exceeded available balance!");
        }
    }

    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.availableBalance.subtract(holdBalance);
        this.holdBalance = holdBalance;
    }

    public BigDecimal getWithdrawLimit() {
        return withdrawLimit;
    }

    /**
     * Check amount argument against account's local withdraw limit
     * 
     * @param amount
     */
    public void checkAgainstWithdrawLimit(BigDecimal amount) {
        if (amount.compareTo(getWithdrawLimit()) > 0) {
            throw new IllegalArgumentException("Withdraw amount exceeded withdraw limit!");
        }
    }

    /**
     * Account's local withdraw limit must be >= 0
     * 
     * @param amount
     */
    public void setWithdrawLimit(BigDecimal withdrawLimit) {
        if (withdrawLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Withdraw limit must be above zero.");
        }
        this.withdrawLimit = withdrawLimit;
    }

    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    /**
     * Account's local transfer limit must be >= 0
     * 
     * @param transferLimit
     */
    public void setTransferLimit(BigDecimal transferLimit) {
        if (transferLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transfer limit must be above zero.");
        }
        this.transferLimit = transferLimit;
    }

    /**
     * Account's local transfer limit must be > 0
     * 
     * @param amount
     */
    public void checkAgainstTransferLimit(BigDecimal amount) {
        if (amount.compareTo(getTransferLimit()) > 0) {
            throw new IllegalArgumentException("Transfer amount exceeded transfer limit!");
        }
    }

    public BigDecimal getOverseasWithdrawLimit() {
        return overseasWithdrawLimit;
    }

    /**
     * Account's overseas transfer limit must be >= 0
     * 
     * @param transferLimit
     */
    public void setOverseasWithdrawLimit(BigDecimal overseasWithdrawLimit) {
        if (overseasWithdrawLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Oversea withdrawal limit must be above zero.");
        }
        this.overseasWithdrawLimit = overseasWithdrawLimit;
    }

    public BigDecimal getOverseasTransferLimit() {
        return overseasTransferLimit;
    }

    /**
     * Account's overseas transfer limit must be > 0
     * 
     * @param amount
     */
    public void setOverseasTransferLimit(BigDecimal overseasTransferLimit) {
        if (overseasTransferLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Oversea transfer limit must be above zero.");
        }
        this.overseasTransferLimit = overseasTransferLimit;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void checkReceivingAccountStatus() throws InvalidAccountException {
        if (this.status.compareTo(AccountStatus.CLOSED) == 0) {
            throw new InvalidAccountException("Receiving Account is closed!");
        }
        if (this.status.compareTo(AccountStatus.FROZEN) == 0) {
            throw new InvalidAccountException("Receiving Account is frozen!");
        }
    }

    public void addAvailableBalance(BigDecimal amount) {
        setAvailableBalance(getAvailableBalance().add(amount));
    }

    /**
     * Validate amount to withdraw and set account's available balance
     * 
     * @param amount
     */
    public void withdrawAvailableBalance(BigDecimal amount) {
        checkAgainstWithdrawLimit(amount);
        checkAgainstAvailableBalance(amount);
        setAvailableBalance(getAvailableBalance().subtract(amount));
    }

    /**
     * Validate amount to transfer and set account's available balance
     * 
     * @param amount
     */
    public void transferAvailableBalance(BigDecimal amount) {
        checkAgainstTransferLimit(amount);
        checkAgainstAvailableBalance(amount);
        setAvailableBalance(getAvailableBalance().subtract(amount));
    }

}
