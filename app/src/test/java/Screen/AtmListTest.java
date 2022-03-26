package Screen;

import org.junit.jupiter.api.Test;

import Atm.Atm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class AtmListTest {
    @Test
    public void success() {
        ScreenState atmList = new AtmList();
        ScreenStateContext stateContext = new ScreenStateContext();
        stateContext.setAndPrintScreen(atmList);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        Atm atm = ((AtmList) atmList).selectAtm(in);
        assertEquals(300, atm.getNumOf10DollarsNotes());
        assertEquals(300, atm.getNumOf50DollarsNotes());
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
        ((AtmList) atmList).selectAtm(in);
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
        ((AtmList) atmList).selectAtm(in);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("0.4".getBytes()));
        in = new Scanner(System.in);
        ((AtmList) atmList).selectAtm(in);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }
}
