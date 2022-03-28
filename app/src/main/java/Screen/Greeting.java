package Screen;

public class Greeting implements ScreenState {
    private String Greeting;

    public Greeting() {
        Greeting = line + '\n' + "Welcome Customer!" + '\n' + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(Greeting);
    }
}
