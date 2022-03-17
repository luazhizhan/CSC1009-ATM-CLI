package Screen;


class Greeting implements ScreenState{
    String Greeting;
    Greeting()
    {
        Greeting = line + '\n' + "Welcome to OOP ATM" + '\n' + line;
    }

    @Override
    public void PrintScreen(ScreenStateContext stateContext)
    {
        System.out.println(Greeting);
    }
}
