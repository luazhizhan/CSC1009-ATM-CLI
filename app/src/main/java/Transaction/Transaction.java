package Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public abstract class Transaction {
    private String id;
    private String accountId;
    private BigDecimal amount;
    private Date dateCreated;

    public Transaction(String accountId, BigDecimal amount) {
        setAccountId(accountId);
        setAmount(amount);
        setId(UUID.randomUUID().toString());
        setDateCreated(new Date());
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
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

    public void setAmount(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new IllegalArgumentException("Please enter a number > 0.");
        }
        this.amount = amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    private void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
