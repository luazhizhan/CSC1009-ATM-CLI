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

    // private String location; // This got too complex, stay off this for a bit
    // thnx me

    // public String getLocation() {
    // return this.location;
    // }

    private Currency currency;

    public Currency getCurrency() {
        return this.currency;
    }

    // Can currently dispense amounts, algo sucks but it's a dummy
    private MoneyDispenser dispenser;
    private Screen screen;
    // Dummy PIN input getter set up
    private KeyPad keyPad;
    // Prints transactions
    private Printer printer;
    private CardReader cardReader;

    private int minimumWithdrawAmount, maxWithdrawAmount;

    public ATM(String location) {
        this.ATMID = UUID.randomUUID();
        this.setStatus(ATMStatus.WORKING);

        this.currency = CurrencyDatabase.banknotes.get(location);

        dispenser = new MoneyDispenser(location);
        this.screen = new Screen();
        this.keyPad = new KeyPad();
        this.printer = new Printer();
        this.cardReader = new CardReader();
    }

    // public ATM(Currency currency) {
    // this.ATMID = UUID.randomUUID();
    // this.setStatus(ATMStatus.WORKING);

    // this.currency = currency;

    // dispenser = new MoneyDispenser(location);
    // this.screen = new Screen();
    // this.keyPad = new KeyPad();
    // this.printer = new Printer();
    // this.cardReader = new CardReader();
    // }
}