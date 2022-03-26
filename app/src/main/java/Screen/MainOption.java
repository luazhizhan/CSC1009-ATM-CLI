package Screen;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainOption implements ScreenState {
    private String printContents;

    public MainOption() {
        printContents = "\n" + line
                + "\nOptions\n\n1:  Cash Withdrawal \n2:  Cash Deposit \n3:  Bank Transfer \n4:  Manage Account \n"
                + "5:  Exit \n\nPlease enter your choice:\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(printContents);
    }

    public int getSelectedOption(Scanner in) {
        try {
            int option = in.nextInt();
            if (option < 1 || option > 5) {
                System.out.println("\n" + line + "\nNo such option available! Please try again.\n"
                        + line + "\n" + printContents);
                return -1;
            }

            if (option == 5) {
                System.out.println("Exit");
                System.exit(0); // Terminal program
            }
            return option;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput + "\n" + printContents);
            return -1;
        }
    }
}
