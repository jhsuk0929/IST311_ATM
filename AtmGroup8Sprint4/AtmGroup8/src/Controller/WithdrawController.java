/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CheckingAccount;
import Model.Customer;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda, Yingjie Hong
 * Date: April 18th, 2021
 * Class: WithdrawController
 * Description: This class handles the functionality of a user performing a withdraw.
 */
public class WithdrawController implements Initializable {

    // Global Controller Variables
    private Stage stage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private MainMenuController mainMenuController;
    private WithdrawController withdrawController;

    private Customer activeUser;
    private ArrayList<Customer> customers;
    private String accountSelected = null;
    private SavingsAccount savingsAccount = null;
    private CheckingAccount checkingAccount = null;

    // UI Elements
    @FXML
    private Button savingsButton;
    @FXML
    private Button checkingButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button cancelAmountButton;
    @FXML
    private Button signOutButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private TextField amountTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label balanceLabel;

    // Handle all of the buttons on the Withdraw screens
    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {
        // The button clicked on the screen
        Button clickedButton = (Button) event.getSource();

        // Perform an action based on the button clicked
        switch (clickedButton.getId()) {
            case "savingsButton":
                System.out.println("Savings Account Selected");
                // If the user selects Savings show the UI for requesting an amount.
                stage = (Stage) savingsButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/WithdrawAmountUI.fxml"));
                root = loader.load();

                // Let the Controller know about the logged in user
                withdrawController = (WithdrawController) loader.getController();
                withdrawController.setActiveUser(this.activeUser);
                withdrawController.setCustomers(customers);

                // Set the savings account information
                this.savingsAccount = (SavingsAccount) this.activeUser.getAccounts().get(0);
                withdrawController.setAccountSelected(savingsAccount.getACCOUNT_TYPE());

                // Show the Withdraw Amount UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "checkingButton":
                System.out.println("Checking Account Selected");
                // If the user selects Checking show the UI for requesting an amount.
                stage = (Stage) checkingButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/WithdrawAmountUI.fxml"));
                root = loader.load();

                // Let the Controller know about the logged in user
                withdrawController = (WithdrawController) loader.getController();
                withdrawController.setActiveUser(this.activeUser);
                withdrawController.setCustomers(customers);

                // Set the checking account information
                this.checkingAccount = (CheckingAccount) this.activeUser.getAccounts().get(1);
                withdrawController.setAccountSelected(checkingAccount.getACCOUNT_TYPE());

                // Show the Withdraw Amount UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "cancelButton":
                System.out.println("Cancel Button Selected");
                // If the user cancels the withdraw screen go back to the main menu.
                stage = (Stage) cancelButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/MainMenuUI.fxml"));
                root = loader.load();

                // Let the MainMenu know about the logged in user
                mainMenuController = (MainMenuController) loader.getController();
                mainMenuController.setActiveUser(activeUser);
                mainMenuController.setCustomers(customers);

                // Show the Main Menu UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "withdrawButton":
                System.out.println("Withdraw Button Selected");

                this.handleWithdraw(amountTextField.getText());

                break;
            case "cancelAmountButton":
                System.out.println("Cancel Amount Button Selected");
                // If the user cancels at the withdraw amount screen go back to the account selection screen
                stage = (Stage) cancelAmountButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/WithdrawSelectionUI.fxml"));
                root = loader.load();

                // Let the Controller know about the logged in user
                withdrawController = (WithdrawController) loader.getController();
                withdrawController.setActiveUser(this.activeUser);
                withdrawController.setCustomers(customers);
                withdrawController.setButtonText();

                // Show the Withdraw Selection UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "signOutButton":
                System.out.println("Sign Out Button Selected");
                // If the user is finished sign out and show WelcomeUI
                stage = (Stage) signOutButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/WelcomeUI.fxml"));
                root = loader.load();
                
                WelcomeController welcomeController = (WelcomeController) loader.getController();
                welcomeController.setCustomers(customers);

                // Show the Welcome UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "mainMenuButton":
                System.out.println("Main Menu Button Selected");
                // If the user clicks the main menu button return them back to the main menu
                stage = (Stage) mainMenuButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/MainMenuUI.fxml"));
                root = loader.load();

                // Let the MainMenu know about the logged in user
                mainMenuController = (MainMenuController) loader.getController();
                mainMenuController.setActiveUser(activeUser);
                mainMenuController.setCustomers(customers);

                // Show the Main Menu UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            default:
                System.err.println("++WARN: Not a valid button");
                break;
        }

    }

