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
 * Class: SavingsAccount
 * Description: This is a model class that will extend the Account model. This model is for functionality specific to savings accounts.
 */
public class SavingsAccount extends Account {
    
    public SavingsAccount(int typeIndex, double startingBalance, double interestRate) {
        super(typeIndex, startingBalance, interestRate);
    }
    
    
}
