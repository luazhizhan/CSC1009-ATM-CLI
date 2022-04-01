package View;

public class Greeting implements ViewState {
    private String Greeting;

    public Greeting() {
        Greeting = line + '\n' + "Welcome Customer!" + '\n' + line;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(Greeting);
    }
}
