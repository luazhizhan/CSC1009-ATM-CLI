package View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class MainOptionTest {

    @Test
    public void success() {
        ViewState mainOption = new MainOption();
        ViewStateContext stateContext = new ViewStateContext();
        stateContext.setAndPrintScreen(mainOption);

        // Set scanner input value
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Scanner in = new Scanner(System.in);
        int selectedOption = ((MainOption) mainOption).getSelectedOption(in);
        assertEquals(1, selectedOption);
        in.close();
    }

    @Test
    public void failureNoSuchOption() {
        ViewState mainOption = new MainOption();

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
        ViewState mainOption = new MainOption();

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
