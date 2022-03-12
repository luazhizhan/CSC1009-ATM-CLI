package Customer;

import java.util.LinkedList;
import java.util.UUID;

public class Customer {

    private UUID CustomerID;
    private String Name,Email,Phone;
    private Address address;


    public Customer(String Name, String Email, String Phone)
    {
        this.CustomerID= UUID.randomUUID();
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.address = new Address();
    }

    public UUID getCustomerID() {
        return CustomerID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

}
