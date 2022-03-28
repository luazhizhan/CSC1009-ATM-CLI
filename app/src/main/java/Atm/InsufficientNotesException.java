package Atm;

public class InsufficientNotesException extends Exception {

    public InsufficientNotesException(String errorMessage) {
        super(errorMessage);
    }
}
