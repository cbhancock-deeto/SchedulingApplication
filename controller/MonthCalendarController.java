/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class MonthCalendarController implements Initializable {
    
    Stage stage;
    Parent scene;
    
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
    private Label d14;
    @FXML
    private Label d15;
    @FXML
    private Label d16;
    @FXML
    private Label d17;
    @FXML
    private Label d18;
    @FXML
    private Label d19;
    @FXML
    private Label d20;
    @FXML
    private Label d33;
    @FXML
    private Label d32;
    @FXML
    private Label d31;
    @FXML
    private Label d30;
    @FXML
    private Label d29;
    @FXML
    private Label d28;
    @FXML
    private Label d27;
    @FXML
    private Label d26;
    @FXML
    private Label d25;
    @FXML
    private Label d24;
    @FXML
    private Label d23;
    @FXML
    private Label d22;
    @FXML
    private Label d21;
    @FXML
    private Label d34;
    @FXML
    private Label d35;
    
    @FXML
    private Label monthLbl;

    private String getmonth(){
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
        
        monthLbl.setText(getmonth());
        
        // Find first day of month
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        String date = String.valueOf(cal.getTime());
        date = date.substring(0,3);
        int counter;
        if(date.equals("Sun")){
            counter = 0;
        }else if(date.equals("Mon")){
            counter = 1;
        }else if(date.equals("Tue")){
            counter = 2;
        }else if(date.equals("Wed")){
            counter = 3;
        }else if(date.equals("Thu")){
            counter = 4;
        }else if(date.equals("Fri")){
            counter = 5;
        }else{
            counter = 6;
        }
        
        // Determine number of days in month
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        
        YearMonth ym = YearMonth.of(year, month);
        int daysInMonth = ym.lengthOfMonth();
        
        
        cal.set(Calendar.DATE, 1);
        java.util.Date current_day = cal.getTime();
        SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatting.format(current_day);
        day = day.substring(0,10);
        
                
        String[] d = new String[35];
        String time, title;
                        
        String sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                    + " FROM appointment WHERE start LIKE '"
                                    + day + "%' ORDER BY time ASC;";
       
        try{
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            for(int i = 1; i<daysInMonth; i++){
                d[counter]=String.valueOf(i + "\n");
                ResultSet rs = stmt.executeQuery(sqlStatement);
                if(rs.next() == false){
                    counter++;
                    cal.set(Calendar.DATE, i+1);
                    current_day = cal.getTime();
                    day = formatting.format(current_day);
                    day = day.substring(0,10);
                    sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                    + " FROM appointment WHERE start LIKE '"
                                    + day + "%' ORDER BY time ASC;";
                    continue;
                } else {
                    do{
                        time = rs.getString("time");
                        title = rs.getString("title");
                        d[counter] = d[counter] + time + "-" + title + "\n";
                    }while(rs.next());
                }
                counter++;
                cal.set(Calendar.DATE, i+1);
                current_day = cal.getTime();
                day = formatting.format(current_day);
                day = day.substring(0,10);
                sqlStatement = "SELECT CAST(start AS TIME) AS time, title"
                                    + " FROM appointment WHERE start LIKE '"
                                    + day + "%' ORDER BY time ASC;";
            }
            DBConnection.closeConnection();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        for(int j = 0; j<35; j++){
            if(d[j] == null){
                d[j] = " ";
            }
        }
        
       
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
        d14.setText(String.valueOf(d[13]));
        d15.setText(String.valueOf(d[14]));
        d16.setText(String.valueOf(d[15]));
        d17.setText(String.valueOf(d[16]));
        d18.setText(String.valueOf(d[17]));
        d19.setText(String.valueOf(d[18]));
        d20.setText(String.valueOf(d[19]));
        d21.setText(String.valueOf(d[20]));
        d22.setText(String.valueOf(d[21]));
        d23.setText(String.valueOf(d[22]));
        d24.setText(String.valueOf(d[23]));
        d25.setText(String.valueOf(d[24]));
        d26.setText(String.valueOf(d[25]));
        d27.setText(String.valueOf(d[26]));
        d28.setText(String.valueOf(d[27]));
        d29.setText(String.valueOf(d[28]));
        d30.setText(String.valueOf(d[29]));
        d31.setText(String.valueOf(d[30]));
        d32.setText(String.valueOf(d[31]));
        d33.setText(String.valueOf(d[32]));
        d34.setText(String.valueOf(d[33]));
        d35.setText(String.valueOf(d[34]));
        
    }    

    @FXML
    private void onActionOpenAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActinoOpenUpdateAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionOpenWeekView(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/WeekCalendar.fxml"));
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
