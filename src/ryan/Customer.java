// package src.ryan;


// import java.util.LinkedList;

// public class Customer {
//     enum Status
//     {
//         Normal,
//         Privilleged
//     }
//     private int CustomerID;
//     static private int IDTracker=0;
//     private String Name,Email,Phone;
//     private Enum<Status> CustomerStatus;
//     //private List<Account> AccountList;

//     public Customer(String Name, String Email, String Phone)
//     {
//         this.CustomerID= IDTracker;
//         IDTracker+=1;
//         this.Name = Name;
//         this.Email = Email;
//         this.Phone = Phone;
//         this.CustomerStatus = Status.Normal;
//         //this.AccountList = new LinkedList<Account>();
//     }

//     public int getCustomerID() {
//         return CustomerID;
//     }

//     public String getName() {
//         return Name;
//     }

//     public void setName(String name) {
//         Name = name;
//     }

//     public String getEmail() {
//         return Email;
//     }

//     public void setEmail(String email) {
//         Email = email;
//     }

//     public String getPhone() {
//         return Phone;
//     }

//     public void setPhone(String phone) {
//         Phone = phone;
//     }

//     public Enum<Status> getCustomerStatus() {
//         return CustomerStatus;
//     }

//     public void setCustomerStatus(Enum<Status> customerStatus) {
//         CustomerStatus = customerStatus;
//     }
// }
