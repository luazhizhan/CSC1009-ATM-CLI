package Screen;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Atm.Atm;
import DataSource.AtmDataSource;
import DataSource.DataSource;

/**
 * List avaliable ATM screen class
 */
public class AtmList implements ScreenState {
    private String prompt;

    public AtmList() {
        prompt = "\n" + line + "\nPlease select an ATM.\nEnter 0 to exit.\n";
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * User selects ATM he/she like to use
     * 
     * @param in
     * @param ds
     * @return Atm object
     */
    public Atm selectAtm(Scanner in, DataSource<Atm> ds) {
        try {
            // Print all ATMs
            List<Atm> atms = ((AtmDataSource) ds).getAtmList();
            int index = 1;
            for (Atm atm : atms) {
                System.out.println("No " + index++ + ".");
                System.out.println(atm.getAddress().getShortAddress());
                System.out.println("Number of 10 dollars note left: " + atm.getNumOf10DollarsNotes());
                System.out.println("Number of 50 dollars note left: " + atm.getNumOf50DollarsNotes() + "\n");
            }
            System.out.println(line);

            int atmNo = in.nextInt();

            if (atmNo == 0) {
                System.out.println("Exit");
                System.exit(0); // Terminate program
            }

            // Index given exceeded the number of atms
            if (atmNo > atms.size()) {
                System.out.println(ScreenState.invalidInput);
                return null;
            }

            // Invalid input if negative value is given
            if (atmNo < 1) {
                System.out.println(ScreenState.invalidInput);
                return null;
            }

            return atms.get(atmNo - 1); // Minus 1 as index starts from 0

        } catch (NoSuchElementException e) { // Non integer value is given
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
