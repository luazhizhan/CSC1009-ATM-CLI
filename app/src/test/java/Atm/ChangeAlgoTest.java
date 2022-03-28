package Atm;

import org.junit.jupiter.api.Test;

import Helper.ArraysEqual;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeAlgoTest {
    private int[] denominations;
    private int[] limits;
    private int value;

    @Test
    public void success() {
        denominations = new int[] { 2, 5, 10, 50, 100 };
        limits = new int[] { 40, 40, 40, 20, 40 };
        value = 5277;
        assertEquals(ArraysEqual.arraysEqual(ChangeAlgo.MakeChange(denominations, limits, value).y,
                new int[] { 1, 1, 27, 20, 40 }), true);
    }
}
