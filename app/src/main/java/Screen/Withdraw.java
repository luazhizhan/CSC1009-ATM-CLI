package Screen;

public class Withdraw implements ScreenState {
    private String prompt;

    public Withdraw() {
        prompt = line + "\nCash Withdrawal\n" + line + "\n Please Choose From The Following Amounts\n";
        prompt += "\n1. $50\n2.  $100\n3.  $200\n4.  $500\n5.  Other Amount\n6.  Back";

    }

    public Withdraw(int choice) {
        prompt = line + "Please Enter Amount to Withdraw in Multiples of 10:\n";
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }
}
