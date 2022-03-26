package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.AccountStatus;
import Account.CurrentAccount;
import Transaction.CashTransaction;
import Transaction.Transaction;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDataSourceTest {
    private DataSource<Transaction> txnDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        txnDataSource = new TransactionDataSource();
    }

    @Test
    public void success() {
        Transaction cashTxn = txnDataSource.getDataById("41275c16bead4e90af09d1de693a2284");
        Transaction tranfersTxn = txnDataSource.getDataById("553c960b020043dda802e08d91d02a19");
        assertEquals(cashTxn.getAmount(), new BigDecimal(70));
        assertEquals(tranfersTxn.getAmount(), new BigDecimal(60));
    }

    @Test
    public void successAddTransaction() {
        Account account = new CurrentAccount("6454856238", "3314572", "Tom", AccountStatus.NORMAL);
        int previousSize = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId()).size();
        Transaction txn = new CashTransaction(account.getId(), new BigDecimal(120), "123",
                CashTransaction.TransactionType.WITHDRAW);

        txnDataSource.add(txn);
        List<Transaction> txns = ((TransactionDataSource) txnDataSource).getDataByAccountId(account.getId());
        assertEquals(new BigDecimal(120), txns.get(0).getAmount());
        assertEquals(txns.size(), previousSize + 1);
    }
}
