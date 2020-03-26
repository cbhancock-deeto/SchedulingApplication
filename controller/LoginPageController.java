/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static controller.DBConnection.conn;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javafx.scene.control.Alert;
import model.Appointment;
import static model.Appointment.addAppointment;
import model.Customer;
import static model.Customer.addCustomer;
import static model.Customer.getCustomers;
import static model.FifteenMinutes.setToTrue;
import schedulingapplication.userInfo;

/**
 * FXML Controller class
 *
 * @author cblai
 */
public class LoginPageController implements Initializable {

    Stage stage;
    Parent scene;
    
    String inPass, actualPass, uId;
    
    @FXML
    private Button LoginLbl;

    @FXML
    private Button ExitLbl;

    @FXML
    private Label LoginPageLabel;

    @FXML
    private Label UserIDLbl;

    @FXML
    private TextField loginTxt;

    @FXML
    private Label PasswordLbl;

    @FXML
    private TextField passwordTxt;

    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale myLocale = Locale.getDefault();

       //Locale myLocale = new Locale("fr","FR");
       
       
       if(myLocale.getCountry().equals("FR")){
           LoginLbl.setText("S'identifier");
           ExitLbl.setText("Sortie");
           LoginPageLabel.setText("Page de Connexion");
           UserIDLbl.setText("Identifiant:");
           PasswordLbl.setText("Mot de Passe:");
       }

   
       String name, phone, address1, address2, postal, city, country, 
               customerId, addressId, cityId, countryId;
       Boolean activity;
        
        try{
            String sqlStatement = "SELECT customer.customerName, customer.active, address.phone, "
                    + "city.city, address.address, address.address2, address.postalCode, country.country, "
                    + "country.countryId, city.cityId, address.addressId, customer.customerId "
                    + "from country "
                    + "join city ON country.countryId = city.countryId " 
                    + "join address ON city.cityId = address.cityId "
                    + "join customer ON address.addressId = customer.addressId;";
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            
            if(rs.next() == false){
                System.out.println("No customers in database");
                return;
            } else {
                do{
                    name = rs.getString("customerName");
                    phone = rs.getString("phone");
                    address1 = rs.getString("address");
                    address2 = rs.getString("address2");
                    postal = rs.getString("postalCode");
                    city = rs.getString("city");
                    country = rs.getString("country");
                    customerId = rs.getString("customerId");
                    addressId = rs.getString("addressId");
                    cityId = rs.getString("cityId");
                    countryId = rs.getString("countryId");
                    activity = rs.getBoolean("active");
                    
                    Customer cust = new Customer(name, phone,address1,address2,postal,city,country,
                                        customerId,addressId,cityId,countryId,activity);
                    
                    addCustomer(cust);
                    
                }while(rs.next());
            }
            DBConnection.closeConnection();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }   
    
