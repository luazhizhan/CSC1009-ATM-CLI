package src.main.java;

import java.util.UUID;



// Basic implementation to test my stuff
public class Card {
    
    // Lazy implementation, edit data type later after review with team
    private final String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    
    // Random 6 digit number for now, idk what formats and stuff PINs have, so this is temp
    private final int cardPIN;
    public int getCardPIN() {
        return cardPIN;
    }
    
    public Card(int accountPIN) {
        this.cardNumber = UUID.randomUUID().toString();
        this.cardPIN = accountPIN;
    }
}
