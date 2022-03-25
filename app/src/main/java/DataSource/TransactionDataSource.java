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

public class TransactionDataSource extends DataSource<Transaction> {
    private static final String CASH_CSV_PATH = "Transaction/cash_transactions.csv";
    private static final String TRANSFER_CSV_PATH = "Transaction/transfer_transactions.csv";

    public TransactionDataSource() throws FileNotFoundException, IOException {
        super();
        List<Transaction> transactions = readDataFromCSV(CASH_CSV_PATH);
        transactions.addAll(readDataFromCSV(TRANSFER_CSV_PATH));
        setData(transactions);

    }

    @Override
    protected List<Transaction> readDataFromCSV(String filePath) throws FileNotFoundException, IOException {
        List<Transaction> txnDataSource = new ArrayList<Transaction>();
        List<String[]> dataList = super.readDataFromCSV(filePath);
        String[] data;
        if(filePath.equals(CASH_CSV_PATH))
        {
            /**
             * id - 0
             * accountId - 1
             * amount - 2
             * dateCreated - 3
             * atmId - 4
             * type - 5
             */
            for (int i = 0; i < dataList.size(); i++) {
                data = dataList.get(i);
                CashTransaction.TransactionType type;
                if (data[5].compareTo("DEPOSIT")==0) {
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
        else if(filePath.equals(TRANSFER_CSV_PATH))
        {
            /**
             * id - 0
             * accountId - 1
             * amount - 2
             * dateCreated - 3
             * toAccountId - 4
             * message - 5
             * dateCompleted - 6
             */
            for(int i = 0; i < dataList.size(); i++)
            {
                data = dataList.get(i);
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
