package Account;

import java.time.YearMonth;
import java.util.Date;
import java.util.regex.*;

public class Card {
    private String cardNumber;
    private String name;
    private YearMonth expiryDate;
    private int cvv;
    private String accountId;
    private CardStatus status;
    private enum CardStatus{
        VALID,
        CANCELLED,
        EXPIRED
    }
    private int pinNumber;

    public Card(String name, YearMonth expiryDate, String accountId, CardStatus status) {
        this.name = name;
        setExpiryDate(expiryDate);
        this.accountId = accountId;
        this.status = status;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {

        if(validateCardNumber(cardNumber)){
            this.cardNumber = cardNumber;
        }
        else{
            System.out.println("Invalid Account.Card Number Format!");
        }
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
        YearMonth currentDate = YearMonth.now();
        if (expiryDate.isBefore(currentDate)){
            System.out.println("Your Account.Card have expired!");
        }
        else
        {
            this.expiryDate = expiryDate;
        }
    }

    public int getCvv() {
        return cvv;
    }

    public void setCcv(int cvv) {
        if(validateCVV(String.valueOf(cvv))){
        this.cvv = cvv;
        }
        else{
            System.out.println("Invalid cvv number!");
        }
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
        if(validatePin(String.valueOf(pinNumber))){
            this.pinNumber = pinNumber;
        }else
        {
            System.out.println("Invalid Pin Number format!");
        }

    }
    private boolean validatePin(String pinNo){
        // Regex to check valid pin
        String regex = "^[0-9]{6}$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the pin code is empty
        // return false
        if (pinNo == null) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given pin code
        // and regular expression.
        Matcher m = p.matcher(pinNo);

        // Return if the pin code
        // matched the ReGex
        return m.matches();

    }
    private boolean validateCardNumber(String cardNo){
        // Regex to check valid Account.Card No
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14}))$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the Account.Card No is empty
        // return false
        if (cardNo == null) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given Account.Card No
        // and regular expression.
        Matcher m = p.matcher(cardNo);

        // Return if the Account.Card No
        // matched the ReGex
        return m.matches();
    }
    private boolean validateCVV(String cvv){
        // Regex to check valid pin
        String regex = "^[0-9]{3}$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the pin code is empty
        // return false
        if (cvv == null) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given pin code
        // and regular expression.
        Matcher m = p.matcher(cvv);

        // Return if the pin code
        // matched the ReGex
        return m.matches();

    }
}
