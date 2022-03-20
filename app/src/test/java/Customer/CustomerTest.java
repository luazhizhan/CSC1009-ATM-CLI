package Customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class CustomerTest {
    private String email;
    private String phone;
    private String name;
    private Address address;

    @BeforeEach
    public void setUp() {
        email = "joe@test.com";
        phone = "83212345";
        name = "Joe";
        address = new Address("2021", "Bukit Batok Street 23", "02-178", "659526");
    }

    @Test
    public void success() {
        Customer customer = new Customer(name, email, phone);
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());

        email = "tom@test.com";
        phone = "92351234";
        name = "tom";
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setName(name);
        customer.setAddress(address);

        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(address, customer.getAddress());
    }

    @Test
    public void successEmail() {
        Customer customer = new Customer(name, email, phone);
        email = "joe_deo@test.com.sg";
        customer.setEmail(email);
        assertEquals(email, customer.getEmail());

        email = "joe-123@test2.net";
        customer.setEmail(email);
        assertEquals(email, customer.getEmail());
    }

    @Test
    public void successPhone() {
        Customer customer = new Customer(name, email, phone);
        phone = "98324622";
        customer.setPhone(phone);
        assertEquals(phone, customer.getPhone());

        phone = "+6599993234";
        customer.setPhone(phone);
        assertEquals(phone, customer.getPhone());
    }

    @Test
    public void failureEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Customer(name, "john@test", phone));
        assertEquals("Invalid Email.", exception.getMessage());

        Customer customer = new Customer(name, email, phone);

        email = "abc@.com";
        exception = assertThrows(IllegalArgumentException.class,
                () -> customer.setEmail(email));
        assertEquals("Invalid Email.", exception.getMessage());
    }

    @Test
    public void failurePhone() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Customer(name, email, "74392124"));
        assertEquals("Invalid Phone Number.", exception.getMessage());

        Customer customer = new Customer(name, email, phone);

        phone = "8439212";
        exception = assertThrows(IllegalArgumentException.class,
                () -> customer.setPhone(phone));
        assertEquals("Invalid Phone Number.", exception.getMessage());

        phone = "+6484392123";
        exception = assertThrows(IllegalArgumentException.class,
                () -> customer.setPhone(phone));
        assertEquals("Invalid Phone Number.", exception.getMessage());
    }
}
