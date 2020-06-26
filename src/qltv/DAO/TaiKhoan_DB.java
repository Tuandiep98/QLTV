package QLTV.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QLTV.Model.NhanVien;
import QLTV.tien_ich.jdbcHelper;

public class TaiKhoan_DB {
	public static NhanVien users = null;
    private  NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        String MaNhanVien = rs.getString(1);
        String TaiKhoan = rs.getString(2);
        String MatKhau = rs.getString(3);
        String TenNhanVien = rs.getString(4);
        boolean role = rs.getBoolean(5);
        NhanVien nv = new NhanVien(MaNhanVien, TaiKhoan, MatKhau,TenNhanVien, role);
        return nv;
    }
    public  void update(NhanVien s){
        String sql = "update taikhoan set  matkhau=? where taikhoan=?";
        jdbcHelper.executeUpdate(sql,
                s.getPass(),
                s.getTaikhoan());
        
    }
    public NhanVien findById(String taikhoan) {
        String sql = "SELECT * FROM taikhoan WHERE TaiKhoan=?";
        List<NhanVien> list = select(sql, taikhoan);
        return list.size() > 0 ? list.get(0) : null;
    }

  
    public List<NhanVien> select(){
        String sql = "select * from taikhoan where Role = '1'";
        return select(sql);
    }
    private List<NhanVien> select(String sql,Object... args){
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql, args);
                while (rs.next()) {                    
                    NhanVien s = readFromResultSet(rs);
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
