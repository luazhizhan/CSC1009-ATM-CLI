package Screen;

public class Deposit implements ScreenState {

    private String prompt;

    public Deposit() {
        prompt = line + "\nPlease Enter the number of notes deposited in the following format ($10,$50):\n";
    }

    public Deposit(Boolean valid) {
        if (!valid) {
            prompt = line + "\nInvalid Input!";
        }
    }

    Deposit(int ten, int fifty) {
        prompt = line + "\nPlease Confirm the Number of Notes and Amount Deposited\n";
        prompt += "$10:  " + ten + "  $50:  " + fifty;
        prompt += "\nTotal Amount:  $" + ((10 * ten) + (50 * fifty));
        prompt += "\n1.  Confirm\n2.  Cancel";
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

}