    // Validate the user's withdraw request and update the user's account
    private void handleWithdraw(String inputAmout) throws IOException {
        NumberFormat formatter;
        double withdrawAmount = 0.0;
        savingsAccount = (SavingsAccount) this.activeUser.getAccounts().get(0);
        checkingAccount = (CheckingAccount) this.activeUser.getAccounts().get(1);

        // Verify the user entered a whole number
        if (inputAmout.matches("\\d+")) {
            System.out.println("Number was entered");
            withdrawAmount = Double.parseDouble(inputAmout);

            // If the user picked the savings update the account and show the completion screen
            if (this.accountSelected.equals("SAVINGS")) {
                // Check that the users has sufficient funds for the entered amount
                if (savingsAccount.getBalance() - withdrawAmount >= 0) {
                    // Update the balance
                    savingsAccount.setBalance(savingsAccount.getBalance() - withdrawAmount);
                    System.out.println("NEW SAVINGS BALANCE : " + savingsAccount.getBalance());

                    // Set the new UI
                    stage = (Stage) withdrawButton.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("/View/WithdrawCompletedUI.fxml"));
                    root = loader.load();

                    // Let the Controller know about the logged in user
                    withdrawController = (WithdrawController) loader.getController();
                    withdrawController.setActiveUser(this.activeUser);
                    withdrawController.setCustomers(customers);

                    // Show the new balance on the completion screen
                    formatter = NumberFormat.getCurrencyInstance();
                    withdrawController.setBalanceText("NEW BALANCE: " + formatter.format(this.activeUser.getAccounts().get(0).getBalance()));

                    // Show the Withdraw Completion UI
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    // Display a warning that funds were not available for the entered amount
                    System.out.println("INSUFFICENT FUNDS");
                    errorLabel.setText("INSUFFICENT FUNDS FOR THAT AMOUNT");
                    errorLabel.setOpacity(1);
                }
            }
            // If the user picked the checking update the account and show the completion screen
            if (this.accountSelected.equals("CHECKING")) {
                // Check that the users has sufficient funds for the entered amount
                if (checkingAccount.getBalance() - withdrawAmount >= 0) {
                    // Update the balance
                    checkingAccount.setBalance(checkingAccount.getBalance() - withdrawAmount);
                    System.out.println("NEW CHECKING BALANCE : " + checkingAccount.getBalance());

                    // Set the new UI
                    stage = (Stage) withdrawButton.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("/View/WithdrawCompletedUI.fxml"));
                    root = loader.load();

                    // Let the Controller know about the logged in user
                    withdrawController = (WithdrawController) loader.getController();
                    withdrawController.setActiveUser(this.activeUser);
                    withdrawController.setCustomers(customers);

                    // Show the new balance on the completion screen
                    formatter = NumberFormat.getCurrencyInstance();
                    withdrawController.setBalanceText("NEW BALANCE: " + formatter.format(this.activeUser.getAccounts().get(1).getBalance()));

                    // Show the Withdraw Completion UI
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    // Display a warning that funds were not available for the entered amount
                    System.out.println("INSUFFICENT FUNDS");
                    errorLabel.setText("INSUFFICENT FUNDS FOR THAT AMOUNT");
                    errorLabel.setOpacity(1);
                }
            }
        } else {
            // Warn the user that a whole number needs to be entered. No letters and no change can be dispensed
            System.out.println("NOT A NUMBER");
            errorLabel.setText("PLEASE ENTER A POSITIVE WHOLE NUMBER. FOR EXAMPLE, 20.");
            errorLabel.setOpacity(1);
        }

        System.out.println("Amount: " + withdrawAmount);
    }

    // Show the user's balance in the button
    public void setButtonText() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        savingsButton.setText("SAVINGS ACCOUNT: " + formatter.format(activeUser.getAccounts().get(0).getBalance()));
        checkingButton.setText("CHECKING ACCOUNT: " + formatter.format(activeUser.getAccounts().get(1).getBalance()));
    }

    public void setBalanceText(String balance) {
        this.balanceLabel.setText(balance);
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

    /**
     * @return the accountSelected
     */
    public String getAccountSelected() {
        return accountSelected;
    }

    /**
     * @param accountSelected the accountSelected to set
     */
    public void setAccountSelected(String accountSelected) {
        this.accountSelected = accountSelected;
    }

}
