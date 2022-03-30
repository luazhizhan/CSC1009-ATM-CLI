package Atm;

import java.math.BigDecimal;

import Helper.Id;
import Helper.Tuple;
import Address.Address;
import Country.Country;
import Currency.Currency;

public class Atm {
    private String id;
    private Address address;
    private Country country;
    public MoneyHandler moneyHandler;

    public Atm(Country country, Currency currency) {
        setId(Id.generateUUID());
        setCountry(country);
        moneyHandler = new MoneyHandler(currency);
    }

    public Atm(String id, Country country, Currency currency) {
        setId(id);
        setCountry(country);
        moneyHandler = new MoneyHandler(currency);
    }

    public Atm(String id, Country country, Currency currency, Address address) {
        setId(id);
        setCountry(country);
        moneyHandler = new MoneyHandler(currency);
        setAddress(address);
    }

    public Atm(Country country, Currency currency, Address address) {
        setId(Id.generateUUID());
        setCountry(country);
        moneyHandler = new MoneyHandler(currency);
        setAddress(address);
    }

    public Atm(Country country, Currency currency, int[] amounts) {
        setId(Id.generateUUID());
        setCountry(country);
        moneyHandler = new MoneyHandler(currency, amounts);
    }

    public Atm(String id, Country country, Currency currency, int[] amounts) {
        setId(id);
        setCountry(country);
        moneyHandler = new MoneyHandler(currency, amounts);
    }

    public Atm(String id, Country country, Currency currency, int[] amounts, Address address) {
        setId(id);
        setCountry(country);
        moneyHandler = new MoneyHandler(currency, amounts);
        setAddress(address);
    }

    public Atm(Country country, Currency currency, int[] amounts, Address address) {
        setId(Id.generateUUID());
        setCountry(country);
        moneyHandler = new MoneyHandler(currency, amounts);
        setAddress(address);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void printRemainingBills() {
        moneyHandler.printRemainingBills();
    }

    // Only used for tests
    public int[] getBills() {
        return moneyHandler.getBills();
    }

    public BigDecimal deposit(int[] depositAmounts) {
        Tuple<Boolean, BigDecimal> result = moneyHandler.deposit(depositAmounts);
        return result.y;
    }

    /**
     * 
     * @param amount
     * @return Pair<Integer>(numOf10DollarsNotes, numOf50DollarsNotes)
     */
    public Tuple<BigDecimal, int[]> withdraw(BigDecimal amount)
            throws IllegalArgumentException, InsufficientNotesException {
        return moneyHandler.withdraw(amount.intValue());
    }
}
