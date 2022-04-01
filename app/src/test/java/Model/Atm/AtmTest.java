package Model.Atm;

import org.junit.jupiter.api.Test;

import Data.CountryData;
import Data.CurrencyData;
import Data.Data;
import Helper.Tuple;
import Model.Address.Address;
import Model.Country.Country;
import Model.Currency.Currency;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;

public class AtmTest {
        Atm atm;
        private Address address;
        private Data<Country> countryDataSource = null;
        private Data<Currency> currencyDataSource = null;
        private Country singapore;
        private Currency sgd;
        private int[] amounts;

        @BeforeEach
        public void setUp() throws FileNotFoundException, IOException {
                address = new Address("2021", "Bukit Batok Street 23", "02-178", "659526");
                countryDataSource = new CountryData();
                currencyDataSource = new CurrencyData();
                singapore = countryDataSource.getDataById("SGP");
                sgd = currencyDataSource.getDataById("SGD");
                amounts = new int[] { 50, 3 };
        }

        @Test
        public void success() {
                atm = new Atm(singapore, sgd);
                assertNotNull(atm.getCountry());
                assertNotNull(atm.getId());
                assertNull(atm.getAddress());
                atm.setAddress(address);
                assertEquals(address, atm.getAddress());

                atm = new Atm("1234", singapore, sgd);
                assertNotNull(atm.getCountry());
                assertEquals("1234", atm.getId());
                assertNull(atm.getAddress());

                atm = new Atm("12345", singapore, sgd, address);
                assertNotNull(atm.getCountry());
                assertEquals("12345", atm.getId());
                assertEquals(address, atm.getAddress());

                atm = new Atm("12345", singapore, sgd, address);
                assertNotNull(atm.getCountry());
                assertEquals("12345", atm.getId());
                assertEquals(address, atm.getAddress());

                atm = new Atm(singapore, sgd, amounts);
                assertNotNull(atm.getCountry());
                assertNotNull(atm.getId());
                assertNull(atm.getAddress());
                atm.setAddress(address);
                assertEquals(address, atm.getAddress());

                atm = new Atm("1234", singapore, sgd, amounts);
                assertNotNull(atm.getCountry());
                assertEquals("1234", atm.getId());
                assertNull(atm.getAddress());

                atm = new Atm("12345", singapore, sgd, amounts, address);
                assertNotNull(atm.getCountry());
                assertEquals("12345", atm.getId());
                assertEquals(address, atm.getAddress());

                atm = new Atm("12345", singapore, sgd, amounts, address);
                assertNotNull(atm.getCountry());
                assertEquals("12345", atm.getId());
                assertEquals(address, atm.getAddress());
        }

        @Test
        public void successDeposit() {
                atm = new Atm(singapore, sgd);
                int[] depositAmounts = new int[] { 10, 10 };
                BigDecimal total = atm.deposit(depositAmounts);
                assertEquals(total, new BigDecimal(600));

                depositAmounts[0] = 0;
                depositAmounts[1] = 5;
                total = atm.deposit(depositAmounts);
                assertEquals(total, new BigDecimal(250));

                depositAmounts[0] = 2;
                depositAmounts[1] = 0;
                total = atm.deposit(depositAmounts);
                assertEquals(total, new BigDecimal(20));
        }

        @Test
        public void successWithdraw() throws InsufficientNotesException {
                atm = new Atm(singapore, sgd);
                atm = new Atm(singapore, sgd, amounts);
                Tuple<BigDecimal, int[]> notes = atm.withdraw(new BigDecimal(20));
                assertEquals(2, notes.y[0]);
                assertEquals(0, notes.y[1]);

                notes = atm.withdraw(new BigDecimal(50));
                assertEquals(0, notes.y[0]);
                assertEquals(1, notes.y[1]);

                notes = atm.withdraw(new BigDecimal(90));
                assertEquals(4, notes.y[0]);
                assertEquals(1, notes.y[1]);

                // Insufficent 50 dollars notes case
                notes = atm.withdraw(new BigDecimal(130));
                assertEquals(8, notes.y[0]);
                assertEquals(1, notes.y[1]);
        }

        @Test
        public void failureDeposit() {
                atm = new Atm(singapore, sgd);

                Exception exception = assertThrows(IllegalArgumentException.class,
                                () -> atm.deposit(new int[] { -1, 10 }));
                assertEquals("Number of notes should be >= 0.", exception.getMessage());

                exception = assertThrows(IllegalArgumentException.class,
                                () -> atm.deposit(new int[] { 0, -2 }));
                assertEquals("Number of notes should be >= 0.", exception.getMessage());

                exception = assertThrows(IllegalArgumentException.class,
                                () -> atm.deposit(new int[] { 0, 0 }));
                assertEquals("Please deposit at least one note.", exception.getMessage());
        }

        @Test
        public void failureWithdraw() {
                atm = new Atm(singapore, sgd, new int[] { 3, 0 });

                Exception exception = assertThrows(IllegalArgumentException.class,
                                () -> atm.withdraw(new BigDecimal(0)));
                assertEquals("Amount is too low, minimum withdraw limit is 20 SGD.", exception.getMessage());

                exception = assertThrows(IllegalArgumentException.class,
                                () -> atm.withdraw(new BigDecimal(10000)));
                assertEquals("Amount is too high, maximum withdraw limit is 500 SGD.", exception.getMessage());

                exception = assertThrows(InsufficientNotesException.class,
                                () -> atm.withdraw(new BigDecimal(100)));
                assertEquals("Insufficient notes remaining in ATM to dispense this amount.", exception.getMessage());

                atm = new Atm(singapore, sgd, new int[] { 3000, 3000 });

                exception = assertThrows(IllegalArgumentException.class,
                                () -> atm.withdraw(new BigDecimal(1000)));
                assertEquals("Amount is too high, maximum withdraw limit is 500 SGD.", exception.getMessage());

                atm = new Atm(singapore, sgd, new int[] { 3, 2 });

                exception = assertThrows(InsufficientNotesException.class,
                                () -> atm.withdraw(new BigDecimal(75)));
                assertEquals("Insufficient notes remaining in ATM to dispense this amount.", exception.getMessage());

                exception = assertThrows(InsufficientNotesException.class,
                                () -> atm.withdraw(new BigDecimal(150)));
                assertEquals("Insufficient notes remaining in ATM to dispense this amount.", exception.getMessage());

        }

}
