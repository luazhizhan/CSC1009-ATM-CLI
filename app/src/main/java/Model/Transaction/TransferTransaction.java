package Model.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransferTransaction extends Transaction {
    private String toAccountId;
    private String message;
    private Date dateCompleted;

    public enum Type {
        SENT,
        RECEIVED,
    };

    public TransferTransaction(String accountId, String toAccountId, BigDecimal amount) {
        super(accountId, amount);
        setToAccountId(toAccountId);
        setDateCompleted(new Date());
    }

    /**
     * Check if this transfer transaction received(+) or sent(+) using
     * accountId argument
     * 
     * @param accountId
     * @return
     */
    public Type isReceivedOrSent(String accountId) {
        if (accountId.equals(this.getAccountId())) {
            return Type.SENT;
        }
        if (accountId.equals(this.getToAccountId())) {
            return Type.RECEIVED;
        }
        return null;
    }

    public String toTypeString(Type type) {
        return type.equals(Type.SENT) ? "SENT" : "RECEIVED";
    }

    public String getToAccountId() {
        return toAccountId;
    }

    /**
     * Must not be same account id
     * 
     * @param toAccountId
     */
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
