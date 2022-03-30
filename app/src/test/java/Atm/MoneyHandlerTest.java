package Atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Country.Country;
import Currency.Currency;
import DataSource.CountryDataSource;
import DataSource.CurrencyDataSource;
import DataSource.DataSource;
import Helper.ArraysEqual;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class MoneyHandlerTest {
    private int value;
    Atm atm;
    private DataSource<Country> countryDataSource = null;
    private DataSource<Currency> currencyDataSource = null;
    private Country singapore;
    private Currency sgd;

    @BeforeEach
    public void setUp() {
        try {
            countryDataSource = new CountryDataSource();
            currencyDataSource = new CurrencyDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        singapore = countryDataSource.getDataById("SGP");
        sgd = currencyDataSource.getDataById("SGD");
    }

    @Test
    public void success() {
        int[] startingBills = new int[] { 5, 3 };
        atm = new Atm(singapore, sgd, startingBills);
        value = 170;
        int[] expectedWithdrawnBills = new int[] { 2, 3 };
        try {
            assertEquals(ArraysEqual.arraysEqual(atm.withdraw(new BigDecimal(value)).y, expectedWithdrawnBills), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
