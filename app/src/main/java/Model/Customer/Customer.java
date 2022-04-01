package Model.Customer;

import Helper.Id;
import Model.Address.Address;

/**
 * Bank Customer
 */
public class Customer {

    private String id;
    private String name, email, phone;
    private Address address;

    public Customer(String id, String name, String email, String phone, Address address) {
        this.name = name;
        setEmail(email);
        setPhone(phone);
        setId(id);
        setAddress(address);
    }

    public Customer(String name, String email, String phone) {
        this.name = name;
        setEmail(email);
        setPhone(phone);
        setId(Id.generateUUID());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        if (email.matches(emailValid) == false)
            throw new IllegalArgumentException("Invalid Email.");
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        // +65 optional, first digit 8/9, 7 digits between 0 - 9
        String phoneValid = "(\\+?65)?(6|8|9)[0-9]{7}";
        if (phone.matches(phoneValid) == false)
            throw new IllegalArgumentException("Invalid Phone Number.");
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
