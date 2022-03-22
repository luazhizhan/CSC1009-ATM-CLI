package Screen;

import org.junit.jupiter.api.Test;

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
        atmList.printScreen(stateContext);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        String atm = ((AtmList) atmList).selectAtm(in); // TODO change to ATM object
        assertEquals("ATM", atm);
        in.close();

        // TODO test system exit?
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
        assertEquals(outContent.toString().trim()
                .compareTo("Invalid input! Please try again."), 0);
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
        assertEquals(outContent.toString().trim()
                .compareTo("Invalid input! Please try again."), 0);

        System.setIn(new ByteArrayInputStream("0.4".getBytes()));
        assertEquals(outContent.toString().trim()
                .compareTo("Invalid input! Please try again."), 0);
        in.close();
    }
}
