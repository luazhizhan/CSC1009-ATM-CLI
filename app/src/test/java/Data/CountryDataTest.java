package Data;

import org.junit.jupiter.api.Test;

import Model.Country.Country;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CountryDataTest {
    @Test
    public void success() throws FileNotFoundException, IOException {
        Data<Country> countryDataSource = new CountryData();

        Country singapore = countryDataSource.getDataById("SGP");
        assertEquals(singapore.getCountryName(), "Singapore");
        assertEquals(singapore.getCurrencyAcronym(), "SGD");

        Country usa = countryDataSource.getDataById("USA");
        assertEquals(usa.getCountryName(), "United States");
        assertEquals(usa.getCurrencyAcronym(), "USD");

        Country uk = countryDataSource.getDataById("GBR");
        assertEquals(uk.getCountryName(), "United Kingdom");
        assertEquals(uk.getCurrencyAcronym(), "GBP");

        Country australia = countryDataSource.getDataById("AUS");
        assertEquals(australia.getCountryName(), "Australia");
        assertEquals(australia.getCurrencyAcronym(), "AUD");

        Country mali = countryDataSource.getDataById("MLI");
        assertEquals(mali.getCountryName(), "Mali");
        assertEquals(mali.getCurrencyAcronym(), "XOF");
    }
}
