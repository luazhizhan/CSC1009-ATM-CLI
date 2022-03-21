import java.math.BigDecimal;

public class Current extends Account{
    private BigDecimal overDraftLimit;

    public Current(String customerId, String name, BigDecimal overDraftLimit) {
        super(customerId, name);
        this.overDraftLimit = overDraftLimit;
    }

    public BigDecimal getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(BigDecimal overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }

    public void printAccountInfo(){
        System.out.println("Account: "+super.getId());
        System.out.println("Available Balance: "+super.getAvailableBalance());
        System.out.println("Hold Balance: "+super.getHoldBalance());
    }

    @java.lang.Override
    public BigDecimal getAvailableBalance() {
        BigDecimal availableBalance = super.getAvailableBalance().add(this.overDraftLimit);
        return availableBalance;
    }
}
