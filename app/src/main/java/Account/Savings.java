import java.math.BigDecimal;

public class Savings extends Account{
    private BigDecimal interestRate = new BigDecimal("0.0005");

    public Savings(String customerId, String name, BigDecimal interestRate) {
        super(customerId, name);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    public void printAccountInfo(){
        System.out.println("Account: "+super.getId());
        System.out.println("Available Balance: "+super.getAvailableBalance());
        System.out.println("Hold Balance: "+super.getHoldBalance());
    }
    public void rewardInterest(){
        BigDecimal balanceAfterInterest = super.getAvailableBalance().add(super.getAvailableBalance().multiply(interestRate));
        
    }
}
