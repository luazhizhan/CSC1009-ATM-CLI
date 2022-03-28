package Account;

import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Helper.Id;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private String cardNumber; // Primary id
    private String name;
    private YearMonth expiryDate;
    private int cvv;
    private String accountId;
    private Card.CardStatus status;
    private int pinNumber;

    @BeforeEach
    public void setUp() {
        cardNumber = "4555914595512560";
        accountId = Id.generateUUID();
        name = "Ben";
        expiryDate = YearMonth.of(2025, 12);
        cvv = 123;
        status = Card.CardStatus.VALID;
        pinNumber = 123456;
    }

    @Test
    public void createSuccess() {
        // create card and check attributes
        Card card = new Card(cardNumber, name, expiryDate, accountId, status);
        card.setCvv(cvv);
        card.setPinNumber(pinNumber);
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(name, card.getName());
        assertEquals(expiryDate, card.getExpiryDate());
        assertEquals(accountId, card.getAccountId());
        assertEquals(status, card.getStatus());
        assertEquals(cvv, card.getCvv());
        assertTrue(card.checkPinNumber(pinNumber));

        cardNumber = "8285061751052789";
        accountId = Id.generateUUID();
        name = "Jerry";
        expiryDate = YearMonth.of(2025, 5);
        cvv = 321;
        status = Card.CardStatus.CANCELLED;
        pinNumber = 654321;

        card.setCardNumber(cardNumber);
        card.setAccountId(accountId);
        card.setName(name);
        card.setExpiryDate(expiryDate);
        card.setCvv(cvv);
        card.setStatus(status);
        card.setPinNumber(pinNumber);

        // Test if attributes were changed
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(name, card.getName());
        assertEquals(expiryDate, card.getExpiryDate());
        assertEquals(accountId, card.getAccountId());
        assertEquals(status, card.getStatus());
        assertEquals(cvv, card.getCvv());
        assertTrue(card.checkPinNumber(pinNumber));
    }

    @Test
    public void failureIllegalCardNumber() {
        cardNumber = "9999111122223333";
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Card(cardNumber, name, expiryDate, accountId, status).setCardNumber(cardNumber));
        assertEquals("Invalid Account Card Number Format!", exception.getMessage());
    }

    @Test
    public void failureIllegalExpiryDate() {
        expiryDate = YearMonth.of(2020, 12);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Card(cardNumber, name, expiryDate, accountId, status).setExpiryDate(expiryDate));
        assertEquals("Card has expired.", exception.getMessage());
    }

    @Test
    public void failureIllegalCVV() {
        cvv = 9099;
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Card(cardNumber, name, expiryDate, accountId, status).setCvv(cvv));
        assertEquals("Invalid cvv number!", exception.getMessage());
    }

    @Test
    public void failureMismatchPinNumber() {
        Card card = new Card(cardNumber, name, expiryDate, accountId, status);
        pinNumber = 111222;
        assertFalse(card.checkPinNumber(pinNumber));
    }

    @Test
    public void failureIllegalPinNumber() {
        pinNumber = 111;
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Card(cardNumber, name, expiryDate, accountId, status).setPinNumber(pinNumber));
        assertEquals("Invalid Pin Number format!", exception.getMessage());
    }
}
