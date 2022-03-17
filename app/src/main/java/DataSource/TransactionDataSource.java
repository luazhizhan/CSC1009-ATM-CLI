package DataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Transaction.CashTransaction;
import Transaction.Transaction;
import Transaction.TransferTransaction;

public class TransactionDataSource extends DataSource<Transaction> {
    private static final String CASH_CSV_PATH = "Transaction/cash_transactions.csv";
    private static final String TRANSFER_CSV_PATH = "Transaction/transfer_transactions.csv";

    public TransactionDataSource() throws FileNotFoundException, IOException {
        super();
    }

    @Override
    protected List<Transaction> readDataFromCSV() throws FileNotFoundException, IOException {
        List<Transaction> txnDataSource = new ArrayList<Transaction>();

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(CASH_CSV_PATH);
        try (InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)) {
            br.readLine(); // Ignore first row
            String line;

            // Keep reading until last line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                /**
                 * id - 0
                 * accountId - 1
                 * amount - 2
                 * dateCreated - 3
                 * atmId - 4
                 * type - 5
                 */
                CashTransaction.TransactionType type;
                if (data[5] == "DEPOSIT") {
                    type = CashTransaction.TransactionType.DEPOSIT;
                } else {
                    type = CashTransaction.TransactionType.WITHDRAW;
                }
                Transaction txn = new CashTransaction(data[1], new BigDecimal(data[2]), data[4], type);
                txn.setAccountId((data[0]));
                txn.setDateCreated(new Date(Long.parseLong(data[3])));
                txnDataSource.add(txn);
            }
        }

        is = this.getClass().getClassLoader().getResourceAsStream(TRANSFER_CSV_PATH);
        try (InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)) {
            br.readLine(); // Ignore first row
            String line;

            // Keep reading until last line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                /**
                 * id - 0
                 * accountId - 1
                 * amount - 2
                 * dateCreated - 3
                 * toAccountId - 4
                 * message - 5
                 * dateCompleted - 6
                 */
                Transaction txn = new TransferTransaction(data[1], data[4], new BigDecimal(data[2]));
                txn.setAccountId((data[0]));
                txn.setDateCreated(new Date(Long.parseLong(data[3])));
                ((TransferTransaction) txn).setDateCompleted(new Date(Long.parseLong(data[6])));
                txnDataSource.add(txn);
            }
        }
        return txnDataSource;
    }

    @Override
    public Transaction getDataById(String id) {
        return this.getData().stream()
                .filter(data -> data.getAccountId().equals(id))
                .findFirst().orElse(null);
    }

}
