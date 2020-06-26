/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qltv.tien_ich;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class DataConnection {
    
    private static Connection con;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
 
    public static Connection getConnection() throws SQLException {
        con = null;
        try {
            URL = "jdbc:mysql://localhost:3306/QLTV?zeroDateTimeBehavior=convertToNull";
            USER = "root";
            PASSWORD = "";
            // driver register
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (con);
    }
 
    public static void freeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ResultSet ExcuteQuery(String query) throws Exception {
        Statement statement = getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public void ExcuteUpdate(String sql) throws Exception {
        Statement statement = getConnection().createStatement();
        statement.executeUpdate(sql);
    }
}
