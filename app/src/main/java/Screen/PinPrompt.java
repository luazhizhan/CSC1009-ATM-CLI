package Screen;

public class PinPrompt implements ScreenState{
    String prompt;
    public PinPrompt()
    {
        prompt = "Please enter your PIN";
    }
    public PinPrompt(int tries)
    {
        switch (tries)
        {
            case 3:
                prompt = line+"\nToo Many Attempts! \nPlease Contact Bank For Assistance!\n"+line;
                break;

            default:
                prompt= line+"\nIncorrect PIN!\nPlease Re-enter your PIN!";
        }
    }

    @Override
    public void PrintScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

}
