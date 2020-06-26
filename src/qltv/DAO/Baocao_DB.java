/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.DAO;

import QLTV.Model.Danhmuc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import QLTV.tien_ich.jdbcHelper;

/**
 *
 * @author Admin
 */
public class Baocao_DB {

    public List<Vector> selectNamXB() {
        String sql = "select distinct  namxb, count(namxb) from book\n"
                + "group by namxb";
        List<Vector> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Vector row = new Vector();
                    row.add(rs.getString(1));
                    row.add(rs.getString(2));
                    list.add(row);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Vector> selectChude() {
        String sql = "select distinct  tendm, count(book.masach)  from book right join danhmuc on book.madm = danhmuc.madm\n"
                + "group by tendm";
        List<Vector> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Vector row = new Vector();
                    row.add(rs.getString(1));
                    row.add(rs.getString(2));
                    list.add(row);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Vector> selectBanDT() {
        String sql = "SELECT masach,tieude,tacgia,namxb from book where BANDIENTU = 1";
        List<Vector> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Vector row = new Vector();
                    row.add(rs.getString(1));
                    row.add(rs.getString(2));
                    row.add(rs.getString(3));
                    row.add(rs.getString(4));
                   
                    list.add(row);
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
