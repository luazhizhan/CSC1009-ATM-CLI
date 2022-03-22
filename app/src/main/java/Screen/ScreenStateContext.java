package Screen;

public class ScreenStateContext {
    private ScreenState currentState;

    public void setAndPrintScreen(ScreenState state) {
        currentState = state;// set it to the different screen states
        currentState.printScreen(this); // print the screen state create object of the state you wish to print and pass
                                        // parameters
    }

}
