package Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Atm.Atm;
import Country.Country;
import DataSource.AtmDataSource;
import DataSource.CountryDataSource;
import DataSource.DataSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class AtmListTest {
    private DataSource<Atm> atmDataSource;
    private DataSource<Country> countryDataSource;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        countryDataSource = new CountryDataSource();
        atmDataSource = new AtmDataSource((CountryDataSource) countryDataSource);
    }

    @Test
    public void success() {
        ScreenState atmList = new AtmList();
        ScreenStateContext stateContext = new ScreenStateContext();
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
        ScreenState atmList = new AtmList();

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
        ScreenState atmList = new AtmList();

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
