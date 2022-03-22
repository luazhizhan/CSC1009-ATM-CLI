package Account;

import Account.Account;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    private BigDecimal interestRate = new BigDecimal("0.0005");

    public SavingsAccount(String id, String customerId, String name, AccountStatus status, BigDecimal interestRate) {
        super(id, customerId, name, status);
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if(interestRate.compareTo(BigDecimal.ZERO)<=0){
            System.out.println("Interest Rate Below or equal to Zero!");
        }else{
            this.interestRate = interestRate;
        }
    }

    @Override
    public void printAccountInfo(){
        System.out.println("Account.Account ID:                   "+getId());
        System.out.println("Account.Account Name:                 "+ getName());
        System.out.println("Account.Account Status:               "+getStatus());
        System.out.println("Available Balance:                    "+getAvailableBalance());
        System.out.println("Hold Balance:                         "+getHoldBalance());
        System.out.println("Withdrawal Limit:                     "+getWithdrawLimit());
        System.out.println("Transfer Limit:                       "+getTransferLimit());
        System.out.println("Overseas Withdrawal Limit:            "+getOverseasWithdrawLimit());
        System.out.println("Overseas Transfer Limit:              "+getOverseasTransferLimit());
        System.out.println("Savings Account.Account Interest Rate:" +getInterestRate());
    }
    public void rewardInterest(){
        BigDecimal balanceAfterInterest = super.getAvailableBalance().add(super.getAvailableBalance().multiply(interestRate));

    }
}
