package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import Transaction.Transaction;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDataSourceTest {

    @Test
    public void success() throws FileNotFoundException, IOException {
        DataSource<Transaction> txnDataSource = new TransactionDataSource();
        Transaction cashTxn = txnDataSource.getDataById("41275c16-bead-4e90-af09-d1de693a2284");
        Transaction tranfersTxn = txnDataSource.getDataById("553c960b-0200-43dd-a802-e08d91d02a19");
        assertEquals(cashTxn.getAmount(), new BigDecimal(70));
        assertEquals(tranfersTxn.getAmount(), new BigDecimal(60));
    }
}
