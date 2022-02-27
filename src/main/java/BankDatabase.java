package src.main.java;

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

    private Account getAccountFromPIN(int PIN)
    {
        // loop through accounts searching for matching account number
        for (Account currentAccount : accounts)
        {
            // return current account if match found
            if (currentAccount.getPIN() == PIN)
            return currentAccount;
        } // end for

        return null; // if no matching account was found, return null
    }

    // determine whether user-specified account number and PIN match
    // those of an account in the database
    public boolean authenticateUser(int userPIN)
    {
        // attempt to retrieve the account with the account number
        Account userAccount = getAccountFromPIN(userPIN);

        // if account exists, return result of Account method validatePIN
        if (userAccount != null)
            return userAccount.validatePIN(userPIN);
        else
            return false; // account number not found, so return false
    }

    // return available balance of Account with specified account number
    public double getAvailableBalance(String userAccountNumber)
    {
        return getAccountFromNumber(userAccountNumber).getAvailableBalance();
    } // end method getAvailableBalance

    // return total balance of Account with specified account number
    public double getTotalBalance(String userAccountNumber)
    {
        return getAccountFromNumber(userAccountNumber).getTotalBalance();
    }


    public void DeleteAccountByIndex(int position) {
        accounts.remove(position);
    }

}

