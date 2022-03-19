package Customer;

import java.util.LinkedList;
import java.util.UUID;

public class Customer {

    private UUID customerID;
    private String name,email,phone;
    private Address address;


    public Customer(String name, String email, String phone)
    {
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

    public void setEmail(String email)
    {
        String emailValid = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        if (email.matches(emailValid))
        {
            this.email = email;

        }
        else
        {
            throw new IllegalArgumentException("Invalid Email");
        }

    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        String phoneValid = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
        if(phone.matches(phoneValid))
        {
            this.phone = phone;
        }
        else
        {
            throw new IllegalArgumentException("Invalid Phone Number");
        }
    }

}
