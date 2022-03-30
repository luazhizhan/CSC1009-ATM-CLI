package Screen;

/**
 * Screen class middleware class to for inital printing
 */
public class ScreenStateContext {
    private ScreenState currentState;

    /**
     * Controls how inital screen are printed on the screen
     * 
     * @param state
     */
    public void setAndPrintScreen(ScreenState state) {
        currentState = state;// set it to the different screen states
        currentState.printScreen(this); // print the screen state create object of the state you wish to print and pass
                                        // parameters
    }

}
