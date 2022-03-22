package Account;

import Account.Account;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private BigDecimal overDraftLimit;

    public CurrentAccount(String id, String customerId, String name, AccountStatus status, BigDecimal overDraftLimit) {
        super(id, customerId, name, status);
        this.overDraftLimit = overDraftLimit;
    }

    public BigDecimal getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(BigDecimal overDraftLimit) {
        if(overDraftLimit.compareTo(BigDecimal.ZERO)>0){
            this.overDraftLimit = overDraftLimit;
        }
        else{
            System.out.println("Overdraft Limit below Zero!");
        }

    }

    @Override
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
        System.out.println("Overdraft Limit:            "+getOverDraftLimit());
    }

    @Override
    public void setAvailableBalance(BigDecimal availableBalance) {
        if(availableBalance.compareTo(overDraftLimit.negate())<0)
        {
            System.out.println("Exceeded Overdraft Limit!");
        }
        else {
            super.availableBalance = availableBalance;
        }
    }
}
