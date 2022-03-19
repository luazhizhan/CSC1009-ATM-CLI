package Screen;

public class CardPrompt implements ScreenState {
    private String prompt;
    public CardPrompt()
    {
        prompt = line+"\nPlease Enter Your Card Number: ";
    }
    public CardPrompt(boolean valid)
    {
        prompt = line+"\nInvalid Card Number!\nPlease Re-Enter Card Number";
    }
    /*
    public CardPrompt(Customer cust)
    {
        prompt = line + "\nWelcome " + cust.name;
    }
     */
    @Override
    public void PrintScreen(ScreenStateContext stateContext)
    {
        System.out.println(prompt);
    }

}
