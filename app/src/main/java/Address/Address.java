package Address;

public class Address {
    private String blkNum;
    private String streetAddress;
    private String unitNumber;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private static final String SINGAPORE = "Singapore";

    public Address(String blkNum, String streetAddress, String unitNumber,
            String postalCode) {
        setBlkNum(blkNum);
        setStreetAddress(streetAddress);
        setUnitNumber(unitNumber);
        setPostalCode(postalCode);
        setCity(SINGAPORE);
        setState(SINGAPORE);
        setCountry(SINGAPORE);
    }

    public Address(String blkNum, String streetAddress, String unitNumber,
            String postalCode, String city, String state, String country) {
        setBlkNum(blkNum);
        setStreetAddress(streetAddress);
        setUnitNumber(unitNumber);
        setPostalCode(postalCode);
        setCity(city);
        setState(state);
        setCountry(country);
    }

    public String getBlkNum() {
        return blkNum;
    }

    public void setBlkNum(String blkNum) {
        this.blkNum = blkNum;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {

        this.streetAddress = streetAddress;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        String phoneValid = "\\d{6}";
        if (postalCode.matches(phoneValid)) {
            this.postalCode = postalCode;
        } else {
            throw new IllegalArgumentException("Invalid Postal Code.");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShortAddress() {
        return (blkNum + " " + streetAddress + " " + unitNumber + " S" + postalCode);
    }

    public String getAddress() {
        return (blkNum + " " + streetAddress + ", " + unitNumber + ", "
                + city + ", " + state + ", " + country);
    }

}
