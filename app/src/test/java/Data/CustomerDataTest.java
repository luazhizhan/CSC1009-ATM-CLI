package Data;

import org.junit.jupiter.api.Test;

import Model.Customer.Customer;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerDataTest {
    @Test
    public void success() throws FileNotFoundException, IOException {
        Data<Customer> customerData = new CustomerData();
        Customer customer = customerData.getDataById("4099532");
        assertEquals(customer.getName(), "Tala Osborn");
        assertEquals(customer.getEmail(), "TalaOsborn@fakemal.com");
        assertEquals(customer.getPhone(), "62530167");
        assertEquals(customer.getAddress().getBlkNum(), "4");
        assertEquals(customer.getAddress().getStreetAddress(), "Delta Road");
        assertEquals(customer.getAddress().getUnitNumber(), "44-009");
        assertEquals(customer.getAddress().getPostalCode(), "100014");
        assertEquals(customer.getAddress().getCity(), "SINGAPORE");
        assertEquals(customer.getAddress().getState(), "SINGAPORE");
        assertEquals(customer.getAddress().getCountry(), "SINGAPORE");

    }
}
