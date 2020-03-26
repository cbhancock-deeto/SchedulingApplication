/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author cblai
 */
public class userInfo {
    private static String userName, uId;
    
    public userInfo(String userN){
        userName = userN;
    }
    
    public userInfo(){
        userName = "none";
        uId = "none";
    }
    
    public static void setUserName(String user){
        userName = user;
    }
    
    public static void setUserId(String userId){
        uId = userId;
    }
 
    public static String getUserName(){
        return userName;
    }
    
    public static String getUserId(){
        return uId;
    }
    
    public static String getDate(){
        SimpleDateFormat formatting = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String date = formatting.format(cal.getTime());
        return date;
    }
   
}
