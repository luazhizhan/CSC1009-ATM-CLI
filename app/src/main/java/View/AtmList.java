package View;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Data.AtmData;
import Data.Data;
import Model.Atm.Atm;

/**
 * List avaliable ATM view class
 */
public class AtmList implements ViewState {
    private String prompt;

    public AtmList() {
        prompt = "\n" + line + "\nPlease select an ATM.\nEnter 0 to exit.\n";
    }

    @Override
    public void print(ViewStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * User selects ATM he/she like to use
     * 
     * @param in
     * @param ds
     * @return Atm object
     */
    public Atm selectAtm(Scanner in, Data<Atm> ds) {
        try {
            // Print all ATMs
            List<Atm> atms = ((AtmData) ds).getAtmList();
            int index = 1;
            for (Atm atm : atms) {
                System.out.println("No " + index++ + ".");
                System.out.println(atm.getCountry().getCountryName());
                System.out.println(atm.getAddress().getShortAddress());
                atm.printRemainingBills();
            }
            System.out.println(line);

            int atmNo = in.nextInt();

            if (atmNo == 0) {
                System.out.println("Exit");
                System.exit(0); // Terminate program
            }

            // Index given exceeded the number of atms
            if (atmNo > atms.size()) {
                System.out.println(ViewState.invalidInput);
                return null;
            }

            // Invalid input if negative value is given
            if (atmNo < 1) {
                System.out.println(ViewState.invalidInput);
                return null;
            }

            return atms.get(atmNo - 1); // Minus 1 as index starts from 0

        } catch (NoSuchElementException e) { // Non integer value is given
            System.out.println(ViewState.invalidInput);
            return null;
        }
    }
}
