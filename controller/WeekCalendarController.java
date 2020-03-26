/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.Calendar;
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
import controller.MainMenuController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class WeekCalendarController implements Initializable {

    Stage stage;
    Parent scene;
    
    @FXML
    private Label d1;
    @FXML
    private Label d7;
    @FXML
    private Label d6;
    @FXML
    private Label d5;
    @FXML
    private Label d4;
    @FXML
    private Label d3;
    @FXML
    private Label d2;
    
    @FXML
    private Label monthTxtLbl;
    
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
        monthTxtLbl.setText(getmonth());
        
        // Determine number of days in CURRENT month
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        
        YearMonth ym = YearMonth.of(year, month);
        int daysInMonth = ym.lengthOfMonth();
        
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        int actualDay = cal.get(Calendar.DAY_OF_MONTH);
        int daysBetween = 0;
        
        //Determine number of days in previous month
        int lastMonth = cal.get(Calendar.MONTH-1);
        
        
        // Set weekday to Saturday
        if (weekDay != 7){
            daysBetween = 7 - weekDay;
            weekDay = 7;
        }
        actualDay = actualDay + daysBetween;
        
        // String for current day (starting on Saturday) for sql statement appt retrieval
        
        cal.set(Calendar.DATE, actualDay);
        
                
        java.util.Date current_day = cal.getTime();
        SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatting.format(current_day);
        day = day.substring(0,10);
        
        String sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                    + " FROM appointment WHERE start LIKE '"
                                    + day + "%' ORDER BY time ASC;";
        
        System.out.println(day);

        String[] d = new String[7];
        for(int i = 0; i<7; i++){
            d[i] = String.valueOf(actualDay - (6-i)) + "\n";
        }
        
        try{
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            for(int q = 6; q >= 0; q--){
                ResultSet rs = stmt.executeQuery(sqlStatement);

                if(!rs.next()){
                    actualDay--;
                    cal.set(Calendar.DATE, actualDay);
                    current_day = cal.getTime();
                    day = formatting.format(current_day);
                    day = day.substring(0,10);
                    sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                    + " FROM appointment WHERE start LIKE '"
                                    + day + "%' ORDER BY time ASC;";
                } else {
                    do{
                        d[q] += rs.getString("time") + " " + rs.getString("title") + "\n";
                    }while(rs.next());
                    
                    actualDay--;
                    cal.set(Calendar.DATE, actualDay);
                    current_day = cal.getTime();
                    day = formatting.format(current_day);
                    day = day.substring(0,10);
                    sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                    + " FROM appointment WHERE start LIKE '"
                                    + day + "%' ORDER BY time ASC;";
                }
            }
            DBConnection.closeConnection();    
        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    
        d1.setText(d[0]);
        d2.setText(d[1]);
        d3.setText(d[2]);
        d4.setText(d[3]);
        d5.setText(d[4]);
        d6.setText(d[5]);
        d7.setText(d[6]);
        
    }    

    @FXML
    private void onActionOpenNewAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddApointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionOpenUpdateAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionOpenMonthCalendar(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MonthCalendar.fxml"));
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
