package src.main.java;


// Dummy class for testing
public class Transaction {
    
    private String TransactionID = "TR00001";

    // Format the trasnsaction data to be printed here
    // For printing the transaction. Used by printer in ATM.
    public void print() {
        System.out.println(TransactionID);
    }
}
