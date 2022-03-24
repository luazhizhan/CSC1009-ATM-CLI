package Account;

import java.math.BigDecimal;

public abstract class Account {
    private String id; // Account ID
    private String customerId; // Customer ID
    private String name; // name of Account, not customer's name
    private AccountStatus status; // status of Account: Enum

    protected BigDecimal availableBalance; // available balance
    private BigDecimal holdBalance; // balance on hold
    private BigDecimal withdrawLimit; // withdrawal limit local
    private BigDecimal transferLimit; // transfer limit local
    private BigDecimal overseasWithdrawLimit; // withdrawal limit overseas
    private BigDecimal overseasTransferLimit; // transfer limit overseas
    protected final int DEFAULT_LIMIT = 5000;

    public Account(String id, String customerId, String name) {
        /* create account according to most important attributes */
        setId(id);
        setCustomerId(customerId);
        setName(name);
        setStatus(AccountStatus.NORMAL);
        setDefaultLimits();
    }

    public Account(String id, String customerId, String name, AccountStatus status) {
        /* create account according to most important attributes */
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

    public void setAvailableBalance(BigDecimal availableBalance) {
        if (availableBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Available Balance must be above zero.");
        }
        this.availableBalance = availableBalance;
    }

    public void checkAvaliableBalance(BigDecimal amount) {
        if (amount.compareTo(getAvailableBalance()) > 0) {
            throw new IllegalArgumentException("Withdraw amount exceeded avaliable balance!");
        }
    }

    public void subtractAvaliableBalance(BigDecimal amount) {
        setAvailableBalance(getAvailableBalance().subtract(amount));
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

    public void checkWithdrawLimit(BigDecimal amount) {
        if (amount.compareTo(getWithdrawLimit()) > 0) {
            throw new IllegalArgumentException("Withdraw amount exceeded withdraw limit!");
        }
    }

    public void setWithdrawLimit(BigDecimal withdrawLimit) {
        if (withdrawLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Withdraw limit must be above zero.");
        }
        this.withdrawLimit = withdrawLimit;
    }

    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(BigDecimal transferLimit) {
        if (transferLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transfer limit must be above zero.");
        }
        this.transferLimit = transferLimit;
    }

    public BigDecimal getOverseasWithdrawLimit() {
        return overseasWithdrawLimit;
    }

    public void setOverseasWithdrawLimit(BigDecimal overseasWithdrawLimit) {
        if (overseasWithdrawLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Oversea withdrawal limit must be above zero.");
        }
        this.overseasWithdrawLimit = overseasWithdrawLimit;
    }

    public BigDecimal getOverseasTransferLimit() {
        return overseasTransferLimit;
    }

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

    public void printAccountInfo() {
        System.out.println("Account.Account ID:                 " + getId());
        System.out.println("Account.Account Name:               " + getName());
        System.out.println("Account.Account Status:             " + getStatus());
        System.out.println("Available Balance:          " + getAvailableBalance());
        System.out.println("Hold Balance:               " + getHoldBalance());
        System.out.println("Withdrawal Limit:           " + getWithdrawLimit());
        System.out.println("Transfer Limit:             " + getTransferLimit());
        System.out.println("Overseas Withdrawal Limit:  " + getOverseasWithdrawLimit());
        System.out.println("Overseas Transfer Limit:    " + getOverseasTransferLimit());
    }

}
