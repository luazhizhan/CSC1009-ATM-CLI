package DataSource;

import org.junit.jupiter.api.Test;

import Currency.Currency;

import Helper.ArraysEqual;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDataSourceTest {
    @Test
    public void success() throws FileNotFoundException, IOException {
        DataSource<Currency> currencyDataSource = new CurrencyDataSource();
        Currency currencyUSD = currencyDataSource.getDataById("USD");
        assertEquals(currencyUSD.getCurrencyAcronym(), "USD");
        assertEquals(currencyUSD.getWithdrawMinimum(), 20);
        assertEquals(currencyUSD.getWithdrawMaximum(), 1000);
        assertEquals(ArraysEqual.arraysEqual(currencyUSD.getBanknotes(), new int[] { 1, 5, 10, 20, 50, 100 }), true);

        Currency currencyCAD = currencyDataSource.getDataById("CAD");
        assertEquals(currencyCAD.getCurrencyAcronym(), "CAD");
        assertEquals(currencyCAD.getWithdrawMinimum(), 20);
        assertEquals(currencyCAD.getWithdrawMaximum(), 500);
        assertEquals(currencyCAD.getBanknotes(), new int[] { 20, 50, 100 });
        assertEquals(ArraysEqual.arraysEqual(currencyCAD.getBanknotes(), new int[] { 20, 50, 100 }), true);

        Currency currencyJPY = currencyDataSource.getDataById("JPY");
        assertEquals(currencyJPY.getCurrencyAcronym(), "JPY");
        assertEquals(currencyJPY.getWithdrawMinimum(), 1000);
        assertEquals(currencyJPY.getWithdrawMaximum(), 100000);
        assertEquals(ArraysEqual.arraysEqual(currencyJPY.getBanknotes(), new int[] { 1000, 5000, 10000 }), true);
        assertEquals(currencyJPY.findExchangeRate(currencyUSD).getRate().doubleValue(), 0.008137038);
        assertEquals(currencyJPY.findExchangeRate(currencyCAD).getRate().doubleValue(), 0.0101677871818068);
    }
}
