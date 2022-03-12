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

    public Address()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter Your Blk Number: ");
        this.blkNum = input.next();
        System.out.println("Please enter your street address: ");
        this.streetAddress = input.next();
        System.out.println("Please enter your unit number (Leave Blank if not required): ");
        this.unitNumber = input.next();
        System.out.println("Please enter your postalcode: ");
        this.postalCode = input.next();
        System.out.println("Please enter your city: ");
        this.city = input.next();
        System.out.println("Please enter your state: ");
        this.state = input.next();
        System.out.println("Please enter your country: ");
        this.country = input.next();
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
        this.postalCode = postalCode;
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
