package src.main.Enrique.ATM;


// Mostly/very unnecessary (for given scope) unless we expand with a higher level management system i.e. bank, ATM operators, etc.
// Leaving it here for now since I've written it.
public enum ATMStatus {
    WORKING(0),
    DOWN(-1),
    MAINTENANCE(1);

    private final int statusCode;

    public int getStatus() {
        return statusCode;
    }

    ATMStatus(int statusCode) {
        this.statusCode = statusCode;
    }
}