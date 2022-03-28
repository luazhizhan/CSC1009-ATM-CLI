package Address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class AddressTest {

    private String blkNum;
    private String streetAddress;
    private String unitNumber;
    private String postalCode;
    private static final String SINGAPORE = "Singapore";

    @BeforeEach
    public void setUp() {
        blkNum = "2021";
        streetAddress = "Bukit Batok Street 23";
        unitNumber = "02-178";
        postalCode = "659526";
    }

    @Test
    public void success() {
        Address address = new Address(blkNum, streetAddress, unitNumber, postalCode);
        assertEquals(blkNum, address.getBlkNum());
        assertEquals(streetAddress, address.getStreetAddress());
        assertEquals(unitNumber, address.getUnitNumber());
        assertEquals(postalCode, address.getPostalCode());
        assertEquals(SINGAPORE, address.getCity());
        assertEquals(SINGAPORE, address.getState());
        assertEquals(SINGAPORE, address.getCountry());
        assertEquals("2021 Bukit Batok Street 23, 02-178, Singapore, Singapore, Singapore", address.getAddress());

        blkNum = "10";
        streetAddress = "Glasgow Terrace,9B Glasgow Road";
        unitNumber = "01-525";
        postalCode = "110010";
        String city = "City Square";
        String state = "Johor Bahru";
        String country = "Malaysia";
        address.setBlkNum(blkNum);
        address.setStreetAddress(streetAddress);
        address.setUnitNumber(unitNumber);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setState(state);
        address.setCountry(country);

        assertEquals(blkNum, address.getBlkNum());
        assertEquals(streetAddress, address.getStreetAddress());
        assertEquals(unitNumber, address.getUnitNumber());
        assertEquals(postalCode, address.getPostalCode());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
        assertEquals(country, address.getCountry());
        assertEquals("10 Glasgow Terrace,9B Glasgow Road, 01-525, City Square, Johor Bahru, Malaysia",
                address.getAddress());
    }

    @Test
    public void failurePostalCode() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Address(blkNum, streetAddress, unitNumber, "54221"));
        assertEquals("Invalid Postal Code.", exception.getMessage());
    }
}
