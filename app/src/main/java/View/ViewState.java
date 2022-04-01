package View;

/**
 * View classes interface to enforce methods to be implemented and to DRY code
 */
public interface ViewState {
    final static String line = "--------------------------------------------------";
    final static String invalidInput = "\n" + line + "\n" + "Invalid input! Please try again.\n" + line;

    public void print(ViewStateContext stateContext); // used to print the view
}
