import java.io.FileNotFoundException;
import java.io.IOException;

import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Transaction.Transaction;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            DataSource<Transaction> txnDataSource = new TransactionDataSource();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getClass());
        }
    }
}
