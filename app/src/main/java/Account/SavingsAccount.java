package Account;

import java.math.BigDecimal;

import Currency.Currency;

/**
 * Saving bank account
 */
public class SavingsAccount extends Account {
    private BigDecimal interestRate = new BigDecimal("0.0005"); // 0.5%

    public SavingsAccount(String id, String customerId, String name, AccountStatus status, Currency currency) {
        super(id, customerId, name, status, currency);
        System.out.println(currency.getWithdrawMaximum());
        setInterestRate(interestRate);
    }

    public SavingsAccount(String id, String customerId, String name, Currency currency) {
        super(id, customerId, name, currency);
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

    public void rewardInterest() {
        BigDecimal availableBalance = super.getAvailableBalance();
        BigDecimal balanceAfterInterest = availableBalance.add(availableBalance.multiply(interestRate));
        super.setAvailableBalance(balanceAfterInterest);
    }
}
