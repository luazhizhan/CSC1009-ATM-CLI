package Screen;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Atm.Atm;

public class AtmList implements ScreenState {
    private String text;

    public AtmList() {
        // TODO Print the list of ATMs
        String atmList = "TODO List all ATMs";
        text = "\n" + line + "\nPlease select an ATM.\nEnter 0 to quit.\n"
                + atmList + "\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(text);
    }

    public Atm selectAtm(Scanner in) {
        try {
            int atmNo = in.nextInt();
            if (atmNo == 0) {
                System.out.println("Quit");
                System.exit(0); // Terminal program
            }

            // TODO Add more checks as necessary
            if (atmNo < 1) {
                System.out.println(ScreenState.invalidInput);
                return null;
            }

            // TODO Return the actual ATM
            return new Atm();
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
