/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import schedulingapplication.userInfo;
import static controller.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Customer;
import static model.Customer.addCustomer;
import static schedulingapplication.userInfo.getDate;
import static schedulingapplication.userInfo.getUserName;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField phoneNumberTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField address2Txt;
    @FXML
    private TextField postalCodeTxt;
    @FXML
    private TextField cityTxt;
    @FXML
    private TextField countryTxt;
    @FXML
    private RadioButton activeRBtn;

    @FXML
    private ToggleGroup activity;

    @FXML
    private RadioButton inactiveRBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void onActionSaveBtn(ActionEvent event) throws IOException {
        
        Calendar cal = Calendar.getInstance();
        
        String customerName,phoneNumber,address1,address2,postal,city,country;
        String countryId, cityId, addressId, customerId;
        customerName = customerNameTxt.getText();
        phoneNumber = phoneNumberTxt.getText();
        address1 = addressTxt.getText();
        address2 = address2Txt.getText();
        postal = postalCodeTxt.getText();
        city = cityTxt.getText();
        country = countryTxt.getText();
        int activeI;
        Boolean activeB;
        
        if(activeRBtn.isSelected()){
                activeI = 1;
                activeB = true;
        } else {
                activeI = 0;
                activeB = false;
        }
        
        
        
        try {
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            
            
            // First, country entry
            String sqlStatement = "SELECT countryId FROM country where "
                                + "country ='" + country + "';";
            ResultSet rs = stmt.executeQuery(sqlStatement);
            
            if(rs.next()){
                countryId = rs.getString("countryId");
            }
            else{
                System.out.println("line 116");
                sqlStatement = "INSERT INTO country (country, createDate, createdBy, lastUpdateBy) "
                        + "VALUES ('" 
                        + country + "', '"
                        + userInfo.getDate() + "', '"
                        + userInfo.getUserName() + "', '"
                        + userInfo.getUserName() + "');";
                stmt.executeUpdate(sqlStatement);
                sqlStatement = "SELECT countryId FROM country where "
                                + "country ='" + country + "';";
                rs = stmt.executeQuery(sqlStatement);
                rs.next();
                countryId = rs.getString("countryId");
            }
            
            
            // Second, city entry
            sqlStatement = "SELECT cityId FROM city where "
                                + "city ='" + city + "';";
            rs = stmt.executeQuery(sqlStatement);
            System.out.println("line 136");
            if(rs.next()){
                cityId = rs.getString("cityId");
            }
            else{
                sqlStatement = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy) "
                        + "VALUES ('" 
                        + city + "', '"
                        + countryId + "', '"
                        + userInfo.getDate() + "', '"
                        + userInfo.getUserName() + "', '"
                        + userInfo.getUserName() + "');";
                stmt.executeUpdate(sqlStatement);
                sqlStatement = "SELECT cityId FROM city where "
                                + "city ='" + city + "';";
                System.out.println("line 151");
                rs = stmt.executeQuery(sqlStatement);
                rs.next();
                cityId = rs.getString("cityId");
            }
            
            
            // Third, address entry
            sqlStatement = "SELECT addressId FROM address where "
                                + "address = '" + address1 + "' AND address2 = '"
                                + address2 + "';";
            rs = stmt.executeQuery(sqlStatement);
            System.out.println("line 163");
            if(rs.next()){
                addressId = rs.getString("addressId");
            }
            else{
                sqlStatement = "INSERT INTO address (address, address2, cityId, "
                        + "postalCode, phone, createDate, createdBy, lastUpdateBy) "
                        + "VALUES ('" 
                        + address1 + "', '"
                        + address2 + "', '"
                        + cityId + "', '"
                        + postal + "', '"
                        + phoneNumber + "', '"
                        + userInfo.getDate() + "', '"
                        + userInfo.getUserName() + "', '"
                        + userInfo.getUserName() + "');";
                System.out.println("line 179");
                stmt.executeUpdate(sqlStatement);
                System.out.println("line 181");
                sqlStatement = "SELECT addressId FROM address where "
                                + "address = '" + address1 + "' AND address2 = '"
                                + address2 + "';";
                rs = stmt.executeQuery(sqlStatement);
                rs.next();
                addressId = rs.getString("addressId");
            }
            
            
            
            // fourth, customer entry
                sqlStatement = "INSERT INTO customer (customerName,addressId,createDate, createdBy, lastUpdateBy, active) "
                        + "VALUES ('" 
                        + customerName + "', '"
                        + addressId + "', '"
                        + userInfo.getDate() + "', '"
                        + userInfo.getUserName() + "', '"
                        + userInfo.getUserName() + "', '"
                        + activeI + "');";
                System.out.println("line 201");
                stmt.executeUpdate(sqlStatement);
                
                sqlStatement = "SELECT customerId from customer where customerName = '" 
                        + customerName + "';";
                rs = stmt.executeQuery(sqlStatement);
                rs.next();
                customerId = rs.getString("customerId");
   
                Customer newCust = new Customer(customerName,phoneNumber,address1,address2, postal,city,country,customerId,addressId,
                                        cityId,countryId,activeB);
                addCustomer(newCust);
                
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
 
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
