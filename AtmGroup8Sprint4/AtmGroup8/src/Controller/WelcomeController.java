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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda, Yingjie Hong
 * Date: April 4th, 2021
 * Class: WelcomeController
 * Description: This class handles the functionality of the WelcomeUI.
 */
public class WelcomeController implements Initializable {

    private ArrayList<Customer> customers;
    
    private Stage stage;
    private Parent root;

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Circle statusCircle;

    // Load the LoginUi once the user has clicked Login
    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        System.out.println("LOGIN Button Clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginUI.fxml"));
        root = loader.load();
        stage = (Stage) loginButton.getScene().getWindow();
        
        // Let the WelcomeController know about the customer list
        LoginController loginController = (LoginController) loader.getController();
        loginController.setCustomers(customers);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
