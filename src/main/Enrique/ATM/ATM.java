package src.main.Enrique.ATM;

import java.util.UUID;


// ATM machine.
public class ATM {

    // Lazy implementation, edit data type later after review with team
    private final UUID ATMID;
    public UUID getATMID() {
        return ATMID;
    }

    private ATMStatus status;
    public ATMStatus getStatus() {
        return status;
    }
    // Only useful if we implement a management system for ATMs
    public void setStatus(ATMStatus status) {
        this.status = status;
    }

    private String location; // This got too complex, stay off this for a bit thnx me
    public String getLocation() {
        return this.location;
    }

    // Can currently dispense amounts, algo sucks but it's a dummy
    private CashDispenser dispenser;
    private Screen screen;
    // Dummy PIN input getter set up
    private KeyPad keyPad;
    // Prints transactions
    private Printer printer;
    private CardReader cardReader;
    
    
    public ATM(String location) {
        this.ATMID = UUID.randomUUID();
        this.setStatus(ATMStatus.WORKING);

        this.location = location;
        
        dispenser = new CashDispenser(location);
        this.screen = new Screen();
        this.keyPad = new KeyPad();
        this.printer = new Printer();
        this.cardReader = new CardReader();
    }
}