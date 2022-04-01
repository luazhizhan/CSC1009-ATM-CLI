package Model.Atm;

/**
 * Insufficient Notes Exception for code readability. Consumed by Atm class
 */
public class InsufficientNotesException extends Exception {

    public InsufficientNotesException(String errorMessage) {
        super(errorMessage);
    }
}
