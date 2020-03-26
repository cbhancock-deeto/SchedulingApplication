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
import model.Appointment;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class AppointmentDetailsController implements Initializable {

    @FXML
    private Label titleTxt;
    @FXML
    private Label descriptionTxt;
    @FXML
    private Label locationTxt;
    @FXML
    private Label contactTxt;
    @FXML
    private Label urlTxt;
    @FXML
    private Label startTxt;
    @FXML
    private Label endTxt;
    @FXML
    private Label typeTxt;

    public void sendAppointment(Appointment appt){
        titleTxt.setText(appt.getTitle());
        typeTxt.setText(appt.getType());
        contactTxt.setText(appt.getContact());
        locationTxt.setText(appt.getLocation());
        urlTxt.setText(appt.getUrl());
        descriptionTxt.setText(appt.getDescription());
        startTxt.setText(appt.getStartTime());
        endTxt.setText(appt.getEndTime());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    

    Stage stage;
    Parent scene;
    @FXML
    private void onActionOpenAppointmentList(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
