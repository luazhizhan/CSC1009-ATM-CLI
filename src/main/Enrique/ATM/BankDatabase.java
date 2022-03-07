package src.main.Enrique.ATM;

import java.util.ArrayList;

// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
    // idk what to use, but for right now, ArrayList
    public ArrayList<Account> accounts = new ArrayList<Account>() ; // array of Accounts

    
    public BankDatabase() {}
    

    // retrieve Account object containing specified account number
    public Account getAccountFromNumber(String accountNumber) {
        for (Account currentAccount : accounts) {
            if (currentAccount.getAccountNumber() == accountNumber)
                return currentAccount;
        }
        // No matching account found
        return null;
    }

    private Account getAccountFromPIN(int PIN) {
        // loop through accounts searching for matching account number
        for (Account currentAccount : accounts) {
            // return current account if match found
            if (currentAccount.getPIN() == PIN)
                return currentAccount;
        }

        // if no matching account was found, return null
        return null;
    }

    // determine whether user-specified account number and PIN match
    // those of an account in the database
    public boolean authenticateUser(int userPIN) {
        // attempt to retrieve the account with the account number
        Account userAccount = getAccountFromPIN(userPIN);

        // if account exists, return result of Account method validatePIN
        if (userAccount != null)
            return userAccount.validatePIN(userPIN);
        else
            // account number not found, so return false
            throw new NullPointerException("No such account found!");
    }

    // return available balance of Account with specified account number
    public double getAvailableBalance(String userAccountNumber) {
        // Don't know if I should/need to throw an exception, by this point the user 
        // should be verified and the account obtained.
        // Leaving this here as a marker so If I need to apply this to 
        // getTotalBalance/other methods that require accessing an account first,
        // But if we're dealing with money, redundancy is good to have?

        // try {
        //     Account userAccount = getAccountFromNumber(userAccountNumber);
        // } 
        // catch (Exception e) {
        //      //TODO: handle exception
                // System.out.println("Account not found, please contact your bank."); // some error message
        // }


        Account userAccount = getAccountFromNumber(userAccountNumber);
        
        if(userAccount == null) throw new NullPointerException("No account found!");
        
        return userAccount.getAvailableBalance();
    }

    // return total balance of Account with specified account number
    public double getTotalBalance(String userAccountNumber) {
        Account userAccount = getAccountFromNumber(userAccountNumber);
        
        if(userAccount == null) throw new NullPointerException("No account found!");

        return userAccount.getTotalBalance();
    }

    // return available balance of Account with specified account number
    public boolean validatePIN(String userAccountNumber, int PIN) {
        Account userAccount = getAccountFromNumber(userAccountNumber);
        
        if(userAccount == null) throw new NullPointerException("No account found!");

        return userAccount.validatePIN(PIN);
    }

    public void DeleteAccountByIndex(int position) {
        accounts.remove(position);
    }

}

