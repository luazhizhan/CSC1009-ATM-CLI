package Account;

import java.time.YearMonth;

public class Card {
    private String cardNumber; // Primary id
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

    public Card() {

    }

    public Card(String cardNumber, String name, String accountId, CardStatus status) {
        setCardNumber(cardNumber);
        setName(name);
        setAccountId(accountId);
        setStatus(status);
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
        validateCardNumber(cardNumber);
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
            throw new IllegalArgumentException("Card has expired.");
        }
        this.expiryDate = expiryDate;
    }

    /**
     * This method is for DataSource class only!!
     * 
     * @param expiryDate
     */
    public void setExpiryDateWithoutCheck(YearMonth expiryDate) {
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

    public boolean checkPinNumber(int pinNumber) {
        return this.pinNumber == pinNumber;
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
        return pinNo.matches(regex);

    }

    /**
     * Validate credit/debit card using Luhn algorithm
     * https://www.geeksforgeeks.org/luhn-algorithm/
     * 
     * @param cardNo credit/debit card number without space
     * @return boolean
     */
    public static void validateCardNumber(String cardNo) {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNo.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        if (nSum % 10 != 0) {
            throw new IllegalArgumentException("Invalid Account Card Number Format!");
        }
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
        return cvv.matches(regex);

    }
}
