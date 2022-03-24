package Screen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MainOptionTest {

    @Test
    public void success() {
        ScreenState mainOption = new MainOption();
        ScreenStateContext stateContext = new ScreenStateContext();
        mainOption.printScreen(stateContext);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        int selectedOption = ((MainOption) mainOption).getSelectedOption(in);
        assertEquals(1, selectedOption);
        in.close();
    }

    @Test
    public void failureNoSuchOption() {
        ScreenState mainOption = new MainOption();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("-77".getBytes()));
        Scanner in = new Scanner(System.in);
        int selectedOption = ((MainOption) mainOption).getSelectedOption(in);
        assertEquals(-1, selectedOption);
        assertTrue(outContent.toString().contains("No such option available! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("8".getBytes()));
        in = new Scanner(System.in);
        selectedOption = ((MainOption) mainOption).getSelectedOption(in);
        assertEquals(-1, selectedOption);
        assertTrue(outContent.toString().contains("No such option available! Please try again."));
        in.close();
    }

    @Test
    public void failureInvalidInput() {
        ScreenState mainOption = new MainOption();

        // Set and read System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("abc".getBytes()));
        Scanner in = new Scanner(System.in);
        int selectedOption = ((MainOption) mainOption).getSelectedOption(in);
        assertEquals(-1, selectedOption);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();

        System.setIn(new ByteArrayInputStream("five".getBytes()));
        in = new Scanner(System.in);
        selectedOption = ((MainOption) mainOption).getSelectedOption(in);
        assertEquals(-1, selectedOption);
        assertTrue(outContent.toString().contains("Invalid input! Please try again."));
        in.close();
    }
}
