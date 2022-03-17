package Screen;

public class test {
    public static void main(String[] args)
    {
        ScreenStateContext stateContext =new ScreenStateContext();
        stateContext.SetAndPrintScreen(new Greeting());
        stateContext.SetAndPrintScreen(new CardPrompt());
        stateContext.SetAndPrintScreen(new CardPrompt(false));
        stateContext.SetAndPrintScreen(new PinPrompt());
        stateContext.SetAndPrintScreen(new PinPrompt(3));
        stateContext.SetAndPrintScreen(new MainOption());
        stateContext.SetAndPrintScreen(new MainOption(4));

    }
}
