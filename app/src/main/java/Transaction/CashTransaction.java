package Transaction;

import java.math.BigDecimal;

public class CashTransaction extends Transaction {

    private String atmId;
    private TransactionType type;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW
    }

    public CashTransaction(String accountId, BigDecimal amount,
            String atmId, TransactionType type) {
        super(accountId, amount);
        setAtmId(atmId);
        setType(type);
    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

}
