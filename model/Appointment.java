/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author cblai
 */
public class Appointment {
    private String apptId, title, description, location, contact, type, url, startDate, startTime, 
                    custId, endDate, endTime, customer, country, city;

    
    private static ObservableList<Appointment> allAppointments
            = FXCollections.observableArrayList();
    
    
            
    public void setCountry(String country){
        this.country = country;
    }     
    
    public String getCountry(){
        return this.country;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCity(){
        return this.city;
    }
            
    
    
    public static void addAppointment(Appointment appt){
        allAppointments.add(appt);
    }
    public static void removeAppointment(Appointment appt){
        allAppointments.remove(appt);
    }
    public static ObservableList<Appointment> getAppointments(){
        return allAppointments;
    }
    public static void setAppointmentList(ObservableList<Appointment> apptList){
        allAppointments = apptList;
    }
    
    public static int getApptIndex(Appointment appt){
        return allAppointments.indexOf(appt);
    }
    
    public static void replaceAppointment(Appointment appt, int index){
        System.out.println(appt.getTitle() + index);
        allAppointments.set(index,appt);
    }
    
    
    //Checks through entire array to see if the ldt exists after start time and
    //before end time of any existing appointment
    public Boolean checkOverlap(LocalDateTime ldt) throws ParseException{
        
        for (Appointment appt : getAppointments()){
            
            LocalDateTime apptDTStart = dateStringToLDT(appt.getStartDate());
            LocalDateTime apptDTEnd = dateStringToLDT(appt.getEndDate());
            
            if((ldt.isAfter(apptDTStart) && ldt.isBefore(apptDTEnd)) 
                    || (ldt.equals(apptDTStart))){
                return true;
            }
        }
        return false;
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
    
   
    public Appointment(String apptId, String custId, String title, String description, String location, String contact,
                       String type, String url, String start, String end, String customer, String city, String country) throws ParseException{
        
        this.apptId = apptId;
        this.custId = custId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.customer = customer;
        this.city = city;
        this.country = country;

        
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(start);
        this.startTime = new SimpleDateFormat("HH:mm").format(dateStart);
        this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);


        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(end);
        this.endTime = new SimpleDateFormat("HH:mm").format(dateEnd);
        this.endDate = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
       
    }
    
    public Appointment(String apptId, String custId, String title, String description, String location, String contact,
                       String type, String url, String start, String end, String customer) throws ParseException{
        
        this.apptId = apptId;
        this.custId = custId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.customer = customer;

        
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
        this.startTime = new SimpleDateFormat("HH:mm:ss").format(dateStart);
        this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);


        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end);
        this.endTime = new SimpleDateFormat("HH:mm:ss").format(dateEnd);
        this.endDate = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
       
    }
    
    public void setCustomer(String customer){
        this.customer = customer;
    }
    
    public String getCustomer(){
        return this.customer;
    }
  
    public void setApptId(String apptId){
        this.apptId = apptId;
    }
     
    public String getApptId(){
        return this.apptId;
    }
    
    public void setEndTime(LocalDateTime ldt) throws ParseException{
        String end = String.valueOf(ldt);
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end);
        this.endTime = new SimpleDateFormat("HH:mm").format(dateEnd);
    }
    public void setStartTime(LocalDateTime ldt) throws ParseException{
        String start = String.valueOf(ldt);
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
        this.startTime = new SimpleDateFormat("HH:mm").format(dateStart);
    }
    public void setStartDate(LocalDateTime ldt){
        
        Date date1 = java.sql.Timestamp.valueOf(ldt);
        DateFormat formatting = new SimpleDateFormat("yyyy-mm-dd");
        String sDate = formatting.format(date1);
        this.startDate = sDate;
    }
    public void setCustId(String custId){
        this.custId = custId;
    }
    public String getCustId(){
        return this.custId;
    }
    public String getStartTime(){
        return this.startTime;
    }
    public String getEndTime(){
        return this.endTime;
    }
    public String getStartDate(){
        return this.startDate;
    }
    
    public String getEndDate(){
        return this.endDate;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getLocation(){
        return this.location;
    }
    public String getContact(){
        return this.contact;
    }
    public String getType(){
        return this.type;
    }
    public String getUrl(){
        return this.url;
    }    
}
