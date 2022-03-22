package Account;

import java.math.BigDecimal;

public abstract class Account {
    private String id; //Account ID
    private String customerId; //Customer ID
    private String name; //name of Account, not customer's name
    private AccountStatus status; //status of Account: Enum

    protected BigDecimal availableBalance; //available balance
    private BigDecimal holdBalance; //balance on hold
    private BigDecimal withdrawLimit; //withdrawal limit local
    private BigDecimal transferLimit; //transfer limit local
    private BigDecimal overseasWithdrawLimit; //withdrawal limit overseas
    private BigDecimal overseasTransferLimit; //transfer limit overseas

    public Account(String id, String customerId, String name, AccountStatus status){
        /*create account according to most important attributes*/
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.status = status;
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
        if(availableBalance.compareTo(BigDecimal.ZERO)>0){
            this.availableBalance = availableBalance;
        }
        else{
            System.out.println("Available Balance below Zero!");
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

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void printAccountInfo(){
        System.out.println("Account.Account ID:                 "+getId());
        System.out.println("Account.Account Name:               "+ getName());
        System.out.println("Account.Account Status:             "+getStatus());
        System.out.println("Available Balance:          "+getAvailableBalance());
        System.out.println("Hold Balance:               "+getHoldBalance());
        System.out.println("Withdrawal Limit:           "+getWithdrawLimit());
        System.out.println("Transfer Limit:             "+getTransferLimit());
        System.out.println("Overseas Withdrawal Limit:  "+getOverseasWithdrawLimit());
        System.out.println("Overseas Transfer Limit:    "+getOverseasTransferLimit());
    }

}
