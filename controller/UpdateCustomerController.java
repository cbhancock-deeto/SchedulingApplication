/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Customer;
import static model.Customer.getCustIndex;
import static model.Customer.replaceCustomer;
import static schedulingapplication.userInfo.getDate;
import static schedulingapplication.userInfo.getUserName;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class UpdateCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    Customer tempCust;
    int c_index;
    
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField adressTxt;
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
    private ToggleGroup Activity;
    @FXML
    private RadioButton inactiveRBtn;

    
    public void sendCustomer(Customer cust){
        
        c_index = getCustIndex(cust);
        nameTxt.setText(cust.getName());
        phoneTxt.setText(cust.getPhone());
        adressTxt.setText(cust.getAddress1());
        address2Txt.setText(cust.getAddress2());
        postalCodeTxt.setText(cust.getPostal());
        countryTxt.setText(cust.getCountry());
        cityTxt.setText(cust.getCity());
        
        if(cust.getActivity())
            activeRBtn.setSelected(true);
        else
            inactiveRBtn.setSelected(true);  
        
        tempCust = cust;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TOD
    }    

    @FXML
    private void onActionSave(ActionEvent event) throws IOException {
        // Country change
        // Check if country has changed
        
        String sqlStatement;
        String newCountry = countryTxt.getText();
        String newCity = cityTxt.getText();
        String newAddress1 = adressTxt.getText();
        String newAddress2 = address2Txt.getText();
        String newPostal = postalCodeTxt.getText();
        String newName = nameTxt.getText();
        String newPhone = phoneTxt.getText();
        
        String newCountryId, newCityId, newAddressId;
        
        ResultSet rs;
        try{
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();

            // Country
            if(!newCountry.equals(tempCust.getCountry())){
                sqlStatement = "SELECT countryId from country where country = '"
                    + newCountry + "';";
                rs = stmt.executeQuery(sqlStatement);
                    if(rs.next() == false){
                    // countryId does not exist and a new country needs to be created
                        sqlStatement = "INSERT INTO country (country, createDate, createdBy, lastUpdateBy) VALUES "
                            + "('" + newCountry + 
                            "', '" + getDate() +
                            "', '" + getUserName() +
                            "', '" + getUserName() + "');";
                        stmt.executeUpdate(sqlStatement);
                    
                    // Retrieve countryId
                        sqlStatement = "SELECT countryId from country where country = '"
                        + tempCust.getCountry() + "';";
                        rs = stmt.executeQuery(sqlStatement);
                        rs.next();
                        newCountryId = rs.getString("countryId");
                        tempCust.setCountry(newCountry);
                        tempCust.setCountryId(newCountryId);
                } else {
                        //Country Id DOES exist and needs to be retrieved/assigned
                        newCountryId = rs.getString("countryId");
                        tempCust.setCountryId(newCountryId);
                        tempCust.setCountry(newCountry);
                }
            }
            
            // City
            if(!newCity.equals(tempCust.getCity())){
                //City Id does not exist and needs to be created
                sqlStatement = "SELECT cityId from city where city = '"
                        + newCity + "';";
                rs = stmt.executeQuery(sqlStatement);
                if(rs.next() == false){
                    sqlStatement = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy) VALUES "
                            + "('" + newCity +
                            "', '" + tempCust.getCountryId() +
                            "', '" + getDate() +
                            "', '" + getUserName() +
                            "', '" + getUserName() + "');";
                    stmt.executeUpdate(sqlStatement);
                    
                    //Retrieve cityId
                    sqlStatement = "SELECT cityId from city where city = '"
                        + newCity + "';";
                    rs = stmt.executeQuery(sqlStatement);
                    
                    rs.next();
                    newCityId = rs.getString("cityId");
                    tempCust.setCity(newCity);
                    tempCust.setCityId(newCityId);
                } else {
                    
                    //city Id does exist and needs to be retrieved/assigned
                    newCityId = rs.getString("cityId");
                    tempCust.setCity(newCity);
                    tempCust.setCityId(newCityId);
                }
            }
            
            if(!newAddress1.equals(tempCust.getAddress1())){
                //Address Id does not exist and needs to be created
                sqlStatement = "SELECT addressId from address where address = '"
                        + newAddress1 + "';";
                rs = stmt.executeQuery(sqlStatement);
                if(rs.next() == false){
                    sqlStatement = "INSERT INTO address (address, address2, cityId, postalCode, phone,"
                            + " createDate, createdBy, lastUpdateBy) VALUES "
                            + "('" + newAddress1 +
                            "', '" + newAddress2 +
                            "', '" + tempCust.getCityId() +
                            "', '" + newPostal +
                            "', '" + newPhone +
                            "', '" + getDate() +
                            "', '" + getUserName() +
                            "', '" + getUserName() + "');";
                    stmt.executeUpdate(sqlStatement);
                    
                    //Retrieve addressId
                    sqlStatement = "SELECT addressId from address where address = '"
                        + newAddress1 + "';";
                    rs = stmt.executeQuery(sqlStatement);
                    rs.next();
                    newAddressId = rs.getString("addressId");
                    tempCust.setAddress1(newAddress1);
                    tempCust.setAddressId(newAddressId);
                    
                    sqlStatement = "UPDATE customer SET addressId = '" + newAddressId + "',  "
                        + "lastUpdateBy = '" + getUserName() + "' "
                        + "where customerId = '"
                        + tempCust.getCustomerId() + "';";
                    
                    stmt.executeUpdate(sqlStatement);
                } else {
                    
                    //address Id does exist and needs to be retrieved/assigned
                    newAddressId = rs.getString("addressId");
                    tempCust.setAddress1(newAddress1);
                    tempCust.setAddressId(newAddressId);
                    
                }
            }
            
            //update postal if different
            if(!newPostal.equals(tempCust.getPostal())){
                sqlStatement = "UPDATE address SET postalCode = '" + newPostal + "',  "
                        + "lastUpdateBy = '" + getUserName() + "', "
                        + "lastUpdate = '" + getDate()
                        + "' where addressId = '"
                        + tempCust.getAddressId() + "';";
                stmt.executeUpdate(sqlStatement);
                tempCust.setPostal(newPostal);
            }
            
            //update phone if different
            if(!newPhone.equals(tempCust.getPhone())){
                sqlStatement = "UPDATE address SET phone = '" + newPhone + "', "
                        + "lastUpdateBy = '" + getUserName() + "', "
                        + "lastUpdate = '" + getDate()
                        + "' where addressId = '"
                        + tempCust.getAddressId() + "';";
                stmt.executeUpdate(sqlStatement);
                tempCust.setPhone(newPhone);
            }
            
            //update address2 if different
            if(!newAddress2.equals(tempCust.getAddress2())){
                sqlStatement = "UPDATE address SET address2 = '" + newAddress2 + "', "
                        + "lastUpdateBy = '" + getUserName() + "', "
                        + "lastUpdate = '" + getDate()
                        + "' where addressId = '"
                        + tempCust.getAddressId() + "';";
                stmt.executeUpdate(sqlStatement);
                tempCust.setAddress2(newAddress2);
            }
            
            if(!newName.equals(tempCust.getName())){
                sqlStatement = "UPDATE customer SET customerName = '" + newName + "', "
                        + "lastUpdateBy = '" + getUserName() + "', "
                        + "lastUpdate = '" + getDate()
                        + "' where customerId = '"
                        + tempCust.getCustomerId() + "';";
                stmt.executeUpdate(sqlStatement);
                tempCust.setName(newName);
            }
            
            if(activeRBtn.isSelected()){
                sqlStatement = "UPDATE customer SET active = 1, "
                        + "lastUpdateBy = '" + getUserName() + "', "
                        + "lastUpdate = '" + getDate()
                        + "' where customerId = '"
                        + tempCust.getCustomerId() + "';";
                tempCust.setActivity(true);
            } else {
                sqlStatement = "UPDATE customer SET active = 0, "
                        + "lastUpdateBy = '" + getUserName() + "', "
                        + "lastUpdate = '" + getDate()
                        + "' where customerId = '"
                        + tempCust.getCustomerId() + "';";
                tempCust.setActivity(false);
            }
            stmt.executeUpdate(sqlStatement);
            
            DBConnection.closeConnection();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        replaceCustomer(tempCust,c_index);
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
