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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class CustomerDetailsController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private Label nameTxt;
    @FXML
    private Label phoneTxt;
    @FXML
    private Label address1Txt;
    @FXML
    private Label address2Txt;
    @FXML
    private Label postalTxt;
    @FXML
    private Label countryTxt;
    @FXML
    private Label activityTxt;
    @FXML
    private Label cityTxt;

    public void sendCustomer(Customer cust){
        
        nameTxt.setText(cust.getName());
        phoneTxt.setText(cust.getPhone());
        address1Txt.setText(cust.getAddress1());
        address2Txt.setText(cust.getAddress2());
        postalTxt.setText(cust.getPostal());
        countryTxt.setText(cust.getCountry());
        cityTxt.setText(cust.getCity());
        
        if(cust.getActivity())
            activityTxt.setText("Active");
        else
            activityTxt.setText("Inactive");  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionOpenCustomerList(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
