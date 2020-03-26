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
import model.Appointment;
import model.Customer;
import controller.UpdateAppointmentController;
import static model.Appointment.removeAppointment;
import static model.Customer.removeCustomer;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class AppointmentListController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentListTableView;
    @FXML
    private TableColumn<Appointment, ?> DateColumn;
    @FXML
    private TableColumn<Appointment, ?> TimeColumn;
    @FXML
    private TableColumn<Appointment, ?> TitleColumn;
    @FXML
    private TableColumn<Appointment, ?> CustomerColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppointmentListTableView.setItems(Appointment.getAppointments());
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        TimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        CustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
    }    
    
    Stage stage;
    Parent scene;
    
    
    // adjusts appointment times based on the time zone of the current user
    @FXML
    void adjustToCurrentTimeZone(ActionEvent event) {
        
    }

    // returns appointment times to New York times (this is assumed to be the default time zone
    // appointments are set to)
    @FXML
    void adjustToNewYorkTime(ActionEvent event) {

    }

    @FXML
    private void onActionOpenNewAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionOpenAppointmentDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AppointmentDetails.fxml"));
        loader.load();
        
        AppointmentDetailsController ADC = loader.getController();
        ADC.sendAppointment(AppointmentListTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void onActionOpenUpdateAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
        loader.load();
        
        UpdateAppointmentController UAC = loader.getController();
        UAC.sendAppointment(AppointmentListTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    private void onActionDeleteSelectedAppointment(ActionEvent event) throws IOException {
        removeAppointment(AppointmentListTableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentList.fxml"));
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
