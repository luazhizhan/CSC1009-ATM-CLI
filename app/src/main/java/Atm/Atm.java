package Atm;

import java.math.BigDecimal;

import Address.Address;
import Country.Country;
import DataSource.CurrencyDataSource;
import Helper.Id;
import Helper.Tuple;

public class Atm {
    private String id;
    private Address address;
    private Country country;

    MoneyHandler moneyHandler;

    // For test
    public Atm() {
        setId(Id.generateUUID());
        try {
            moneyHandler = new MoneyHandler(new CurrencyDataSource().getDataById("SGP"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // For test
    public Atm(String id) {
        setId(id);
        try {
            moneyHandler = new MoneyHandler(new CurrencyDataSource().getDataById("SGP"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // For test
    public Atm(Country country, int[] amounts) {
        setId(Id.generateUUID());
        setCountry(country);
        try {
            moneyHandler = new MoneyHandler(new CurrencyDataSource().getDataById(country.getCurrencyAcronym()),
                    amounts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // For test
    public Atm(String id, Country country) {
        setId(id);
        setCountry(country);
        try {
            moneyHandler = new MoneyHandler(new CurrencyDataSource().getDataById(country.getCurrencyAcronym()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Atm(String id, Country country, Address address) {
        setId(id);
        setCountry(country);
        try {
            moneyHandler = new MoneyHandler(new CurrencyDataSource().getDataById(country.getCurrencyAcronym()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    public BigDecimal deposit(int[] depositAmounts) {
        Tuple<Boolean, BigDecimal> result = moneyHandler.deposit(depositAmounts);
        try {
            if (result.x == false) {
                throw new InsufficientNotesException("Deposit failed!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result.y;
    }

    /**
     * 
     * @param amount
     * @return Pair<Integer>(numOf10DollarsNotes, numOf50DollarsNotes)
     * @throws IllegalArgumentException
     * @throws InsufficientNotesException
     */
    public Tuple<BigDecimal, int[]> withdraw(BigDecimal amount)
            throws IllegalArgumentException, InsufficientNotesException {

        Tuple<BigDecimal, int[]> result = moneyHandler.withdraw(amount.intValue());

        if (result.x == BigDecimal.ZERO) {
            throw new InsufficientNotesException("ATM has insufficient funds!");
        }

        return result;
    }
}
