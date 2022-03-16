package Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class ThirdPartyTransferTransactionTest {
    @Test
    public void successTransfer() {
        String accountId = UUID.randomUUID().toString();
        String toAccountId = UUID.randomUUID().toString();
        String byCustomerId = UUID.randomUUID().toString();
        BigDecimal amt = new BigDecimal(10);

        ThirdPartyTransferTransaction txn = new ThirdPartyTransferTransaction(accountId,
                toAccountId, byCustomerId, amt);

        assertEquals(accountId, txn.getAccountId());
        assertEquals(toAccountId, txn.getToAccountId());
        assertEquals(amt, txn.getAmount());
        assertNotNull(txn.getDateCompleted());
        assertNull(txn.getMessage());
    }

    @Test
    public void successTransferWithMsg() {
        String accountId = UUID.randomUUID().toString();
        String toAccountId = UUID.randomUUID().toString();
        String byCustomerId = UUID.randomUUID().toString();
        BigDecimal amt = new BigDecimal(10);
        String msg = "Hello World";

        ThirdPartyTransferTransaction txn = new ThirdPartyTransferTransaction(accountId,
                toAccountId, byCustomerId, amt);
        txn.setMessage(msg);

        assertEquals(accountId, txn.getAccountId());
        assertEquals(toAccountId, txn.getToAccountId());
        assertEquals(amt, txn.getAmount());
        assertNotNull(txn.getDateCompleted());
        assertEquals(msg, txn.getMessage());
    }

    @Test
    public void failureTransferToSameAccountId() {
        String accountId = UUID.randomUUID().toString();
        String byCustomerId = UUID.randomUUID().toString();
        BigDecimal amt = new BigDecimal(5.3);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ThirdPartyTransferTransaction(accountId,
                        accountId, byCustomerId, amt));
        assertEquals("Account number must be different.", exception.getMessage());
    }

    @Test
    public void failureIllegalAmount() {
        String accountId = UUID.randomUUID().toString();
        String toAccountId = UUID.randomUUID().toString();
        String byCustomerId = UUID.randomUUID().toString();
        BigDecimal illegalAmt = new BigDecimal(0);
        BigDecimal illegalAmt2 = new BigDecimal(-10.5);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ThirdPartyTransferTransaction(accountId, toAccountId,
                        byCustomerId, illegalAmt));
        assertEquals("Please enter a number > 0.", exception.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> new ThirdPartyTransferTransaction(accountId, toAccountId,
                        byCustomerId, illegalAmt2));
        assertEquals("Please enter a number > 0.", exception2.getMessage());
    }
}
