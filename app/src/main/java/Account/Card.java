import java.util.Date;

public class Card {
    private String cardNumber;
    private String name;
    private String expiryDate;
    private int ccv;
    private String accountId;
    private enum CardStatus{
        VALID,
        CANCELLED,
        EXPIRED
    }
    private int pinNumber;

    public Card(String name, String expiryDate, String accountId) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
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

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }
}
