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
 * Date: April 18th, 2021 Class: DepositController 
 * Description: This class handles the functionality of a user performing a deposit.
 */
public class DepositController implements Initializable {

    // Global Controller Variables
    private Stage stage;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private MainMenuController mainMenuController;
    private DepositController depositController;

    private Customer activeUser;
    private ArrayList<Customer> customers;
    private String accountSelected = null;
    private SavingsAccount savingsAccount = null;
    private CheckingAccount checkingAccount = null;
    private final double MAX_DEPOSIT_AMOUNT = 5000.00;

    // UI Elements
    @FXML
    private Button savingsButton;
    @FXML
    private Button checkingButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button depositButton;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Handle all of the buttons on the Deposit screens
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
                loader = new FXMLLoader(getClass().getResource("/View/DepositAmountUI.fxml"));
                root = loader.load();

                // Let the Controller know about the logged in user
                setDepositController((DepositController) loader.getController());
                getDepositController().setActiveUser(this.activeUser);
                getDepositController().setCustomers(customers);

                // Set the savings account information
                this.savingsAccount = (SavingsAccount) this.activeUser.getAccounts().get(0);
                getDepositController().setAccountSelected(savingsAccount.getACCOUNT_TYPE());

                // Show the Deposit Amount UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "checkingButton":
                System.out.println("Checking Account Selected");
                // If the user selects Checking show the UI for requesting an amount.
                stage = (Stage) checkingButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/DepositAmountUI.fxml"));
                root = loader.load();

                // Let the Controller know about the logged in user
                depositController = (DepositController) loader.getController();
                depositController.setActiveUser(this.activeUser);
                depositController.setCustomers(customers);

                // Set the checking account information
                this.checkingAccount = (CheckingAccount) this.activeUser.getAccounts().get(1);
                depositController.setAccountSelected(checkingAccount.getACCOUNT_TYPE());

                // Show the Deposit Amount UI
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "cancelButton":
                System.out.println("Cancel Button Selected");
                // If the user cancels the deposit screen go back to the main menu.
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
            case "depositButton":
                System.out.println("Deposit Button Selected");

                this.handleDeposit(amountTextField.getText());

                break;
            case "cancelAmountButton":
                System.out.println("Cancel Amount Button Selected");
                // If the user cancels at the deposit amount screen go back to the account selection screen
                stage = (Stage) cancelAmountButton.getScene().getWindow();
                loader = new FXMLLoader(getClass().getResource("/View/DepositSelectionUI.fxml"));
                root = loader.load();

                // Let the Controller know about the logged in user
                depositController = (DepositController) loader.getController();
                depositController.setActiveUser(this.activeUser);
                depositController.setCustomers(customers);
                depositController.setButtonText();

                // Show the Deposit Selection UI
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

    // Validate the user's deposit request and update the user's account
    private void handleDeposit(String inputAmout) throws IOException {
        NumberFormat formatter;
        double depositAmount = 0.0;
        savingsAccount = (SavingsAccount) this.activeUser.getAccounts().get(0);
        checkingAccount = (CheckingAccount) this.activeUser.getAccounts().get(1);

        // Verify the user entered a whole number
        if (inputAmout.matches("\\d+")) {
            System.out.println("Number was entered");
            depositAmount = Double.parseDouble(inputAmout);

            // Verify the deposit amount is less than the max allowed
            if (depositAmount <= MAX_DEPOSIT_AMOUNT) {
                // If the user picked the savings update the account and show the completion screen
                if (this.accountSelected.equals("SAVINGS")) {
                    // Update the balance
                    savingsAccount.setBalance(savingsAccount.getBalance() + depositAmount);
                    System.out.println("NEW SAVINGS BALANCE : " + savingsAccount.getBalance());
                    
                    // Set the new UI
                    stage = (Stage) depositButton.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("/View/DepositCompletedUI.fxml"));
                    root = loader.load();

                    // Let the Controller know about the logged in user
                    depositController = (DepositController) loader.getController();
                    depositController.setActiveUser(this.activeUser);
                    depositController.setCustomers(customers);

                    // Show the new balance on the completion screen
                    formatter = NumberFormat.getCurrencyInstance();
                    depositController.setBalanceText("NEW BALANCE: " + formatter.format(this.activeUser.getAccounts().get(0).getBalance()));

                    // Show the Withdraw Completion UI
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }
                // If the user picked the checking update the account and show the completion screen
                if (this.accountSelected.equals("CHECKING")) {
                    // Update the balance
                    checkingAccount.setBalance(checkingAccount.getBalance() + depositAmount);
                    System.out.println("NEW CHECKING BALANCE : " + checkingAccount.getBalance());
                    
                    // Set the new UI
                    stage = (Stage) depositButton.getScene().getWindow();
                    loader = new FXMLLoader(getClass().getResource("/View/DepositCompletedUI.fxml"));
                    root = loader.load();

                    // Let the Controller know about the logged in user
                    depositController = (DepositController) loader.getController();
                    depositController.setActiveUser(this.activeUser);
                    depositController.setCustomers(customers);

                    // Show the new balance on the completion screen
                    formatter = NumberFormat.getCurrencyInstance();
                    depositController.setBalanceText("NEW BALANCE: " + formatter.format(this.activeUser.getAccounts().get(1).getBalance()));

                    // Show the Withdraw Completion UI
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }
            } else {
                // Warn the user that the max deposit amount is $5,000.00
                formatter = NumberFormat.getCurrencyInstance();
                
                System.out.println("DEPOSIT IS TOO BIG FOR ATM");
                errorLabel.setText("THE MAX AMOUNT THAT CAN BE DEPOSITED IS " + formatter.format(MAX_DEPOSIT_AMOUNT));
                errorLabel.setOpacity(1);
            }

        } else {
            // Warn the user that a whole number needs to be entered. No letters and no change can be dispensed
            System.out.println("NOT A NUMBER");
            errorLabel.setText("PLEASE ENTER A POSITIVE WHOLE NUMBER. FOR EXAMPLE, 20.");
            errorLabel.setOpacity(1);
        }

        System.out.println("Amount: " + depositAmount);
    }

    // Show the user's balance in the button
    public void setButtonText() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        savingsButton.setText("SAVINGS ACCOUNT: " + formatter.format(activeUser.getAccounts().get(0).getBalance()));
        checkingButton.setText("CHECKING ACCOUNT: " + formatter.format(activeUser.getAccounts().get(1).getBalance()));
    }
    
    // Update the balance label on the Deposit Completion screen
    public void setBalanceText(String balance) {
        this.balanceLabel.setText(balance);
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
     * @return the depositController
     */
    public DepositController getDepositController() {
        return depositController;
    }

    /**
     * @param depositController the depositController to set
     */
    public void setDepositController(DepositController depositController) {
        this.depositController = depositController;
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
