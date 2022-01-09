/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda, Yingjie Hong
 * Date: April 4th, 2021
 * Class: Account
 * Description: This is the superclass for other Account Model objects, like SavingsAccount and CheckingAccount.
 */
public abstract class Account {
    
    private final String[] ACCOUNT_TYPES = new String[] {"SAVINGS", "CHECKING"};
    private final String ACCOUNT_TYPE;
    private double balance;
    private double interestRate;
//    private ArrayList<Transaction> transactions;
    
    public Account(int typeIndex, double startingBalance, double interestRate) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPES[typeIndex];
        this.balance = startingBalance;
        this.interestRate = interestRate;
    }
    
    // Add to the balance
    
    // Remove from the balance

    /**
     * @return the ACCOUNT_TYPES
     */
    public String[] getACCOUNT_TYPES() {
        return ACCOUNT_TYPES;
    }

    /**
     * @return the ACCOUNT_TYPE
     */
    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
}
