package View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Data.AtmData;
import Data.CountryData;
import Data.CurrencyData;
import Data.Data;
import Model.Atm.Atm;
import Model.Country.Country;
import Model.Currency.Currency;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class AtmListTest {
    private Data<Atm> atmDataSource;
    private Data<Country> countryDataSource = null;
    private Data<Currency> currencyDataSource = null;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        countryDataSource = new CountryData();
        currencyDataSource = new CurrencyData();
        atmDataSource = new AtmData((CountryData) countryDataSource,
                (CurrencyData) currencyDataSource);
    }

    @Test
    public void success() {
        ViewState atmList = new AtmList();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrintScreen(atmList);

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        Atm atm = ((AtmList) atmList).selectAtm(in, atmDataSource);
        String out = outContent.toString();
        assertEquals(300, atm.getBills()[0]);
        assertEquals(300, atm.getBills()[1]);
        assertTrue(out.contains("Alpha Street"));
        assertTrue(out.contains("Bravo Way"));
        assertTrue(out.contains("Charlie Walk"));
        in.close();
    }

    @Test
    public void failureNegativeATMNo() {
        ViewState atmList = new AtmList();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("-1".getBytes()));

        Scanner in = new Scanner(System.in);
        ((AtmList) atmList).selectAtm(in, atmDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }

    @Test
    public void failureNonIntegerInput() {
        ViewState atmList = new AtmList();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("test".getBytes()));

        Scanner in = new Scanner(System.in);
        ((AtmList) atmList).selectAtm(in, atmDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("0.4".getBytes()));
        in = new Scanner(System.in);
        ((AtmList) atmList).selectAtm(in, atmDataSource);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }
}
