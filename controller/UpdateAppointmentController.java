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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import static model.Appointment.getAppointments;
import static model.Appointment.getApptIndex;
import static model.Appointment.replaceAppointment;
import model.Customer;
import static schedulingapplication.userInfo.getDate;
import static schedulingapplication.userInfo.getUserName;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class UpdateAppointmentController implements Initializable {

    
    
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField typeTxt;
    @FXML
    private TextField contactTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField startTimeTxt;
    @FXML
    private TextField endTimeTxt;
    @FXML
    private TextField urlTxt;
    @FXML
    private TextArea descriptionTxt;
    @FXML
    private TableView<Customer> selectCustomerTableView;
    @FXML
    private TableColumn<Customer, ?> customerColumn;
    @FXML
    private TableColumn<Customer, ?> locationColumn;

    
    Stage stage;
    Parent scene;
    
    public Customer selectCustomer(String id){
        for (Customer cust : Customer.getCustomers()){
            if(cust.getCustomerId().equals(id))
                return cust;
        }
        return null;
    }
    
    public Appointment selectAppointment(int id){
        for (Appointment appt : Appointment.getAppointments()){
            if(appt.getApptId().equals(String.valueOf(id)))
                return appt;
        }
        return null;
    }
    
    private LocalDateTime dateStringToLDT(String dateTime) throws ParseException{
        
        java.util.Date inApptTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
        String year = new SimpleDateFormat("yyyy").format(inApptTime);
        String month = new SimpleDateFormat("MM").format(inApptTime);
        String day = new SimpleDateFormat("dd").format(inApptTime);
        String hour = new SimpleDateFormat("HH").format(inApptTime);
        String minutes = new SimpleDateFormat("mm").format(inApptTime);
        String seconds = new SimpleDateFormat("ss").format(inApptTime);
       
        LocalDateTime outApptTime = LocalDateTime.of(Integer.parseInt(year),Integer.parseInt(month),
                Integer.parseInt(day),Integer.parseInt(hour),Integer.parseInt(minutes),Integer.parseInt(seconds));
        
        return outApptTime;
    }
    
    String custId, apptId;
    int a_index;
    
    
    public void sendAppointment(Appointment appt){
        titleTxt.setText(appt.getTitle());
        typeTxt.setText(appt.getType());
        contactTxt.setText(appt.getContact());
        locationTxt.setText(appt.getLocation());
        startTimeTxt.setText(appt.getStartDate() + " " + appt.getStartTime());
        endTimeTxt.setText(appt.getEndDate() + " " + appt.getEndTime());
        urlTxt.setText(appt.getUrl());
        descriptionTxt.setText(appt.getDescription());
        custId = appt.getCustId();
        apptId = appt.getApptId();
        a_index = getApptIndex(appt);
        
    }
    
    public Boolean checkOverlap(LocalDateTime ldt) throws ParseException{
        
        for (Appointment appt : getAppointments()){
            
            LocalDateTime apptDTStart = dateStringToLDT(appt.getStartDate() + " " + appt.getStartTime());
            LocalDateTime apptDTEnd = dateStringToLDT(appt.getEndDate() + " " + appt.getEndTime());
            
            if((ldt.isAfter(apptDTStart) && ldt.isBefore(apptDTEnd)) 
                    || (ldt.equals(apptDTStart))){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectCustomerTableView.setItems(Customer.getCustomers());
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        selectCustomerTableView.getSelectionModel().select(selectCustomer(custId));
    }    
 

    @FXML
    private void onActionSaveAppointment(ActionEvent event) throws IOException, ParseException {
        Customer tempCust = selectCustomerTableView.getSelectionModel().getSelectedItem();

        // Verify that input start is after 8 and before 5
        // convert string to LocalTime
        java.util.Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeTxt.getText());
        String startHour = new SimpleDateFormat("HH").format(dateStart);
        String startMinutes = new SimpleDateFormat("mm").format(dateStart);
                
        LocalTime apptStartTime = LocalTime.of(Integer.parseInt(startHour),Integer.parseInt(startMinutes));
        LocalTime businessHoursStart = LocalTime.of(8,0);
        LocalTime businessHoursEnd = LocalTime.of(17,0);
                
        // Alert that input end is after 8 and before 5
        if(apptStartTime.isAfter(businessHoursEnd) ||  apptStartTime.isBefore(businessHoursStart)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Appointments can be scheduled during business"
                    + "hours only");
            alert.showAndWait();
            return;
        }
        
        // Verify that new appointment time does not overlap with any existing appointments
        // Convert input information into localdatetime, then compare start and end times
        LocalDateTime ldtStart = dateStringToLDT(startTimeTxt.getText());
        LocalDateTime ldtEnd = dateStringToLDT(endTimeTxt.getText());
        
        Boolean startTimeOverlap = checkOverlap(ldtStart);
        Boolean endTimeOverlap = checkOverlap(ldtEnd);
        
        if(startTimeOverlap || endTimeOverlap){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Appointment is already scheduled during that time.");
            alert.showAndWait();
            return;
        }
        
        
        
        try{
            String sqlStatement = "UPDATE appointment SET "
                                    + "customerId = '" + tempCust.getCustomerId() + "', "
                                    + "title = '" + titleTxt.getText() + "', "
                                    + "type = '" + typeTxt.getText() + "', "
                                    + "contact = '" + contactTxt.getText() + "', "
                                    + "location = '" + locationTxt.getText() + "', "
                                    + "start = '" + startTimeTxt.getText() + "', "
                                    + "end = '" + endTimeTxt.getText() + "', "
                                    + "url = '" + urlTxt.getText() + "', "
                                    + "description = '" + descriptionTxt.getText() + "', "
                                    + "lastUpdateBy = '" + getUserName() + "', "
                                    + "lastUpdate = '" + getDate() + "' "
                                    + "WHERE appointmentId = '" + apptId + "';";
            
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlStatement);
            DBConnection.closeConnection();
        }
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        Appointment tempAppt = new Appointment(apptId, tempCust.getCustomerId(), titleTxt.getText(), descriptionTxt.getText(),
                                        locationTxt.getText(), contactTxt.getText(), typeTxt.getText(), urlTxt.getText(),
                                        startTimeTxt.getText(), endTimeTxt.getText(), tempCust.getName());
        
        replaceAppointment(tempAppt,a_index);
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
