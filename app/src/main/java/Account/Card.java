package Account;

import java.time.YearMonth;

public class Card {
    private String cardNumber;
    private String name;
    private YearMonth expiryDate;
    private int cvv;
    private String accountId;
    private CardStatus status;
    private int pinNumber;

    public enum CardStatus {
        VALID,
        CANCELLED,
        EXPIRED
    }

    public Card(String cardNumber, String name, YearMonth expiryDate,
            String accountId, CardStatus status) {
        setCardNumber(cardNumber);
        setName(name);
        setExpiryDate(expiryDate);
        setAccountId(accountId);
        setStatus(status);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (validateCardNumber(cardNumber) == false) {
            throw new IllegalArgumentException("Invalid Account Card Number Format!");
        }
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(YearMonth expiryDate) {
        if (expiryDate.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("Invalid Account Card Number Format!");
        }
        this.expiryDate = expiryDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        if (validateCVV(String.valueOf(cvv)) == false) {
            throw new IllegalArgumentException("Invalid cvv number!");
        }
        this.cvv = cvv;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public void setPinNumber(int pinNumber) {
        if (validatePin(String.valueOf(pinNumber)) == false) {
            throw new IllegalArgumentException("Invalid Pin Number format!");
        }
        this.pinNumber = pinNumber;
    }

    private boolean validatePin(String pinNo) {
        // Return false if the pin code is empty
        if (pinNo == null) {
            return false;
        }

        // Regex to check valid pin
        String regex = "^[0-9]{6}$";

        // Return if the pin code
        // matched the ReGex
        return regex.matches(pinNo);

    }

    private boolean validateCardNumber(String cardNo) {
        // Return false if the Account.Card No is empty
        if (cardNo == null) {
            return false;
        }

        // Regex to check valid Account.Card No
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14}))$";

        // Return if the Account.Card No
        // matched the ReGex
        return regex.matches(cardNo);
    }

    private boolean validateCVV(String cvv) {
        // If the pin code is empty
        // return false
        if (cvv == null) {
            return false;
        }

        // Regex to check valid pin
        String regex = "^[0-9]{3}$";

        // Return if the pin code
        // matched the ReGex
        return regex.matches(regex);

    }
}
