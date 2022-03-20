package Screen;

public class ScreenStateContext {
    private ScreenState currentState;

    public void setScreenState(ScreenState currentState) {
        this.currentState = currentState;
    }

    public ScreenState getScreenState() {
        return currentState;
    }

    public void printScreen() {
        currentState.printScreen(this);
    }

    public void printScreen(ScreenState currentState) {
        // set it to the different screen states
        setScreenState(currentState);

        // print the screen state create object of the state
        // you wish to print and pass parameters
        currentState.printScreen(this);
    }

}
