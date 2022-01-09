/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Account;
import Model.CheckingAccount;
import Model.Customer;
import Model.SavingsAccount;
import java.util.ArrayList;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda, Yingjie Hong
 * Date: April 4th, 2021
 * Class: CustomerList
 * Description: This is a class that will create test users for our ATM.
 */
public class CustomerList {
    
    private ArrayList<Customer> customers; // Array of test Users
    
    // Constructor for a CustomerList object
    public CustomerList() {
        customers = new ArrayList<Customer>();
        
        // Create the test Users
        Customer customer1 = new Customer("Bob", "Smith", "BSmith", "123 Main St, State College, PA", "610-555-1234", "BSmith@psu.edu", "11111", "password1");
        Customer customer2 = new Customer("Tim", "Johnson", "TJohnson", "123 Main St, State College, PA", "610-555-1234", "TJohnson@psu.edu", "22222", "password2");
        Customer customer3 = new Customer("Sarah", "Williams", "SWilliams", "123 Main St, State College, PA", "610-555-1234", "SWilliams@psu.edu", "33333", "password3");
        Customer customer4 = new Customer("Jill", "Jones", "JJones", "123 Main St, State College, PA", "610-555-1234", "JJones@psu.edu", "44444", "password4");
        Customer customer5 = new Customer("John", "Chambers", "JChambers", "123 Main St, State College, PA", "610-555-1234", "JChambers@psu.edu", "55555", "password5");
        Customer customer6 = new Customer("Test", "User", "testUser", "123 Main St, State College, PA", "610-555-1234", "testUser@psu.edu", "testUser", "testPass");
        
        // Add test Users to the array
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        customers.add(customer6);
        
        // Add accounts to each Customer
        double balance = 100.00;
        double rate = 0.05;
        for (Customer customer : customers) {
            customer.addAccount(new SavingsAccount(0, balance, rate));
            balance += 50.00; // Use different balances
            customer.addAccount(new CheckingAccount(1, balance, rate));
        }
        
    }

    /**
     * @return the customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
}
