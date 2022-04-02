package Model.Country;

// Class for countries.

// Contains the currency code (ISO 3166), 
// an array of banknote values in of that currencies latest series, <-- Presumed to be what ATMs are configured to accept and dispense.
public class Country {

    // Identifier for the country.
    private final String countryAcronym;
    // Identifier for a countries currency
    private final String currencyAcronym;
    // Country name
    private final String countryName;

    // CONSTRUCTOR
    public Country(String countryAcronym, String currencyAcronym, String countryName) {
        this.countryAcronym = countryAcronym;
        this.currencyAcronym = currencyAcronym;
        this.countryName = countryName;
    }

    public String getCountryAcronym() {
        return countryAcronym;
    }

    public String getCurrencyAcronym() {
        return currencyAcronym;
    }

    public String getCountryName() {
        return countryName;
    }
}
