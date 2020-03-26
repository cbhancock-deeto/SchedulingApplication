/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import static model.Customer.removeCustomer;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class CustomerPageController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private TableView<Customer> CustomerListTableView;
    @FXML
    private TableColumn<Customer, ?> NameColumn;
    @FXML
    private TableColumn<Customer, ?> LocationColumn;
    @FXML
    private TableColumn<Customer, ?> ContactionColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CustomerListTableView.setItems(Customer.getCustomers());
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        ContactionColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }    

    @FXML
    private void onActionOpenCustomerDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomerDetails.fxml"));
        loader.load();
        
        CustomerDetailsController CDC = loader.getController();
        CDC.sendCustomer(CustomerListTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show(); 
        
    }

    @FXML
    private void onActionOpenUpdateCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
        loader.load();
        
        UpdateCustomerController UCC = loader.getController();
        UCC.sendCustomer(CustomerListTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show(); 
        
    }
    
    @FXML
    void onActionOpenNewCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionDeleteSelectedCustomer(ActionEvent event) throws IOException {
        removeCustomer(CustomerListTableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionOpenMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
