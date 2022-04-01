package Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Account.Account;
import Model.Account.AccountStatus;
import Model.Account.CurrentAccount;
import Model.Currency.Currency;
import Model.Transaction.CashTransaction;
import Model.Transaction.Transaction;
import Model.Transaction.TransferTransaction;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDataTest {
    private Account account;
    private Data<Transaction> txnData;
    private Data<Currency> currencyData = null;

    // TransferTransaction transactionId of with "toAccountId" = mock accountId
    private static final String TFS_TO_ACCOUNT_ID_TXN_ID = "d72ff38fc005450696fe9f208f3728f7";

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        currencyData = new CurrencyData();
        account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL,
                currencyData.getDataById("SGD"));
        txnData = new TransactionData();
    }

    @Test
    public void success() {
        Transaction cashTxn = txnData.getDataById("41275c16bead4e90af09d1de693a2284");
        Transaction tranfersTxn = txnData.getDataById("553c960b020043dda802e08d91d02a19");
        assertEquals(cashTxn.getAmount(), new BigDecimal(70));
        assertEquals(tranfersTxn.getAmount(), new BigDecimal(60));
    }

    /**
     * Test for transfer transaction "toAccountId" == mock accountId is inside the
     * list
     */
    @Test
    public void successGetDataByAccountId() {
        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());

        // Find transfer transaction "toAccountId" that mataches this accountId.
        Transaction tfsTxn = txns.stream()
                .filter(data -> data.getId().equals(TFS_TO_ACCOUNT_ID_TXN_ID))
                .findFirst().orElse(null);
        assertEquals(TFS_TO_ACCOUNT_ID_TXN_ID, tfsTxn.getId());
        assertEquals(account.getId(), ((TransferTransaction) tfsTxn).getToAccountId());
    }

    @Test
    public void successAddTransaction() {
        int previousSize = ((TransactionData) txnData).getDataByAccountId(account.getId()).size();
        Transaction txn = new CashTransaction(account.getId(), new BigDecimal(120), "123",
                CashTransaction.TransactionType.WITHDRAW);

        txnData.add(txn);
        List<Transaction> txns = ((TransactionData) txnData).getDataByAccountId(account.getId());
        assertEquals(new BigDecimal(120), txns.get(0).getAmount());
        assertEquals(txns.size(), previousSize + 1);
    }
}
