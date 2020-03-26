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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import static model.FifteenMinutes.getApptWithinFifteen;
import model.ReportInterface;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;
    

    @FXML
    private Label t1;

    @FXML
    private Label t2;

    @FXML
    private Label t10;

    @FXML
    private Label t9;

    @FXML
    private Label t8;

    @FXML
    private Label t7;

    @FXML
    private Label t6;

    @FXML
    private Label t5;

    @FXML
    private Label t4;

    @FXML
    private Label t3;

    @FXML
    private Label t13;

    @FXML
    private Label t12;

    @FXML
    private Label t11;

    @FXML
    private Label d1;

    @FXML
    private Label d2;

    @FXML
    private Label d3;

    @FXML
    private Label d4;

    @FXML
    private Label d5;

    @FXML
    private Label d6;

    @FXML
    private Label d7;

    @FXML
    private Label d8;

    @FXML
    private Label d9;

    @FXML
    private Label d10;

    @FXML
    private Label d11;

    @FXML
    private Label d12;

    @FXML
    private Label d13;

    @FXML
    private Label howManyAppointments;

    @FXML
    private Label todayDateTxt;
    
    
    private String getweekday(){
        Calendar cal = Calendar.getInstance();
        String[] weekDays = new String[] { "Sunday", "Monday", "Tuesday", 
                                           "Wednesday", "Thursday", "Friday", 
                                           "Saturday" };
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        return weekDays[dayNum - 1];
    }
    
    public String getmonth(){
        Calendar cal = Calendar.getInstance();
        String[] months = {"January", "February", "March", 
                           "April", "May", "June", "July", 
                           "August", "September", "October", 
                           "November", "December"};
        
        int month = cal.get(Calendar.MONTH);
        return months[month];
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Display the current date in the top right of the window
        Calendar now = Calendar.getInstance(); 
        
        String weekDayTxt, monthTxt, dayTxt, yearTxt;
        
        weekDayTxt = getweekday();
        monthTxt = getmonth();
        dayTxt = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        yearTxt = String.valueOf(now.get(Calendar.YEAR));
        todayDateTxt.setText(weekDayTxt + ", " +
                             monthTxt + " " +
                             dayTxt + ", " +
                             yearTxt);
        
        
        // Retrieve appointment data for current day
        long m=System.currentTimeMillis();  
        java.sql.Date today=new java.sql.Date(m);  
        
        String[] t = new String[13];
        String[] d = new String[13];
        int counter = 0;
        
        try{
            String sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                + " FROM appointment WHERE start LIKE '"
                                + today + "%' ORDER BY time ASC;";
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            
            if(rs.next() == false){
                for (int i = 0; i < 13; i++){
                    t[i] = " ";
                    d[i] = " ";
                }
            } else {
                do{
                    t[counter] = rs.getString("time");
                    d[counter] = rs.getString("title");
                    counter++;
                }while(rs.next());
            }
            
            DBConnection.closeConnection();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        for(int j = 0; j<13; j++){
            if(d[j] == null){
                d[j] = " ";
                t[j] = " ";
            }
        }
        
        // Set times of appointments
        t1.setText(String.valueOf(t[0]));
        t2.setText(String.valueOf(t[1]));
        t3.setText(String.valueOf(t[2]));
        t4.setText(String.valueOf(t[3]));
        t5.setText(String.valueOf(t[4]));
        t6.setText(String.valueOf(t[5]));
        t7.setText(String.valueOf(t[6]));
        t8.setText(String.valueOf(t[7]));
        t9.setText(String.valueOf(t[8]));
        t10.setText(String.valueOf(t[9]));
        t11.setText(String.valueOf(t[10]));
        t12.setText(String.valueOf(t[11]));
        t13.setText(String.valueOf(t[12]));
   
        d1.setText(String.valueOf(d[0]));
        d2.setText(String.valueOf(d[1]));
        d3.setText(String.valueOf(d[2]));
        d4.setText(String.valueOf(d[3]));
        d5.setText(String.valueOf(d[4]));
        d6.setText(String.valueOf(d[5]));
        d7.setText(String.valueOf(d[6]));
        d8.setText(String.valueOf(d[7]));
        d9.setText(String.valueOf(d[8]));
        d10.setText(String.valueOf(d[9]));
        d11.setText(String.valueOf(d[10]));
        d12.setText(String.valueOf(d[11]));
        d13.setText(String.valueOf(d[12]));

        howManyAppointments.setText(String.valueOf(counter + " appointments"));
        System.out.println(getApptWithinFifteen());
        if (getApptWithinFifteen()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment"
                    + " within the next fifteen minutes, ya better get ready!");
            alert.setTitle("APPOINTMENT");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }    

    @FXML
    private void onActionOpenMonthCal(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MonthCalendar.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    @FXML
    private void onActionOpenViewCustomerList(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void onActionOpenNewAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionOpenUpdateAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionOpenLoginMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    
    @FXML
    void ApptByCustomerReportBtn(ActionEvent event) {
        ArrayList<String> customers = new ArrayList<String>();
        ArrayList<String> customerId = new ArrayList<String>();
        try{
                String sqlStatement = "select customerId, customerName from customer;";
                DBConnection.makeConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);
           
                while(rs.next()){
                    customers.add(rs.getString("customerName"));
                    customerId.add(rs.getString("customerId"));
                    }
                    DBConnection.closeConnection();
        }
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        // Lambda expression being used here to handle the formatting of reports
        ReportInterface generateReport = (s1, s2, s3) -> {
            String out1 = String.format("|%-10s|", s1);
            String out2 = String.format("|%-10s|", s2);
            String out3 = String.format("|%-15s|", s3);
            System.out.println(out1 + " " + out2 + " " + out3);
        };
           
        try{
            DBConnection.makeConnection();
            for(int i = 0; i < customerId.size(); i++){
                
                String custNum = String.format("|%-3s", customerId.get(i));
                String custName = String.format("%-15s", customers.get(i));
                System.out.println(custNum + "-" + custName + ":");
                
                String sqlStatement = "select title as Title, "
                    + "CONCAT(MONTH(start),\"-\",DAY(start),\"-\",YEAR(start)) as Date, "
                    + "TIME_FORMAT(start, '%h:%i %p') as Time from appointment "
                    + "WHERE customerId = " + customerId.get(i) 
                    + " GROUP BY userId, YEAR(start), MONTH(start), DAY(start), HOUR(start), MINUTE(start);";
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);
            
                String line = String.format("%40s","").replace(' ', '-');
                String out1 = String.format("|%-10s|", "Date:");
                String out2 = String.format("|%-10s|", "Time:");
                String out3 = String.format("|%-15s|", "Title:");
                System.out.println(out1 + " " + out2 + " " + out3);
                System.out.println(line);
                while(rs.next()){
                    generateReport.generate(rs.getString("Date"), rs.getString("Time"),rs.getString("Title"));
                }
                System.out.println(""); 
            }
            DBConnection.closeConnection();
            
            }
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @FXML
    void apptTypeByMonthReportBtn(ActionEvent event) {
        
        // Lambda expression being used here to handle the formatting of reports
        ReportInterface generateReport = (s1, s2, s3) -> {
            String out1 = String.format("|%-10s|", s1);
            String out2 = String.format("|%-10s|", s2);
            String out3 = String.format("|%-10s|", s3);
            System.out.println(out1 + " " + out2 + " " + out3);
        };
           
        try{
                String sqlStatement = "select type as Type, CONCAT(MONTH(start),\"-\", YEAR(start)) as Month, "
                        + "COUNT(Type) AS Number from appointment GROUP BY Type, YEAR(start), MONTH(start);";
                DBConnection.makeConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);
            
                String line = String.format("%40s","").replace(' ', '-');
                String out1 = String.format("|%-10s|", "Type:");
                String out2 = String.format("|%-10s|", "Month:");
                String out3 = String.format("|%-10s|", "Number:");
                System.out.println(out1 + " " + out2 + " " + out3);
                System.out.println(line);
                while(rs.next()){
                        generateReport.generate(rs.getString("Type"), rs.getString("Month"),rs.getString("Number"));
                }
            
            DBConnection.closeConnection();
                    
            }
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    @FXML
    void consultantScheduleReportBtn(ActionEvent event) {
        
        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> userId = new ArrayList<String>();
        try{
                String sqlStatement = "select userId, userName from user;";
                DBConnection.makeConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);
           
                while(rs.next()){
                    users.add(rs.getString("userName"));
                    userId.add(rs.getString("userId"));
                    }
                    DBConnection.closeConnection();
        }
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        // Lambda expression being used here to handle the formatting of reports
        ReportInterface generateReport = (s1, s2, s3) -> {
            String out1 = String.format("|%-10s|", s1);
            String out2 = String.format("|%-10s|", s2);
            String out3 = String.format("|%-15s|", s3);
            System.out.println(out1 + " " + out2 + " " + out3);
        };
           
        try{
            DBConnection.makeConnection();
            for(int i = 0; i < userId.size(); i++){
                
                String userNum = String.format("|%-2s", userId.get(i));
                String userName = String.format("%-15s", users.get(i));
                System.out.println(userNum + "-" + userName + ":");
                
                String sqlStatement = "select title as Title, "
                    + "CONCAT(MONTH(start),\"-\",DAY(start),\"-\",YEAR(start)) as Date, "
                    + "TIME_FORMAT(start, '%h:%i %p') as Time from appointment "
                    + "WHERE userId = " + userId.get(i) 
                    + " GROUP BY userId, YEAR(start), MONTH(start), DAY(start), HOUR(start), MINUTE(start);";
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlStatement);
            
                String line = String.format("%40s","").replace(' ', '-');
                String out1 = String.format("|%-10s|", "Date:");
                String out2 = String.format("|%-10s|", "Time:");
                String out3 = String.format("|%-15s|", "Title:");
                System.out.println(out1 + " " + out2 + " " + out3);
                System.out.println(line);
                while(rs.next()){
                    generateReport.generate(rs.getString("Date"), rs.getString("Time"),rs.getString("Title"));
                }
                System.out.println("");
            }
            DBConnection.closeConnection();
            
            }
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    

    @FXML
    private void onActionExitApp(ActionEvent event) {
        System.exit(0);
    }
    
}
