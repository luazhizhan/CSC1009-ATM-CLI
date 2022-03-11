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

    }
    public void rewardInterest(){
        System.out.println("Account: "+super.getId());
        System.out.println("Available Balance: "+super.getAvailableBalance());
        System.out.println("Hold Balance: "+super.getHoldBalance());
    }
}
