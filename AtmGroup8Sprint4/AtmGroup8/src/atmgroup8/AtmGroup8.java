/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmgroup8;

import Controller.CustomerList;
import Controller.WelcomeController;
import Model.Customer;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Authors: Ian Gwillim <https://github.com/I-M-G>, Jihun Suk, Vaibhav Gowda,
 * Yingjie Hong 
 * Date: April 4th, 2021 
 * Class: AtmGroup8 Description: This is the
 * launching point of our application and includes the main method.
 */
public class AtmGroup8 extends Application {

    private CustomerList customerList;
    private ArrayList<Customer> customers;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    // Load the Welcome UI when the application starts
    @Override
    public void start(Stage stage) throws Exception {
        customerList = new CustomerList();
        customers = customerList.getCustomers();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/WelcomeUI.fxml"));
        Parent root = loader.load();

        // Let the WelcomeController know about the customer list
        WelcomeController welcomeController = (WelcomeController) loader.getController();
        welcomeController.setCustomers(customers);

        Scene scene = new Scene(root);

        stage.setTitle("ATM by Group 8");
        stage.setScene(scene);
        stage.show();

    }

}
