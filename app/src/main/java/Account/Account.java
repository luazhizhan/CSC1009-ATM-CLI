import java.math.BigDecimal;

public abstract class Account {
    private String id;
    private String customerId;
    private String name;
    private enum status{
        NORMAL,
        FROZEN,
        CLOSED
    }
    private BigDecimal availableBalance;
    private BigDecimal holdBalance;
    private BigDecimal withdrawLimit;
    private BigDecimal transferLimit;
    private BigDecimal overseasWithdrawLimit;
    private BigDecimal overseasTransferLimit;

    public Account(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
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
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    public BigDecimal getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(BigDecimal withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(BigDecimal transferLimit) {
        this.transferLimit = transferLimit;
    }

    public BigDecimal getOverseasWithdrawLimit() {
        return overseasWithdrawLimit;
    }

    public void setOverseasWithdrawLimit(BigDecimal overseasWithdrawLimit) {
        this.overseasWithdrawLimit = overseasWithdrawLimit;
    }

    public BigDecimal getOverseasTransferLimit() {
        return overseasTransferLimit;
    }

    public void setOverseasTransferLimit(BigDecimal overseasTransferLimit) {
        this.overseasTransferLimit = overseasTransferLimit;
    }

    public abstract void printAccountInfo();

}
