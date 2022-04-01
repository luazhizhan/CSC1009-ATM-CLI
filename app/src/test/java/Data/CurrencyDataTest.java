package Data;

import org.junit.jupiter.api.Test;

import Model.Currency.Currency;
import Model.Currency.ExchangeRate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDataTest {

    @Test
    public void success() throws FileNotFoundException, IOException {
        Data<Currency> currencyData = new CurrencyData();
        Currency currencyUSD = currencyData.getDataById("USD");
        assertEquals(currencyUSD.getCurrencyAcronym(), "USD");
        assertEquals(currencyUSD.getWithdrawMinimum(), 20);
        assertEquals(currencyUSD.getWithdrawMaximum(), 1000);
        assertEquals(arraysEqual(currencyUSD.getBanknotes(), new int[] { 1, 5, 10, 20, 50, 100 }), true);

        Currency currencyCAD = currencyData.getDataById("CAD");
        assertEquals(currencyCAD.getCurrencyAcronym(), "CAD");
        assertEquals(currencyCAD.getWithdrawMinimum(), 20);
        assertEquals(currencyCAD.getWithdrawMaximum(), 500);
        // assertEquals(currencyCAD.getBanknotes(), new int[] { 20, 50, 100 });
        assertEquals(arraysEqual(currencyCAD.getBanknotes(), new int[] { 20, 50, 100 }), true);

        Currency currencyJPY = currencyData.getDataById("JPY");
        assertEquals(currencyJPY.getCurrencyAcronym(), "JPY");
        assertEquals(currencyJPY.getWithdrawMinimum(), 1000);
        assertEquals(currencyJPY.getWithdrawMaximum(), 100000);
        assertEquals(arraysEqual(currencyJPY.getBanknotes(), new int[] { 1000, 5000, 10000 }), true);
        assertEquals(currencyJPY.findExchangeRate(currencyUSD).getRate().doubleValue(), 0.008137038);
        assertEquals(currencyJPY.findExchangeRate(currencyCAD).getRate().doubleValue(), 0.0101677871818068);

        // Check if all currencies have rates to all other currencies
        for (int i = 0; i < currencyData.getData().size(); i++) {

            Currency currencyA = currencyData.getData().get(i);
            Set<ExchangeRate> currencyARates = currencyData.getData().get(i).getRates();

            // Check against all other currencies in the data
            for (int j = 0; j < currencyData.getData().size(); j++) {
                Currency currencyB = currencyData.getData().get(i);
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

    public boolean arraysEqual(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length)
            return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }
}
