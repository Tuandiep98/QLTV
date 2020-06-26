/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.DAO;

import QLTV.Model.Nhaxb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import QLTV.tien_ich.jdbcHelper;

/**
 *
 * @author Admin
 */
public class NhaXB_DB {
    private static Nhaxb readFromResultSet(ResultSet rs) throws SQLException {
        String manxb = rs.getString(1);
        String tennxb = rs.getString(2);
        Nhaxb nxb = new Nhaxb(manxb, tennxb);
        return nxb;
    }

    public  void insert(Nhaxb s){
        String sql = "insert into nhaxb values(?,?)";
        jdbcHelper.executeUpdate(sql, 
                s.getManxb(),
                
                s.getTennxb());
        
    }

    public  void update(Nhaxb s){
        String sql = "update nhaxb set tennxb=? where manxb=?";
        jdbcHelper.executeUpdate(sql, 
                s.getTennxb(),
               
                s.getManxb());
        
    }

    public  void Delete(String manxb){
        String sql = "delete nhaxb where manxb = ?";
        jdbcHelper.executeUpdate(sql, manxb);
    }
    
    public List<Nhaxb> select(){
        String sql = "select * from nhaxb";
        return select(sql);
    }
    
    private List<Nhaxb> select(String sql,Object... args){
        List<Nhaxb> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql, args);
                while (rs.next()) {                    
                    Nhaxb s = readFromResultSet(rs);
                    list.add(s);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
