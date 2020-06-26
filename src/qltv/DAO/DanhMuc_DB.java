/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.DAO;

import QLTV.Model.Danhmuc;
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
public class DanhMuc_DB {
    
    private static Danhmuc readFromResultSet(ResultSet rs) throws SQLException {
        String macd = rs.getString(1);
        String tencd = rs.getString(2);
        Danhmuc chude = new Danhmuc(macd, tencd);
        return chude;
    }

    public  void insert(Danhmuc s){
        String sql = "insert into danhmuc values(?,?)";
        jdbcHelper.executeUpdate(sql, 
                s.getMadm(),
                s.getTendm());
        
    }

    public  void update(Danhmuc s){
        String sql = "update danhmuc set tendm=? where madm=?";
        jdbcHelper.executeUpdate(sql, 
                s.getTendm(),
               
                s.getMadm());
        
    }

    public  void Delete(String madm){
        String sql = "delete danhmuc where madm = ?";
        jdbcHelper.executeUpdate(sql, madm);
    }
    
    public List<Danhmuc> select(){
        String sql = "select * from danhmuc order by tendm";
        return select(sql);
    }
    
    public List<Danhmuc> selectCDCS(){
        String sql = "select distinct danhmuc.madm, tendm  from danhmuc join book on book.madm = danhmuc.madm order by tendm";
        return select(sql);
    }
    
    
    private List<Danhmuc> select(String sql,Object... args){
        List<Danhmuc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql, args);
                while (rs.next()) {                    
                    Danhmuc s = readFromResultSet(rs);
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
