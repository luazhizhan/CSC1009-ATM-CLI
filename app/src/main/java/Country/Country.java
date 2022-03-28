package Country;

// Class for countries.

// Contains the currency code (ISO 3166), 
// an array of banknote values in of that currencies latest series, <-- Presumed to be what ATMs are configured to accept and dispense.
public class Country {

    // CONSTRUCTORS
    public Country(String countryAcronym) {
        this.setCurrencyAcronym(countryAcronym);
    }

    public Country(String countryAcronym, String currencyAcronym) {
        this.setCountryAcronym(countryAcronym);
        this.setCurrencyAcronym(currencyAcronym);
    }

    public Country(String countryAcronym, String currencyAcronym, String countryName) {
        this.setCountryAcronym(countryAcronym);
        this.setCurrencyAcronym(currencyAcronym);
        this.setCountryName(countryName);
    }

    // Identifier for the country.
    private String countryAcronym;

    public String getCountryAcronym() {
        return countryAcronym;
    }

    public void setCountryAcronym(String countryAcronym) {
        this.countryAcronym = countryAcronym;
    }

    // Identifier for a countries currency
    private String currencyAcronym;

    public String getCurrencyAcronym() {
        return currencyAcronym;
    }

    public void setCurrencyAcronym(String currencyAcronym) {
        this.currencyAcronym = currencyAcronym;
    }

    // Identifier for a countries currency
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
