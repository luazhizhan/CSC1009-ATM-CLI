package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Transaction.CashTransaction;
import Transaction.Transaction;
import Transaction.TransferTransaction;

/**
 * Transaction data source from CSV file
 */
public class TransactionDataSource extends DataSource<Transaction> {
    private static final String CASH_CSV_PATH = "Transaction/cash_transactions.csv";
    private static final String TRANSFER_CSV_PATH = "Transaction/transfer_transactions.csv";
    private static final int CONVERT_TO_MILLISECONDS = 1000;

    /**
     * Override default constructor
     * Parse two files - cash_transactions and transfer_transactions csv
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public TransactionDataSource() throws FileNotFoundException, IOException {
        super();
        List<Transaction> transactions = parseCSVDataList(readDataFromCSV(CASH_CSV_PATH));
        transactions.addAll(parseCSVDataList(readDataFromCSV(TRANSFER_CSV_PATH)));
        setData(transactions);

    }

    /**
     * Parse two types of file - cash_transactions and transfer_transactions csv
     */
    @Override
    protected List<Transaction> parseCSVDataList(List<String[]> dataList) {
        List<Transaction> txnDataSource = new ArrayList<Transaction>();
        String[] first = dataList.get(0);

        // Cash transaction as no dateCompleted field found
        if (first.length < 7) {
            /**
             * id - 0
             * accountId - 1
             * amount - 2
             * dateCreated - 3
             * atmId - 4
             * type - 5
             */
            for (String[] data : dataList) {
                // Parse TransactionType string to TransactionType enum
                CashTransaction.TransactionType type;
                if (data[5].compareTo("DEPOSIT") == 0) {
                    type = CashTransaction.TransactionType.DEPOSIT;
                } else {
                    type = CashTransaction.TransactionType.WITHDRAW;
                }

                Transaction txn = new CashTransaction(data[1], new BigDecimal(data[2]), data[4], type);
                txn.setId(data[0]);
                txn.setDateCreated(new Date(Long.parseLong(data[3]) * CONVERT_TO_MILLISECONDS));
                txnDataSource.add(txn);
            }
            return txnDataSource;
        }

        // Transfer transaction
        for (String[] data : dataList) {
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
            txn.setId(data[0]);
            txn.setDateCreated(new Date(Long.parseLong(data[3]) * CONVERT_TO_MILLISECONDS));
            ((TransferTransaction) txn).setMessage(data[5]);
            ((TransferTransaction) txn).setDateCompleted(new Date(Long.parseLong(data[6]) * CONVERT_TO_MILLISECONDS));
            txnDataSource.add(txn);
        }
        return txnDataSource;
    }

    /**
     * Get transaction by transaction Id
     * 
     * @param id transaction id
     * @return Transaction
     */
    @Override
    public Transaction getDataById(String id) {
        return this.getData().stream()
                .filter(data -> data.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Get all transaction by account id sorted by date created descendingly
     * 
     * @param accountId
     * @return List<Transaction>
     */
    public List<Transaction> getDataByAccountId(String accountId) {
        return this.getData().stream()
                .filter(data -> {
                    if (data.getAccountId().equals(accountId))
                        return true;
                    if (data instanceof TransferTransaction)
                        return ((TransferTransaction) data).getToAccountId().equals(accountId);
                    return false;
                })
                .sorted((a, b) -> b.getDateCreated().compareTo(a.getDateCreated())).toList();
    }

    public void add(Transaction txn) {
        this.getData().add(txn);
    }

}
