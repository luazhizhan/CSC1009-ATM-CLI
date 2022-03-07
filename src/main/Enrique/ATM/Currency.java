package src.main.Enrique.ATM;

import java.util.HashSet;
import java.util.Set;



// Checked with Prof after class 07/03/22, can use .csv files to import exchange rates or currency data.

// Class for currencies. They can be used by a country, multiple countries (EU). 

// Contains the currency code (ISO 4217), 
// an array of banknote values in of that currencies latest series, <-- Presumed to be what ATMs are configured to accept and dispense.
// and an array of countries/country that uses that currency.

// Leaving the property setters means they are mutable, so only permit valid users, if we get that far.
public class Currency {

    // CONSTRUCTORS
    public Currency(String currencyAcronym) {
        this.setCurrencyAcronym(currencyAcronym);
    }
    
    public Currency(String currencyAcronym, int[] banknotes, String[] countries) {
        this.setCurrencyAcronym(currencyAcronym);
        this.setBanknotes(banknotes);
        this.setCountries(countries);
    }


    // We will use this as an identifier for the banknote series.
    private String currencyAcronym;
    public String getCurrencyAcronym() {
        return currencyAcronym;
    }
    public void setCurrencyAcronym(String currencyAcronym) {
        this.currencyAcronym = currencyAcronym;
    }
    
    // Can I cache exchange rates in here? Turns out yes
    // Ripped this exchange rate bit from SO, but its p handy and sensible.
    private Set<ExchangeRate> rates = new HashSet<>();
    
    public ExchangeRate findExchangeRate(Currency currency) {
        for(ExchangeRate rate: rates) {
            if ( rate.getCurrency().equals(currency)) {
                return rate;
            }
        }
        throw new IllegalArgumentException("Currency not found: " + currency);
    }
    public void setExchangeRate(ExchangeRate rate) {
        if (rates.contains(rate)) rates.remove(rate);
        rates.add(rate);
    }
    public boolean removeExchangeRate(ExchangeRate rate) {
        return rates.remove(rate);
    }

    // Using only the latest series of banknotes for each countries.
    private int[] banknotes;
    public int[] getBanknotes() {
        return banknotes;
    }
    public void setBanknotes(int[] banknotes) {
        this.banknotes = banknotes;
    }

    // Should use country acronyms
    private String[] countries;
    public String[] getCountries() {
        return countries;
    }
    public void setCountries(String[] countries) {
        this.countries = countries;
    }


}
