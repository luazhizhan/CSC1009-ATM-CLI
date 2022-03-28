package Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Atm.Atm;
import DataSource.AtmDataSource;
import DataSource.DataSource;

import javax.xml.crypto.Data;


public class AtmList implements ScreenState {
    private String prompt;
    private List<Atm> atmList= new AtmDataSource().getAtmData();;
    public AtmList() throws IOException {


            prompt = "\n" + line + "\nPlease select an ATM.\nEnter 0 to exit.\n";
            for(int i = 0; i< atmList.size();i++) {

                prompt =prompt + (i+1)
                        + "  Atm at:  " + atmList.get(i).getAddress().getBlkNum()
                        + ", " + atmList.get(i).getAddress().getStreetAddress()
                        + ",  " + atmList.get(i).getAddress().getCity()
                        + " " + atmList.get(i).getAddress().getPostalCode()+ "\n";
            }

    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    public Atm selectAtm(Scanner in) {
        try {
            int atmNo = in.nextInt();
            if (atmNo == 0) {
                System.out.println("Exit");
                System.exit(0); // Terminal program
            }

            if (atmNo < 1) {
                System.out.println(ScreenState.invalidInput);
                return null;
            }

            return new Atm();
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return null;
        }
    }
}
