/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.DAO;

import QLTV.Model.Sach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import QLTV.tien_ich.jdbcHelper;

/**
 *
 * @author Admin
 */
public class Sach_DB {

    private Sach readFromResultSet(ResultSet rs) throws SQLException {
        String masach = rs.getString(1);
        String madm = rs.getString(2);
        String manxb = rs.getString(3);
        String tieude = rs.getString(4);
        String tacgia = rs.getString(5);
        int namxb = rs.getInt(6);
        int sotrang = rs.getInt(7);
        double gia = rs.getDouble(8);
        int sobanluu = rs.getInt(9);
        int phienban = rs.getInt(10);
        int bandientu = rs.getInt(11);
        int muonvenha = rs.getInt(12);

        Sach sach = new Sach(masach, madm, manxb, tieude, tacgia, namxb, sotrang, sobanluu, phienban, bandientu, muonvenha, gia);
        return sach;
    }
    public void insert(Sach s) {
        String sql = "insert into book values(?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcHelper.executeUpdate(sql,
                s.getMasach(),
                s.getMadm(),
                s.getManxb(),
                s.getTiede(),
                s.getTacgia(),
                s.getNamxb(),
                s.getSotrang(),
                s.getGia(),
                s.getSobanluu(),
                s.getPhienban(),
                s.getBandientu(),
                s.getMuonvenha());

    }

    public void update(Sach s) {
        String sql = "update book set madm=?, manxb=?, tieude=?, tacgia=?, namxb=?, sotrang=?,"
                + "gia=?, sobanluu=?, phienban=?, bandientu=?, muonvenha=? where masach=?";
        jdbcHelper.executeUpdate(sql,
                s.getMadm(),
                s.getManxb(),
                s.getTiede(),
                s.getTacgia(),
                s.getNamxb(),
                s.getSotrang(),
                s.getGia(),
                s.getSobanluu(),
                s.getPhienban(),
                s.getBandientu(),
                s.getMuonvenha(),
                s.getMasach());

    }

    public void Delete(String masach) {
        int chose = JOptionPane.showConfirmDialog(null, "bạn có muốn xóa không ?", "hệ thống quản lý sách",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (chose == JOptionPane.YES_OPTION) {
            String sql = "delete book where masach = ?";
            jdbcHelper.executeUpdate(sql, masach);
        }

    }

    public List<Sach> select() {
        String sql = "select * from book";
        return select(sql);
    }
    
    public List<Sach> selectDanhmuc(String madm){
        String sql = "select * from book where madm=?";
        return select(sql,madm);
    }

    private List<Sach> select(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Sach s = readFromResultSet(rs);
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
