/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda, Yingjie Hong
 * Date: April 4th, 2021
 * Class: Customer
 * Description: This is a model class that will extend the User model. This model is for users who can perform monetary transaction on the ATM.
 */
public class Customer extends User {
    
    private String accountNumber;
    private String password;
    private ArrayList<Account> accounts;
    
    // Constructor for a Customer object
    public Customer(String firstName, String lastName, String userName, String address, String phoneNumber, String email, String accountNumber, String password) {
        super(firstName, lastName, userName, address, phoneNumber, email);
        
        this.accountNumber = accountNumber;
        this.password = password;
        this.accounts = new ArrayList<Account>();
    }
    
    // Will check if credentials are vaild for a particular User.
    @Override
    public boolean authenticate(String inputAccountNumber, String inputPassword) {
        
        if (this.accountNumber.equals(inputAccountNumber) && this.getPassword().equals(inputPassword)) {
            return true;
        } else {
            return false;
        }
        
    }
    
    // Find User's Account
    public Account findAccount(String type) {
        Account foundAccount = null;
        
        for (Account acct : this.accounts) {
            if (acct.getACCOUNT_TYPE().equals(type)) {
                foundAccount = acct;
            }
        }
        
        return foundAccount;
    }
    
    // Add a new account to the Customer
    public void addAccount(Account accountToAdd) {
        this.accounts.add(accountToAdd);
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
