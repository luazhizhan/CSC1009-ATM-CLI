package Account;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private BigDecimal overDraftLimit;

    public CurrentAccount(String id, String customerId, String name, AccountStatus status, String currencyCode) {
        super(id, customerId, name, status, currencyCode);
    }

    public CurrentAccount(String id, String customerId, String name, String currencyCode) {
        super(id, customerId, name, currencyCode);
    }

    @Override
    protected void setDefaultLimits() {
        super.setDefaultLimits();
        setOverDraftLimit(new BigDecimal(super.DEFAULT_LIMIT));
    }

    public BigDecimal getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(BigDecimal overDraftLimit) {
        if (overDraftLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Over draft limit must be above zero.");
        }
        this.overDraftLimit = overDraftLimit;
    }

    @Override
    public void setAvailableBalance(BigDecimal availableBalance) {
        // available balance < negative over draft limit
        if (availableBalance.compareTo(overDraftLimit.negate()) < 0) {
            throw new IllegalArgumentException("Exceeded Overdraft Limit!");
        }
        super.availableBalance = availableBalance;
    }

    @Override
    public void checkAgainstAvailableBalance(BigDecimal amount) {
        if (amount.compareTo(getAvailableBalance().add(getOverDraftLimit())) > 0) {
            throw new IllegalArgumentException("Amount exceeded available balance!");
        }
    }

    @Override
    public void printAccountInfo() {
        super.printAccountInfo();
        System.out.println("Overdraft Limit:            " + getOverDraftLimit());
    }
}
