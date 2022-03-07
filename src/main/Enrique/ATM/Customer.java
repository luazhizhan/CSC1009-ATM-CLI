package src.main.Enrique.ATM;

import java.util.UUID;



// Basic implementation to test my stuff
public class Customer {
    
    // Lazy implementation, edit data type later after review with team
    private final String CustomerID;
    public String getCustomerID() {
        return CustomerID;
    }

    Card card;
    Account account;

    public Customer() {
        CustomerID = UUID.randomUUID().toString();
        // card = new Card();
        account = new Account(this.getCustomerID());
        card = new Card(this.account.getPIN());
        account.setCardNumber(this.card.getCardNumber());
    }
}
