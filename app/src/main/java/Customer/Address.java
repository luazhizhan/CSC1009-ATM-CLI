package Customer;

import java.util.Scanner;

public class Address {
    private String blkNum;
    private String streetAddress;
    private String unitNumber;
    private String postalCode;
    private String city;
    private String state;
    private String country;

    protected Address(String blkNum, String streetAddress, String unitNumber, String postalCode, String city, String state, String country)
    {
        this.blkNum = blkNum;
        this.streetAddress = streetAddress;
        this.unitNumber = unitNumber;
        setPostalCode(postalCode);
        this.city = city;
        this.state =state;
        this.country = country;
    }

    String getBlkNum() {
        return blkNum;
    }

    void setBlkNum(String blkNum) {
        this.blkNum = blkNum;
    }

    String getStreetAddress() {
        return streetAddress;
    }

    void setStreetAddress(String streetAddress) {

        this.streetAddress = streetAddress;
    }

    String getUnitNumber() {
        return unitNumber;
    }

    void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    String getPostalCode() {
        return postalCode;
    }

    void setPostalCode(String postalCode) {
        String phoneValid = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
        if(postalCode.matches(phoneValid))
        {
            this.postalCode = postalCode;
        }
        else
        {
            throw new IllegalArgumentException("Invalid Postal Code");
        }

    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    String getAddress()
    {
        return(blkNum+" " +streetAddress+" "+unitNumber+" "+city+" "+state+" "+country);
    }

}
