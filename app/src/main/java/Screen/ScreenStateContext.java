package Screen;

public class ScreenStateContext {
    private ScreenState currentState;

    public ScreenStateContext()
    {
        currentState = new Greeting();

    }
    public void SetAndPrintScreen(ScreenState state)
    {
        currentState = state;//set it to the different screen states
        currentState.PrintScreen(this); //print the screen state create object of the state you wish to print and pass parameters
    }


}
