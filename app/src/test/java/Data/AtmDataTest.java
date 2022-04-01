package Data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Atm.Atm;
import Model.Country.Country;
import Model.Currency.Currency;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AtmDataTest {
    Atm atm;
    private Data<Country> countryDataSource = null;
    private Data<Currency> currencyDataSource = null;

    @BeforeEach
    public void setUp() {
        try {
            countryDataSource = new CountryData();
            currencyDataSource = new CurrencyData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void success() throws FileNotFoundException, IOException {
        Data<Atm> atmDataSource = new AtmData((CountryData) countryDataSource,
                (CurrencyData) currencyDataSource);

        atm = atmDataSource.getDataById("6a8145ec3be544879331c0c592e510b6");
        assertEquals(atm.getAddress().getBlkNum(), "4");
        assertEquals(atm.getAddress().getStreetAddress(), "Delta Road");
        assertEquals(atm.getAddress().getPostalCode(), "443738");
        assertEquals(atm.getAddress().getCity(), "SINGAPORE");
        assertEquals(atm.getAddress().getState(), "SINGAPORE");
        assertEquals(atm.getAddress().getCountry(), "SINGAPORE");

        atm = atmDataSource.getDataById("27c215af35dd4c7dbe704b77360e4888");
        assertEquals(atm.getAddress().getBlkNum(), "3");
        assertEquals(atm.getAddress().getStreetAddress(), "Charlie Walk");
        assertEquals(atm.getAddress().getPostalCode(), "389728");
        assertEquals(atm.getAddress().getCity(), "SINGAPORE");
        assertEquals(atm.getAddress().getState(), "SINGAPORE");
        assertEquals(atm.getAddress().getCountry(), "SINGAPORE");
    }
}
