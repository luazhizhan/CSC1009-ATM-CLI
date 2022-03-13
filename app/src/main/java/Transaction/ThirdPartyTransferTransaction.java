package Transaction;

import java.math.BigDecimal;

public class ThirdPartyTransferTransaction extends TransferTransaction {
    private String byCustomerId;

    public ThirdPartyTransferTransaction(String accountId, String toAccountId,
            String byCustomerId, BigDecimal amount) {
        super(accountId, toAccountId, amount);
        setByCustomerId(byCustomerId);
    }

    public String getByCustomerId() {
        return byCustomerId;
    }

    public void setByCustomerId(String byCustomerId) {
        this.byCustomerId = byCustomerId;
    }
}
