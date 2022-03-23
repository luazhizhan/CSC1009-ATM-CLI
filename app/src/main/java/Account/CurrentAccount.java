package Account;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private BigDecimal overDraftLimit;

    public CurrentAccount(String id, String customerId, String name, AccountStatus status) {
        super(id, customerId, name, status);
    }

    public CurrentAccount(String id, String customerId, String name) {
        super(id, customerId, name);
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
        // avaliable balance < negative over draft limit
        if (availableBalance.compareTo(overDraftLimit.negate()) < 0) {
            throw new IllegalArgumentException("Exceeded Overdraft Limit!");
        }
        this.setAvailableBalance(availableBalance);
    }

    @Override
    public void printAccountInfo() {
        super.printAccountInfo();
        System.out.println("Overdraft Limit:            " + getOverDraftLimit());
    }
}
