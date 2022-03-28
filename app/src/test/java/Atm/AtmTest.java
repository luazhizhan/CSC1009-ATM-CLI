package Atm;

import org.junit.jupiter.api.Test;

import Address.Address;
import DataSource.CountryDataSource;
import Helper.Tuple;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;

public class AtmTest {
    Atm atm;
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address("2021", "Bukit Batok Street 23", "02-178", "659526");
    }

    @Test
    public void success() {
        atm = new Atm();
        assertNotNull(atm.getId());
        assertNull(atm.getAddress());
        atm.setAddress(address);
        assertEquals(address, atm.getAddress());

        atm = new Atm("1234");
        assertEquals("1234", atm.getId());
        assertNull(atm.getAddress());

        try {
            CountryDataSource countryDS = new CountryDataSource();
            atm = new Atm("1234", countryDS.getDataById("SGP"));
            assertEquals("1234", atm.getId());
            assertNull(atm.getAddress());

            atm = new Atm("1234", countryDS.getDataById("SGP"), address);
            assertEquals("1234", atm.getId());
            assertEquals(address, atm.getAddress());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void successDeposit() {
        atm = new Atm();
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
        atm = new Atm();
        try {
            CountryDataSource countryDS = new CountryDataSource();
            atm = new Atm(countryDS.getDataById("SGP"), new int[] { 50, 3 });
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

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void failureDeposit() {
        Atm atm = new Atm();

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
        final Atm atm = new Atm();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> atm.withdraw(new BigDecimal(0)));
        assertEquals("Amount should be above 0.", exception.getMessage());

        exception = assertThrows(InsufficientNotesException.class,
                () -> atm.withdraw(new BigDecimal(100)));
        assertEquals("No notes left.", exception.getMessage());

        try {
            CountryDataSource countryDS = new CountryDataSource();
            final Atm atm1 = new Atm(countryDS.getDataById("SGP"), new int[] { 3, 2 });
            Tuple<BigDecimal, int[]> notes = atm1.withdraw(new BigDecimal(20));

            exception = assertThrows(IllegalArgumentException.class,
                    () -> atm1.withdraw(new BigDecimal(75)));
            assertEquals("Amount must be multiplier of 10.", exception.getMessage());

            exception = assertThrows(InsufficientNotesException.class,
                    () -> atm1.withdraw(new BigDecimal(150)));
            assertEquals("Insufficient notes.", exception.getMessage());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
