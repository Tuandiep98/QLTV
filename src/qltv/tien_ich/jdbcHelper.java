/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.tien_ich;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */

public class jdbcHelper {
    private static com.mysql.jdbc.Connection con;
    private static String URL ="jdbc:mysql://localhost:3306/QLTV?zeroDateTimeBehavior=convertToNull";
    private static String USER="root";
    private static String PASSWORD="";
//    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    private static String url = "jdbc:mysql://localhost:3306/QLTV?zeroDateTimeBehavior=convertToNull";
//    private static String user = "root";
//    private static String pass = "";
    
//    static {
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    
    public static PreparedStatement prepareStatement(String sql, Object... args)throws SQLException{
        Connection cn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement st = null;
        if (sql.trim().startsWith("{")) {
            st = cn.prepareCall(sql);
        }else{
            st = cn.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            st.setObject(i+1, args[i]);
        }
        return st;
    }
    
    public static void executeUpdate(String sql, Object... args){
        try {
            PreparedStatement st = prepareStatement(sql, args);
            try {
                st.executeUpdate();
            } finally {
                st.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }   
    
    public static ResultSet executeQuery(String sql, Object... args){
        try {
            PreparedStatement st = prepareStatement(sql, args);
            return st.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
