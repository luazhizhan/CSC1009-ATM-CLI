package Model.Transaction;

import java.math.BigDecimal;
import java.util.Date;

import Helper.Id;

/**
 * Abstract class for different Transaction
 */
public abstract class Transaction {
    private String id;
    private String accountId;
    private BigDecimal amount;
    private Date dateCreated;

    public Transaction() {
        setId(Id.generateUUID());
    }

    public Transaction(String accountId, BigDecimal amount) {
        setAccountId(accountId);
        setAmount(amount);
        setId(Id.generateUUID());
        setDateCreated(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Amount must be bigger than 0
     * 
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new IllegalArgumentException("Please enter a number > 0.");
        }
        this.amount = amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
