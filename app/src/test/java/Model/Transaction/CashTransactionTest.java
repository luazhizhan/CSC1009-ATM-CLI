package Model.Transaction;

import org.junit.jupiter.api.Test;

import Helper.Id;
import Model.Transaction.CashTransaction.TransactionType;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class CashTransactionTest {

    @Test
    public void successDeposit() {
        String accountId = Id.generateUUID();
        String atmId = Id.generateUUID();
        BigDecimal amt = new BigDecimal(10);

        CashTransaction txn = new CashTransaction(accountId, amt, atmId, TransactionType.DEPOSIT);
        assertEquals(accountId, txn.getAccountId());
        assertEquals(atmId, txn.getAtmId());
        assertEquals(amt, txn.getAmount());
        assertEquals(TransactionType.DEPOSIT, txn.getType());
    }

    @Test
    public void successWithdraw() {
        String accountId = Id.generateUUID();
        String atmId = Id.generateUUID();
        BigDecimal amt = new BigDecimal(10.3);

        CashTransaction txn = new CashTransaction(accountId, amt, atmId, TransactionType.WITHDRAW);
        assertEquals(accountId, txn.getAccountId());
        assertEquals(atmId, txn.getAtmId());
        assertEquals(amt, txn.getAmount());
        assertEquals(TransactionType.WITHDRAW, txn.getType());
    }

    @Test
    public void failureIllegalAmount() {
        String accountId = Id.generateUUID();
        String atmId = Id.generateUUID();
        BigDecimal illegalAmt = new BigDecimal(0);
        BigDecimal illegalAmt2 = new BigDecimal(-10.5);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CashTransaction(accountId, illegalAmt, atmId, TransactionType.DEPOSIT));
        assertEquals("Please enter a number > 0.", exception.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class,
                () -> new CashTransaction(accountId, illegalAmt2, atmId, TransactionType.DEPOSIT));
        assertEquals("Please enter a number > 0.", exception2.getMessage());
    }
}
