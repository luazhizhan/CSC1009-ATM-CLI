package View;

/**
 * Screen classes interface to enforce methods to be implemented and to DRY code
 */
public interface ViewState {
    final static String line = "--------------------------------------------------";
    final static String invalidInput = "\n" + line + "\n" + "Invalid input! Please try again.\n" + line;

    public void printScreen(ViewStateContext stateContext); // used to print the screen
}