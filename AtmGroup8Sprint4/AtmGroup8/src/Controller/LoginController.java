/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda,
 * Yingjie Hong 
 * Date: April 4th, 2021 
 * Class: LoginController Description: This
 * class handles the functionality of the LoginUI.
 */
public class LoginController implements Initializable {

    private ArrayList<Customer> customers;
    private Customer activeUser;

    Stage stage;
    FXMLLoader loader;
    Parent root;

    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField accountNumberField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label invaildLoginLabel;

    // If the User clicks Submit, authenticate the credentials.
    @FXML
    private void handleSubmitButton(ActionEvent event) throws IOException {
        System.out.println("Login Submit Button Clicked");
        System.out.println("Account #: " + accountNumberField.getText() + "   Password: " + passwordField.getText());

        // Set the user's inputs
        String inputAccountNumber = accountNumberField.getText();
        String inputPassword = passwordField.getText();

        // Clear any previous users
        setActiveUser(null);

        // Try to set the active based on the user's inputs
        try {
            setActiveUser(this.handleCredentials(inputAccountNumber, inputPassword));
        } catch (Exception e) {
            System.err.println("WARN: No valid User was returned!");
        }

        // If we have an authenticated User show the Main Menu
        if (getActiveUser() != null) {
            // If the user is successfully authenticated show the Main Menu.
            stage = (Stage) submitButton.getScene().getWindow();
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

        } else {
            // Show/Warn of failed login
            System.out.println("++WARN: NO USER FOUND");
            invaildLoginLabel.setOpacity(1);
        }

    }

    // Return to the WelcomeUI if the user's presses Cancel
    @FXML
    private void handleCancelButton(ActionEvent event) throws IOException {
        System.out.println("Login Cancel Button Clicked");

        // If the user clicks Cancel, Show the WelcomeUI.
        stage = (Stage) cancelButton.getScene().getWindow();
        loader = new FXMLLoader(getClass().getResource("/View/WelcomeUI.fxml"));
        root = loader.load();
        
        WelcomeController welcomeController = (WelcomeController) loader.getController();
        welcomeController.setCustomers(customers);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Verify if the login information is vaild based on our customer list
    private Customer handleCredentials(String inputAccountNumber, String inputPassword) {
        Customer authenticatedUser = null;
        for (int i = 0; i < getCustomers().size(); i++) {
            if (getCustomers().get(i).authenticate(inputAccountNumber, inputPassword)) {
                authenticatedUser = getCustomers().get(i);
                System.out.println("User was authenticated: " + getCustomers().get(i).getUserName());
                break;
            }
        }

        // Returns an authenticated User or a null value
        return authenticatedUser;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Grab a copy of the known users that can be authenticated
//        setCustomerList(new CustomerList());
//        setCustomers(getCustomerList().getCustomers());

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

}
