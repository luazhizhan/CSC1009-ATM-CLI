package Model.Atm;

import java.math.BigDecimal;

import Helper.Id;
import Helper.Tuple;
import Model.Address.Address;
import Model.Country.Country;
import Model.Currency.Currency;

/**
 * ATM
 */
public class Atm {
    private String id;
    private Address address;
    private Country country;
    private Currency currency;
    public MoneyHandler moneyHandler;

    public Atm(Country country, Currency currency) {
        setId(Id.generateUUID());
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency);
    }

    public Atm(String id, Country country, Currency currency) {
        setId(id);
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency);
    }

    public Atm(String id, Country country, Currency currency, Address address) {
        setId(id);
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency);
        setAddress(address);
    }

    public Atm(Country country, Currency currency, Address address) {
        setId(Id.generateUUID());
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency);
        setAddress(address);
    }

    public Atm(Country country, Currency currency, int[] amounts) {
        setId(Id.generateUUID());
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency, amounts);
    }

    public Atm(String id, Country country, Currency currency, int[] amounts) {
        setId(id);
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency, amounts);
    }

    public Atm(String id, Country country, Currency currency, int[] amounts, Address address) {
        setId(id);
        setCountry(country);
        setCurrency(currency);
        moneyHandler = new MoneyHandler(currency, amounts);
        setAddress(address);
    }

    public Atm(Country country, Currency currency, int[] amounts, Address address) {
        setId(Id.generateUUID());
        setCountry(country);
        setCurrency(currency);
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
     * Check amount argument against account's local withdraw limit
     * 
     * @param amount
     */
    public void checkAgainstATMWithdrawLimit(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(currency.getWithdrawMaximum())) > 0) {
            throw new IllegalArgumentException("Withdraw amount exceeded local ATM withdraw limit!");
        }
    }

    /**
     * Validate amount to be withdraw against number of 10 and 50 dollars notes
     * in the ATM.
     * Set new balance of 10 and 50 dollars notes in the ATM
     * 
     * @param amount Amount to withdraw
     * @return Tuple<Withdrawn amount, int[] of bills>
     */
    public Tuple<BigDecimal, int[]> withdraw(BigDecimal amount)
            throws IllegalArgumentException, InsufficientNotesException {
        return moneyHandler.withdraw(amount);
    }
}