    private static String toLocalDateTime(String inDateTime, String country, String province) throws ParseException{
        
            java.util.Date parsedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inDateTime);
            String inTime = new SimpleDateFormat("HH:mm").format(parsedDate);
            String inDate = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);

            if (country.equals("USA") || country.equals("US")){
                country = "America";
            }
            
            LocalDate customerDate = LocalDate.parse(inDate);
            LocalTime customerTime = LocalTime.parse(inTime);

            ZoneId customerZoneId = ZoneId.of(country + "/" + province);
            ZonedDateTime customerZDT = ZonedDateTime.of(customerDate,customerTime,customerZoneId);
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            Instant customerToGMTInstant = customerZDT.toInstant();
            ZonedDateTime gmtToLocalZDT = customerToGMTInstant.atZone(localZoneId);

            String outDate = String.valueOf(gmtToLocalZDT.toLocalDate());
            String outTime = String.valueOf(gmtToLocalZDT.toLocalTime());
            String outDateTime = outDate + " " + outTime;
        
            return outDateTime;
        }
    
    
    
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        Locale myLocale = Locale.getDefault();
        
        userInfo currentUser = new userInfo(loginTxt.getText());
        
        try{
            
            inPass = passwordTxt.getText();
            String sqlStatement = "SELECT userId, password FROM user WHERE userName = '" + currentUser.getUserName() + "'";
            DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            
             // Code to execute if myLocal.getCountry = "FR":
            if(rs.next() == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(myLocale.getCountry().equals("FR")){
                    alert.setContentText("Nom d'utilisateur invalide"); // --> invalid username
                    alert.showAndWait();
                }
                else{
                    alert.setContentText("Invalid username"); // --> invalid username
                    alert.showAndWait();
                }
                    
                loginTxt.setText("");
                passwordTxt.setText("");
                DBConnection.closeConnection();
                return;
            } else {
                do{
                    uId = rs.getString("userId");
                    actualPass = rs.getString("password");
                    
                }while(rs.next());
            }
            currentUser.setUserId(uId);
            
            if (!actualPass.equals(inPass)){
                
                
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                if(myLocale.getCountry().equals("FR")){
                    alert.setContentText("Mot de passe incorrect"); // --> invalid username
                    alert.showAndWait();
                }
                else{
                    alert.setContentText("Incorrect password"); // --> invalid username
                    alert.showAndWait();
                }
                
                loginTxt.setText("");
                passwordTxt.setText("");
                DBConnection.closeConnection();
                return;
            }
            
            sqlStatement = "SELECT appointment.appointmentId, appointment.customerId, "
                        + "appointment.title, appointment.description, \n" +
                        "appointment.location, appointment.contact, appointment.type,\n" +
                        "appointment.url, appointment.start, appointment.end, "
                        + "customer.customerName, country.country, city.city\n" +
                        "FROM country\n" +
                        "JOIN city on country.countryId = city.countryId \n" +
                        "JOIN address on city.cityId = address.cityId\n" +
                        "JOIN customer on address.addressId = customer.addressId\n" +
                        "JOIN appointment ON customer.customerId = appointment.customerId \n" +
                        "WHERE appointment.userId = " +uId + ";";
            String apptId, custId, title, description, location,
                    contact, type, url, start, end, customer, city, country;
            
            rs = stmt.executeQuery(sqlStatement);
            
            
        
            
            while(rs.next()){
                apptId = rs.getString("appointmentId");
                custId = rs.getString("customerId");
                title = rs.getString("title");
                description = rs.getString("description");
                location = rs.getString("location");
                contact = rs.getString("contact");
                type = rs.getString("type");
                url = rs.getString("url");
                start = rs.getString("start");
                end = rs.getString("end");
                customer = rs.getString("customerName");
                city = rs.getString("city");
                country = rs.getString("country");
               
                start = toLocalDateTime(start, country, city);
                end = toLocalDateTime(end,country,city);
                System.out.println("before tempAppt function call");
                Appointment tempAppt = new Appointment(apptId, custId,title,description,location,contact, type,
                                                    url, start, end, customer, city, country);
                
                addAppointment(tempAppt);
                
                System.out.println("After add appt");
                java.util.Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(start);
                System.out.println("After parsing");
                String startHour = new SimpleDateFormat("HH").format(dateStart);
                String startMinutes = new SimpleDateFormat("mm").format(dateStart);
                //System.out.println("hour: " + startHour + "\nminutes: " + startMinutes);
                
                LocalTime startTime = LocalTime.of(Integer.parseInt(startHour),Integer.parseInt(startMinutes));
                
                LocalTime nowTime = LocalTime.now();
                long timeBetween = ChronoUnit.MINUTES.between(nowTime,startTime);
                System.out.print(timeBetween + "minutes\n");
                if (timeBetween > 0 && timeBetween <= 15){
                    setToTrue();
                }
                
                
                System.out.println(tempAppt.getStartDate() + "\n");
            }
            
            DBConnection.closeConnection();
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        
        Calendar calendar = Calendar.getInstance();
        
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        System.out.println(currentTimestamp);
        
        String filename = "userTimestamp.txt";
        
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);
        
        outputFile.println(userInfo.getUserName() + "-" + currentTimestamp);
        
        outputFile.close();
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        
    }
    
    private int appointmentTimeAdjustment(String country, String city){
        int offset = 0;
        TimeZone userTimeZone = TimeZone.getDefault();
        
        
        return offset;
    }
    
    @FXML
    void onActionExitApp(ActionEvent event) {
        System.exit(0);
    }
    
}
