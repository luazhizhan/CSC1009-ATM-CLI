package View;

/**
 * View class middleware class to for inital printing
 */
public class ViewStateContext {
    private ViewState currentState;

    /**
     * Controls how inital view are printed on the view
     * 
     * @param state
     */
    public void setAndPrint(ViewState state) {
        currentState = state;// set it to the different view states
        currentState.print(this); // print the view state create object of the state you wish to print and pass
                                  // parameters
    }

}
