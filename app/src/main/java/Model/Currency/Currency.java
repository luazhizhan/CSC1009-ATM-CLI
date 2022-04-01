package Model.Currency;

import java.util.HashSet;
import java.util.Set;

// Class for currencies. They can be used by a country, multiple countries (EU). 

// Contains the currency code (ISO 4217), 
// an array of banknote values in of that currencies latest series, <-- Presumed to be what ATMs are configured to accept and dispense.
public class Currency {

    // Identifier for the banknote series.
    private String currencyAcronym;

    // Notes used in an ATM for this currency.
    private int[] banknotes;

    // Cache exchange rates
    private Set<ExchangeRate> rates = new HashSet<>();

    // Suggested minimum withdraw amount for this currency.
    private int withdrawMinimum;

    // Suggested maximum withdraw amount for this currency.
    private int withdrawMaximum;

    // CONSTRUCTORS
    public Currency(String currencyAcronym) {
        this.setCurrencyAcronym(currencyAcronym);
    }

    public Currency(String currencyAcronym, int[] banknotes) {
        this.setCurrencyAcronym(currencyAcronym);
        this.setBanknotes(banknotes);
    }

    public String getCurrencyAcronym() {
        return currencyAcronym;
    }

    public void setCurrencyAcronym(String currencyAcronym) {
        this.currencyAcronym = currencyAcronym;
    }

    public Set<ExchangeRate> getRates() {
        return rates;
    }

    public ExchangeRate findExchangeRate(Currency currency) throws IllegalArgumentException {
        for (ExchangeRate rate : rates) {
            if (rate.getCurrency().equals(currency)) {
                return rate;
            }
        }
        throw new IllegalArgumentException("Currency not found: " + currency);
    }

    public void setExchangeRate(ExchangeRate rate) {
        if (rates.contains(rate))
            rates.remove(rate);
        rates.add(rate);
    }

    public boolean removeExchangeRate(ExchangeRate rate) {
        return rates.remove(rate);
    }

    public int[] getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(int[] banknotes) {
        this.banknotes = banknotes;
    }

    public int getWithdrawMinimum() {
        return withdrawMinimum;
    }

    public void setWithdrawMinimum(int withdrawMinimum) {
        this.withdrawMinimum = withdrawMinimum;
    }

    public int getWithdrawMaximum() {
        return withdrawMaximum;
    }

    public void setWithdrawMaximum(int withdrawMaximum) {
        this.withdrawMaximum = withdrawMaximum;
    }

}
