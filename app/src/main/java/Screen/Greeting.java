package Screen;

public class Greeting implements ScreenState{
    private String Greeting;
    public Greeting()
    {
        Greeting = line + '\n' + "Welcome to OOP ATM" + '\n' + line;
    }

    @Override
    public void PrintScreen(ScreenStateContext stateContext)
    {
        System.out.println(Greeting);
    }
}
