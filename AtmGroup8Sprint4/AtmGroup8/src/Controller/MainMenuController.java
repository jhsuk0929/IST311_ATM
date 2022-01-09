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
import javafx.stage.Stage;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda, Yingjie Hong
 * Date: April 4th, 2021
 * Class: MainMenuController
 * Description: This class handles the functionality of the MainMenuUI.
 */
public class MainMenuController implements Initializable {

    private ArrayList<Customer> customers;
    private Customer activeUser;

    @FXML
    private Button checkBalanceButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button depositButton;
    @FXML
    private Button transferButton;
    @FXML
    private Button transactionHistoryButton;
    @FXML
    private Button cancelButton;

    // Handle all of the different buttons on the Main Menu
    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {
        System.out.println(((Button) event.getSource()).getText());

        // The button that was clicked
        String buttonClicked = ((Button) event.getSource()).getText();

        Stage stage;
        FXMLLoader loader;
        Parent root;
        Scene scene;
        
        // Determine next step based on the button selected
        switch (buttonClicked) {
            case "CHECK BALANCE":
                // If the user is successfully authenticated show the Main Menu.
                stage = (Stage) checkBalanceButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/CheckBalanceUI.fxml"));
                root = loader.load();

                // Let the MainMenu know about the logged in user
                CheckBalanceController checkBalanceController = (CheckBalanceController) loader.getController();
                checkBalanceController.setActiveUser(this.activeUser);
                checkBalanceController.setCustomers(customers);
                checkBalanceController.setBalancesToScreen();

                // Show the Check Balance UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "WITHDRAW":
                // If the user wants to withdraw show the WithdrawSelectionUI.
                stage = (Stage) checkBalanceButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/WithdrawSelectionUI.fxml"));
                root = loader.load();

                // Let the WithdrawController know about the logged in user
                WithdrawController withdrawSelectionController = (WithdrawController) loader.getController();
                withdrawSelectionController.setActiveUser(this.activeUser);
                withdrawSelectionController.setCustomers(customers);
                withdrawSelectionController.setButtonText();

                // Show the Withdraw UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "DEPOSIT":
                // If the user wants to withdraw show the WithdrawSelectionUI.
                stage = (Stage) checkBalanceButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/DepositSelectionUI.fxml"));
                root = loader.load();

                // Let the WithdrawController know about the logged in user
                DepositController depositController = (DepositController) loader.getController();
                depositController.setActiveUser(this.activeUser);
                depositController.setCustomers(customers);
                depositController.setButtonText();

                // Show the Withdraw UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "TRANSFER":
                break;
            case "TRANSACTION HISTORY":
                break;
            case "SIGN OUT":
                // If the user is finished sign out and show WelcomeUI
                stage = (Stage) cancelButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/WelcomeUI.fxml"));
                root = loader.load();
                
                WelcomeController welcomeController = (WelcomeController) loader.getController();
                welcomeController.setCustomers(getCustomers());

                // Show the Welcome UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            default:
                break;
        }
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
