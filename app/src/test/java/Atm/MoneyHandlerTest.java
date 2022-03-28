package Atm;

import org.junit.jupiter.api.Test;

import Helper.ArraysEqual;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class MoneyHandlerTest {
    private int[] denominations;
    private int[] limits;
    private int value;
    Atm atm;
    private MoneyHandler moneyHandler;

    @Test
    public void success() {
        atm = new Atm();
        denominations = new int[] { 2, 5, 10, 50, 100 };
        limits = new int[] { 40, 40, 40, 20, 40 };
        value = 5277;

        try {
            assertEquals(ArraysEqual.arraysEqual(atm.withdraw(new BigDecimal(value)).y,
                    new int[] { 1, 1, 27, 20, 40 }), true);
        } catch (Exception e) {

        }
    }
}
