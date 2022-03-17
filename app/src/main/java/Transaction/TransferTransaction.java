package Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransferTransaction extends Transaction {
    private String toAccountId;
    private String message;
    private Date dateCompleted;

    public TransferTransaction(String accountId, String toAccountId, BigDecimal amount) {
        super(accountId, amount);
        setToAccountId(toAccountId);
        setDateCompleted(new Date());
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        if (this.getAccountId() == toAccountId) {
            throw new IllegalArgumentException("Account number must be different.");
        }
        this.toAccountId = toAccountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

}
