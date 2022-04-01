package DataSource;

import org.junit.jupiter.api.Test;

import Currency.Currency;
import Currency.ExchangeRate;
import Helper.ArraysEqual;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Set;

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
        // assertEquals(currencyCAD.getBanknotes(), new int[] { 20, 50, 100 });
        assertEquals(ArraysEqual.arraysEqual(currencyCAD.getBanknotes(), new int[] { 20, 50, 100 }), true);

        Currency currencyJPY = currencyDataSource.getDataById("JPY");
        assertEquals(currencyJPY.getCurrencyAcronym(), "JPY");
        assertEquals(currencyJPY.getWithdrawMinimum(), 1000);
        assertEquals(currencyJPY.getWithdrawMaximum(), 100000);
        assertEquals(ArraysEqual.arraysEqual(currencyJPY.getBanknotes(), new int[] { 1000, 5000, 10000 }), true);
        assertEquals(currencyJPY.findExchangeRate(currencyUSD).getRate().doubleValue(), 0.008137038);
        assertEquals(currencyJPY.findExchangeRate(currencyCAD).getRate().doubleValue(), 0.0101677871818068);

        // Check if all currencies have rates to all other currencies
        for (int i = 0; i < currencyDataSource.getData().size(); i++) {

            Currency currencyA = currencyDataSource.getData().get(i);
            Set<ExchangeRate> currencyARates = currencyDataSource.getData().get(i).getRates();

            // Check against all other currencies in the datasource
            for (int j = 0; j < currencyDataSource.getData().size(); j++) {
                Currency currencyB = currencyDataSource.getData().get(i);
                if (currencyA == currencyB)
                    continue;

                // That currency A does not have a rate to currency B
                Iterator<ExchangeRate> currAIterator = currencyARates.iterator();
                boolean flag = false;
                while (currAIterator.hasNext()) {
                    // Get an exchange rate.
                    ExchangeRate currARate = currAIterator.next();
                    // Currency A has a rate to a currency that
                    if (currARate.getCurrency() == currencyB) {
                        flag = true;
                    }
                }
                if (flag == false) {
                    throw new MissingResourceException("Missing " + currencyA.getCurrencyAcronym() +
                            " to " + currencyB.getCurrencyAcronym() + "!",
                            "Currency", currencyB.getCurrencyAcronym());
                } else
                    flag = false;
            }
        }
    }
}
