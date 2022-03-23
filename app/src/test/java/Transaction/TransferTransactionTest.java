package Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import Helper.Id;

public class TransferTransactionTest {

    @Test
    public void successTransfer() {
        String accountId = Id.generateUUID();
        String toAccountId = Id.generateUUID();
        BigDecimal amt = new BigDecimal(10);

        TransferTransaction txn = new TransferTransaction(accountId, toAccountId, amt);

        assertEquals(accountId, txn.getAccountId());
        assertEquals(toAccountId, txn.getToAccountId());
        assertEquals(amt, txn.getAmount());
        assertNotNull(txn.getDateCompleted());
        assertNull(txn.getMessage());
    }

    @Test
    public void successTransferWithMsg() {
        String accountId = Id.generateUUID();
        String toAccountId = Id.generateUUID();
        BigDecimal amt = new BigDecimal(12.42);
        String msg = "Hello World";

        TransferTransaction txn = new TransferTransaction(accountId, toAccountId, amt);
        txn.setMessage(msg);

        assertEquals(accountId, txn.getAccountId());
        assertEquals(toAccountId, txn.getToAccountId());
        assertEquals(amt, txn.getAmount());
        assertNotNull(txn.getDateCompleted());
        assertEquals(msg, txn.getMessage());
    }

    @Test
    public void failureTransferToSameAccountId() {
        String accountId = Id.generateUUID();
        BigDecimal amt = new BigDecimal(5.3);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new TransferTransaction(accountId, accountId, amt));
        assertEquals("Account number must be different.", exception.getMessage());
    }

    @Test
    public void failureIllegalAmount() {
        String accountId = Id.generateUUID();
        String toAccountId = Id.generateUUID();
        BigDecimal illegalAmt = new BigDecimal(0);
        BigDecimal illegalAmt2 = new BigDecimal(-10.5);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new TransferTransaction(accountId, toAccountId, illegalAmt));
        assertEquals("Please enter a number > 0.", exception.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> new TransferTransaction(accountId, toAccountId, illegalAmt2));
        assertEquals("Please enter a number > 0.", exception2.getMessage());
    }
}
