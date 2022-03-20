package Customer;

import java.util.UUID;

public class Customer {

    private UUID customerID;
    private String name, email, phone;
    private Address address;

    public Customer(String name, String email, String phone) {
        this.name = name;
        setEmail(email);
        setPhone(phone);
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // https://www.baeldung.com/java-email-validation-regex#strict-regular-expression-validation
        String emailValid = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (email.matches(emailValid)) {
            this.email = email;

        } else {
            throw new IllegalArgumentException("Invalid Email.");
        }

    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        // +65 optional, first digit 8/9, 7 digits between 0 - 9
        String phoneValid = "(\\+?65)?(8|9)[0-9]{7}";
        if (phone.matches(phoneValid)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid Phone Number.");
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
