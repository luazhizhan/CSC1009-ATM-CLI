package Account;

import java.math.BigDecimal;

/**
 * Saving bank account
 */
public class SavingsAccount extends Account {
    private BigDecimal interestRate = new BigDecimal("0.0005"); // 0.5%

    public SavingsAccount(String id, String customerId, String name, AccountStatus status) {
        super(id, customerId, name, status);
        setInterestRate(interestRate);
    }

    public SavingsAccount(String id, String customerId, String name) {
        super(id, customerId, name);
        setInterestRate(interestRate);
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    /**
     * Interest rate must be > 0
     * 
     * @param interestRate
     */
    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Interest Rate cannot be below or equal to zero.");
        } else {
            this.interestRate = interestRate;
        }
    }

    @Override
    public void printAccountInfo() {
        System.out.println("Account.Account ID:                   " + getId());
        System.out.println("Account.Account Name:                 " + getName());
        System.out.println("Account.Account Status:               " + getStatus());
        System.out.println("Available Balance:                    " + getAvailableBalance());
        System.out.println("Hold Balance:                         " + getHoldBalance());
        System.out.println("Withdrawal Limit:                     " + getWithdrawLimit());
        System.out.println("Transfer Limit:                       " + getTransferLimit());
        System.out.println("Overseas Withdrawal Limit:            " + getOverseasWithdrawLimit());
        System.out.println("Overseas Transfer Limit:              " + getOverseasTransferLimit());
        System.out.println("Savings Account.Account Interest Rate:" + getInterestRate());
    }

    public void rewardInterest() {
        BigDecimal availableBalance = super.getAvailableBalance();
        BigDecimal balanceAfterInterest = availableBalance.add(availableBalance.multiply(interestRate));
        super.setAvailableBalance(balanceAfterInterest);
    }
}
