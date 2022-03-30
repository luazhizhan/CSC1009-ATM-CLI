package Account;

/**
 * Invalid Account Custom Exception for code readability
 */
public class InvalidAccountException extends Exception {

    public InvalidAccountException(String errorMessage) {
        super(errorMessage);
    }
}
