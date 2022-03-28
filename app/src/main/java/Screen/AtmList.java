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
        prompt = "\n" + line + "\nPlease select an ATM.\nEnter 0 to exit.\n";
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
                System.out.println(atm.getCountry().toString());
                System.out.println(atm.getAddress().getShortAddress());
                atm.PrintRemainingBills();
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

            return atms.get(atmNo - 1); // minus 1 as index starts from 0
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
