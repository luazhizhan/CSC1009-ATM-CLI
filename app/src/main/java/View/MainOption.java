package View;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main options screent
 */
public class MainOption implements ViewState {
    private String printContents;

    /**
     * 1. Cash withdrawal
     * 2. Cash deposit
     * 3. Bank transfer
     * 4. Transaction history
     * 5. Manage account
     * 6. Exit
     */
    public MainOption() {
        printContents = "\n" + line
                + "\nOptions\n\n1:  Cash Withdrawal \n2:  Cash Deposit \n3:  Bank Transfer \n" +
                "4:  Transaction History \n5:  Manage Account \n6:  Exit \n\nPlease enter your choice:\n" + line;
    }

    @Override
    public void printScreen(ViewStateContext stateContext) {
        System.out.println(printContents);
    }

    /**
     * Get select option from user
     * 
     * @param in
     * @return select option in integer
     */
    public int getSelectedOption(Scanner in) {
        try {
            int option = in.nextInt();

            // Invalid option
            if (option < 1 || option > 6) {
                System.out.println("\n" + line + "\nNo such option available! Please try again.\n"
                        + line + "\n" + printContents);
                return -1;
            }

            if (option == 6) {
                System.out.println("Exit");
                System.exit(0); // Terminal program
            }
            return option;
        } catch (NoSuchElementException e) {
            System.out.println(ViewState.invalidInput + "\n" + printContents);
            return -1;
        }
    }
}
