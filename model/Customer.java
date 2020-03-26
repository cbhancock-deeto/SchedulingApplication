/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author cblai
 */
public class Customer {
    private String name, phone, address1, address2, postal, city, country;
    private String customerId, addressId, cityId, countryId;
    private Boolean activity;
    
    private ObservableList<Appointment> associatedAppointments
            = FXCollections.observableArrayList();
    
    private static ObservableList<Customer> allCustomers
            = FXCollections.observableArrayList();
    
    public Customer(String name, String phone, String address1, String address2,
                                        String postal, String city, String country,
                                        String customerId, String addressId, String cityId,
                                        String countryId, Boolean activity){
        this.name = name;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.postal = postal;
        this.city = city;
        this.country = country;
        this.customerId = customerId;
        this.addressId = addressId;
        this.cityId = cityId;
        this.countryId = countryId;
        this.activity = activity;
    }
    
    
    public void addAppointment(Appointment appt){
        associatedAppointments.add(appt);
    }
    public void removeAppointment(Appointment appt){
        associatedAppointments.remove(appt);
    }
    public ObservableList<Appointment> getAppointments(){
        return associatedAppointments;
    }
    public void setAssociatedAppointmentList(ObservableList<Appointment> apptList){
        associatedAppointments = apptList;
    }
    
    
    public static void addCustomer(Customer cust){
        allCustomers.add(cust);
    }
    public static void removeCustomer(Customer cust){
        allCustomers.remove(cust);
    }
    public static ObservableList<Customer> getCustomers(){
        return allCustomers;
    }
    public static void setCustomerList(ObservableList<Customer> custList){
        allCustomers = custList;
    }
    
    public static int getCustIndex(Customer cust){
        return allCustomers.indexOf(cust);
    }
    
    public static void replaceCustomer(Customer cust, int index){
        System.out.println(cust.getName() + index);
        allCustomers.set(index,cust);
    }
    
    
    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setAddress(String address){
        this.address1 = address;
    }
    public void setAddress1(String address){
        this.address1 = address;
    }
    public void setAddress2(String address){
        this.address2 = address;
    }
    public void setAddress(String address1, String address2){
        this.address1 = address1;
        this.address2 = address2;
    }
    public void setPostal(String postal){
        this.postal = postal;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    public void setCountryId(String countryId){
        this.countryId = countryId;
    }
    public void setAddressId(String addressId){
        this.addressId = addressId;
    }
    public void setCityId(String cityId){
        this.cityId = cityId;
    }
    public void setActivity(Boolean activity){
        this.activity = activity;
    }
    
    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getAddress1(){
        return this.address1;
    }
    public String getAddress2(){
        return this.address2;
    }
    public String getPostal(){
        return this.postal;
    }
    public String getCity(){
        return this.city;
    }
    public String getCountry(){
        return this.country;
    }
    public String getCustomerId(){
        return this.customerId;
    }
    public String getCountryId(){
        return this.countryId;
    }
    public String getAddressId(){
        return this.addressId;
    }
    public String getCityId(){
        return this.cityId;
    }
    public Boolean getActivity(){
        return this.activity;
    }
}
