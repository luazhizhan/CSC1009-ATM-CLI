package View;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TimeZone;

import Data.Data;
import Data.TransactionData;
import Model.Transaction.CashTransaction;
import Model.Transaction.Transaction;
import Model.Transaction.TransferTransaction;

/**
 * Print account transaction history screen class
 */
public class TransactionHistory implements ViewState {
    private String prompt;

    public TransactionHistory() {
        prompt = "\n" + line + "\nTransaction History\n" + line;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Print transaction history (5 per page) of account if there are any
     * 
     * @param in
     * @param accountId
     * @param ds
     */
    public void printTxnHistory(Scanner in, String accountId, Data<Transaction> ds) {
        try {
            // Get account's transactions, order by date completed descendingly
            List<Transaction> txns = ((TransactionData) ds).getDataByAccountId(accountId);

            // Return to main if no transactions are found
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

                    if (txn instanceof CashTransaction) { // Cash Transaction
                        CashTransaction cashTxn = ((CashTransaction) txn);
                        System.out.println("Type: CASH " + cashTxn.getTypeInString());

                    } else { // Transfer Transaction

                        TransferTransaction tfsTxn = ((TransferTransaction) txn);
                        TransferTransaction.Type tfsType = tfsTxn.isReceivedOrSent(accountId);
                        System.out.println("Type: " + tfsTxn.toTypeString(tfsType));

                        // Print according to transfer transaction type
                        if (tfsType.equals(TransferTransaction.Type.RECEIVED)) {
                            System.out.println("From account Id: " + tfsTxn.getAccountId());
                        } else {
                            System.out.println("To account Id: " + tfsTxn.getToAccountId());
                        }

                        System.out.println("Message: " + tfsTxn.getMessage());
                    }
                    System.out.println("Amount: " + moneyFormatter.format(txn.getAmount()) + "\n");
                }

                // Increase paging indexes by 5
                start += 5;
                end += 5;

                // Return to main options if no transaction left
                if (start >= txns.size()) {
                    System.out.println("End of transactions history\n" + line);
                    return;
                }

                // Ask user whether he/she want to continue or quit
                // if there are transactions left
                System.out.println("Enter to continue or 0 to quit.\n" + line);
                String option = in.nextLine();
                if (option.compareToIgnoreCase("0") == 0) {
                    return;
                }

            }
        } catch (NoSuchElementException e) {
            System.out.println(ViewState.invalidInput);
        }
    }
}
