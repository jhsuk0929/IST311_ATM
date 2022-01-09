/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.CheckingAccount;
import Model.SavingsAccount;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda,
 * Yingjie Hong 
 * Date: April 4th, 2021 
 * Class: CheckBalanceController Description:
 * This class handles the functionality of the CheckBalanceUI.
 */
public class CheckBalanceController implements Initializable {

    private Customer activeUser;
    private ArrayList<Customer> customers;
    private SavingsAccount userSavingsAccount;
    private CheckingAccount userCheckingAccount;

    Stage stage;
    FXMLLoader loader;
    Parent root;

    @FXML
    private Text savingsBalanceText;
    @FXML
    private Text checkingBalanceText;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button signoutButton;

    // Return User back to the Main Menu for another transaction
    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        stage = (Stage) mainMenuButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/View/MainMenuUI.fxml"));
        root = loader.load();

        // Let the MainMenu know about the logged in user
        MainMenuController mainMenuController = (MainMenuController) loader.getController();
        mainMenuController.setActiveUser(activeUser);
        mainMenuController.setCustomers(customers);

        // Show the Main Menu UI
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Log out user and display the Welcome screen
    @FXML
    private void handleSignOutButton(ActionEvent event) throws IOException {
        stage = (Stage) signoutButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/View/WelcomeUI.fxml"));
        root = loader.load();
        
        WelcomeController welcomeController = (WelcomeController) loader.getController();
        welcomeController.setCustomers(customers);

        // Show the Welcome UI
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Get the user's balances and display them on screen.
    public void setBalancesToScreen() {
        userSavingsAccount = (SavingsAccount) this.activeUser.getAccounts().get(0);
        userCheckingAccount = (CheckingAccount) this.activeUser.getAccounts().get(1);
        System.out.println("Balance Controller User: " + userSavingsAccount.getBalance() + " : " + userCheckingAccount.getBalance());

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        savingsBalanceText.setText(formatter.format(userSavingsAccount.getBalance()));
        checkingBalanceText.setText(formatter.format(userCheckingAccount.getBalance()));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * @return the activeUser
     */
    public Customer getActiveUser() {
        return activeUser;
    }

    /**
     * @param activeUser the activeUser to set
     */
    public void setActiveUser(Customer activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * @return the customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

}
