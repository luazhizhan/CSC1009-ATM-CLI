package Screen;

public interface ScreenState
{
    final static String line = "--------------------------------------------------";
    public void PrintScreen(ScreenStateContext stateContext); //used to print the screen
    //States that the screen have
    //void Greeting(); //Print welcome
    //void CardPrompt(); // pass false in for invalid number pass customer object for valid number
    //void PinPrompt(); //pass number of tries if incorrect. (start from 0) once 3, will print contact bank
    //void MainOptions();
    //void Withdraw();//overload
    //void EnterAmount(int type); // 0 deposit 1 withdraw
    //void Deposit(); //overload to display the rest of the transactions
    //void Transfer();// overload
    //void PrintReciept();//overload
    //void Complete();
    //void Cancelled();
    //void limits();//overload
    //void Errors();

}
