package Screen;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TimeZone;

import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Transaction.CashTransaction;
import Transaction.Transaction;
import Transaction.TransferTransaction;

public class TransactionHistory implements ScreenState {
    private String prompt;

    public TransactionHistory() {
        prompt = "\n" + line + "\nTransaction History\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public void printTxnHistory(Scanner in, String accountId, DataSource<Transaction> ds) {
        try {
            List<Transaction> txns = ((TransactionDataSource) ds).getDataByAccountId(accountId);
            if (txns.size() == 0) {
                System.out.println("\n" + line + "\nNo transactions found\n" + line);
                return;
            }

            // Create time format and set timezone to Singapore
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy hh:mma");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));

            // Money format
            NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();

            // Print first five pages
            int start = 0;
            int end = 5;

            while (true) {
                System.out.println("\n" + line);

                // Reduce end index to txn size
                while (end > txns.size())
                    end--;

                for (int i = start; i < end; i++) {
                    Transaction txn = txns.get(i);
                    System.out.println("Transaction Id: " + txn.getId());
                    System.out.println("Date: " + dateFormatter.format(txn.getDateCreated()));

                    if (txn instanceof CashTransaction) {
                        CashTransaction cashTxn = ((CashTransaction) txn);
                        System.out.println("Type: CASH " + cashTxn.getTypeInString());
                    } else {
                        // Transfer Transaction
                        TransferTransaction tfsTxn = ((TransferTransaction) txn);
                        TransferTransaction.Type tfsType = tfsTxn.isReceivedOrSent(accountId);
                        System.out.println("Type: " + tfsTxn.toTypeString(tfsType));

                        if (tfsType.equals(TransferTransaction.Type.RECEIVED)) {
                            System.out.println("From account Id: " + tfsTxn.getAccountId());
                        } else {
                            System.out.println("To account Id: " + tfsTxn.getToAccountId());
                        }
                        System.out.println("Message: " + tfsTxn.getMessage());
                    }
                    System.out.println("Amount: " + moneyFormatter.format(txn.getAmount()) + "\n");
                }
                start += 5;
                end += 5;

                // End of all transaction
                if (start >= txns.size()) {
                    System.out.println("End of transactions history\n" + line);
                    return;
                }

                // Ask user whether he/she want to continue or quit
                System.out.println("Enter to continue or q to quit.\n" + line);
                String option = in.nextLine();
                if (option.toLowerCase().equals("q")) {
                    return;
                }

            }
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
        }
    }
}
