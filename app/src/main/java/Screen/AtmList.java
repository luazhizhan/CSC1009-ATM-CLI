package Screen;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Atm.Atm;
import DataSource.AtmDataSource;
import DataSource.DataSource;

public class AtmList implements ScreenState {
    private String prompt;

    public AtmList() {
        prompt = "\n" + line + "\nPlease select an ATM.\nEnter 0 to exit.\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Atm selectAtm(Scanner in, DataSource<Atm> ds) {
        try {
            // Print all atms
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
                System.exit(0); // Terminal program
            }

            // Index given exceeded the number of atms
            if (atmNo > atms.size()) {
                System.out.println(ScreenState.invalidInput);
                return null;
            }

            if (atmNo < 1) {
                System.out.println(ScreenState.invalidInput);
                return null;
            }

            return atms.get(atmNo);
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
