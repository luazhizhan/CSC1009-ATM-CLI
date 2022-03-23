package Atm;

import org.junit.jupiter.api.Test;

import Address.Address;
import Helper.Pair;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;

public class AtmTest {
    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address("2021", "Bukit Batok Street 23", "02-178", "659526");
    }

    @Test
    public void success() {
        Atm atm = new Atm();
        assertNotNull(atm.getId());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());
        assertNull(atm.getAddress());
        atm.setAddress(address);
        assertEquals(address, atm.getAddress());

        atm = new Atm("1234");
        assertEquals("1234", atm.getId());
        assertNull(atm.getAddress());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());

        atm = new Atm(address);
        assertNotNull(atm.getId());
        assertEquals(address, atm.getAddress());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());

        atm = new Atm("1234", address);
        assertEquals("1234", atm.getId());
        assertEquals(address, atm.getAddress());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());
    }

    @Test
    public void successDeposit() {
        Atm atm = new Atm();
        BigDecimal total = atm.deposit(10, 10);
        int tenDollarsNotes = Atm.DEFAULT_NUM_10_NOTES + 10;
        int fiftyDollarsNotes = Atm.DEFAULT_NUM_50_NOTES + 10;
        assertEquals(tenDollarsNotes, atm.getNumOf10DollarsNotes());
        assertEquals(fiftyDollarsNotes, atm.getNumOf50DollarsNotes());
        assertEquals(total, new BigDecimal(600));

        total = atm.deposit(0, 5);
        fiftyDollarsNotes += 5;
        assertEquals(tenDollarsNotes, atm.getNumOf10DollarsNotes());
        assertEquals(fiftyDollarsNotes, atm.getNumOf50DollarsNotes());
        assertEquals(total, new BigDecimal(250));

        total = atm.deposit(2, 0);
        tenDollarsNotes += 2;
        assertEquals(tenDollarsNotes, atm.getNumOf10DollarsNotes());
        assertEquals(fiftyDollarsNotes, atm.getNumOf50DollarsNotes());
        assertEquals(total, new BigDecimal(20));
    }

    @Test
    public void successWithdraw() throws InsufficientNotesException {
        Atm atm = new Atm();
        Pair<Integer> notes = atm.withdraw(new BigDecimal(20));
        assertEquals(2, notes.first());
        assertEquals(0, notes.second());

        int tenDollarsNotes = Atm.DEFAULT_NUM_10_NOTES - 2;
        int fiftyDollarsNotes = Atm.DEFAULT_NUM_50_NOTES;
        assertEquals(tenDollarsNotes, atm.getNumOf10DollarsNotes());
        assertEquals(fiftyDollarsNotes, atm.getNumOf50DollarsNotes());

        notes = atm.withdraw(new BigDecimal(50));
        assertEquals(0, notes.first());
        assertEquals(1, notes.second());
        fiftyDollarsNotes--;
        assertEquals(tenDollarsNotes, atm.getNumOf10DollarsNotes());
        assertEquals(fiftyDollarsNotes, atm.getNumOf50DollarsNotes());

        notes = atm.withdraw(new BigDecimal(90));
        assertEquals(4, notes.first());
        assertEquals(1, notes.second());
        tenDollarsNotes -= 4;
        fiftyDollarsNotes--;
        assertEquals(tenDollarsNotes, atm.getNumOf10DollarsNotes());
        assertEquals(fiftyDollarsNotes, atm.getNumOf50DollarsNotes());

        // Insufficent 50 dollars notes case
        atm.setNumOf10DollarsNotes(10);
        atm.setNumOf50DollarsNotes(1);
        notes = atm.withdraw(new BigDecimal(130));
        assertEquals(8, notes.first());
        assertEquals(1, notes.second());
        assertEquals(2, atm.getNumOf10DollarsNotes());
        assertEquals(0, atm.getNumOf50DollarsNotes());
    }

    @Test
    public void failureDeposit() {
        Atm atm = new Atm();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> atm.deposit(-1, 10));
        assertEquals("Number of notes should be >= 0.", exception.getMessage());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());

        exception = assertThrows(IllegalArgumentException.class,
                () -> atm.deposit(0, -2));
        assertEquals("Number of notes should be >= 0.", exception.getMessage());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());

        exception = assertThrows(IllegalArgumentException.class,
                () -> atm.deposit(0, 0));
        assertEquals("Please deposit at least one note.", exception.getMessage());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());
    }

    @Test
    public void failureWithdraw() {
        Atm atm = new Atm();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> atm.withdraw(new BigDecimal(0)));
        assertEquals("Amount should be above 0.", exception.getMessage());
        assertEquals(Atm.DEFAULT_NUM_10_NOTES, atm.getNumOf10DollarsNotes());
        assertEquals(Atm.DEFAULT_NUM_50_NOTES, atm.getNumOf50DollarsNotes());

        atm.setNumOf10DollarsNotes(0);
        atm.setNumOf50DollarsNotes(0);
        exception = assertThrows(InsufficientNotesException.class,
                () -> atm.withdraw(new BigDecimal(100)));
        assertEquals("No notes left.", exception.getMessage());
        assertEquals(0, atm.getNumOf10DollarsNotes());
        assertEquals(0, atm.getNumOf50DollarsNotes());

        // Set total notes amount to $130
        atm.setNumOf10DollarsNotes(3);
        atm.setNumOf50DollarsNotes(2);

        exception = assertThrows(IllegalArgumentException.class,
                () -> atm.withdraw(new BigDecimal(75)));
        assertEquals("Amount must be multiplier of 10.", exception.getMessage());
        assertEquals(3, atm.getNumOf10DollarsNotes());
        assertEquals(2, atm.getNumOf50DollarsNotes());

        exception = assertThrows(InsufficientNotesException.class,
                () -> atm.withdraw(new BigDecimal(150)));
        assertEquals("Insufficient notes.", exception.getMessage());
        assertEquals(3, atm.getNumOf10DollarsNotes());
        assertEquals(2, atm.getNumOf50DollarsNotes());
    }
}
