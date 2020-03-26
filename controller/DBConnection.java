/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cblai
 */
public class DBConnection {
    private static final String databaseName = "U0645S";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U0645S";
    private static final String password = "53688680814";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL,username,password);
        System.out.println("Successful Connection");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        conn.close();
        System.out.println("Connection closed");
    }

}
