/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author cblai
 */
public class FifteenMinutes {
    private static Boolean apptWithinFifteen = false;
    
    public static void setToTrue(){
        apptWithinFifteen = true;
    }
    
    public static void setToFalse(){
        apptWithinFifteen = false;
    }
    
    public static final Boolean getApptWithinFifteen(){
        return apptWithinFifteen;
    }
    
    public static Boolean withinFifteen(LocalTime time){
        LocalTime nowTime = LocalTime.now();
        long timeBetween = ChronoUnit.MINUTES.between(nowTime,time);
        System.out.print(timeBetween + "minutes");
        if (timeBetween > 0 && timeBetween <= 15){
            return true;
        }
        return false;
    }

}
