package Screen;

public interface ScreenState {
    final static String line = "--------------------------------------------------";
    final static String invalidInput = "\n" + line + "\n" + "Invalid input! Please try again.\n" + line;

    public void printScreen(ScreenStateContext stateContext); // used to print the screen
}
