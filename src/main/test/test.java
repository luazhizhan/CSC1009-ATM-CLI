package src.main.test;


import src.main.java.*;


public class test {
    
    public static void main(String[] args) {
        
        BankDatabase bankDB = new BankDatabase();
        
        //The original array has been changed into an arraylist, this makes it easier to add/delete from the database
        Account accounts1 = new Account("John");
        Account accounts2 = new Account("Bubinga Oae");
        Account accounts3 = new Account("Zagreus");
        Account accounts4 = new Account("Lim Bo Teck");
        bankDB.accounts.add(accounts1);
        bankDB.accounts.add(accounts2);
        bankDB.accounts.add(accounts3);
        bankDB.accounts.add(accounts4);

        ATM myATM = new ATM("SINGAPORE");
        
        
        // int amount = 868;
        // Dummy, only works bc dispenser was temporarily public
        // myATM.dispenser.dispenseMoney(amount);
    }
}
