package QLTV.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import QLTV.Model.NguoiDoc;
import QLTV.tien_ich.jdbcHelper;

public class NguoiDoc_DB {
    private  NguoiDoc readFromResultSet(ResultSet rs) throws SQLException {
        String MaNguoiDoc = rs.getString(1);
        String TaiKhoan = rs.getString(2);
        String MatKhau = rs.getString(3);
        String TenNguoiDoc = rs.getString(4);
        boolean GioiTinh = rs.getBoolean(5);
        
        int SoDT = rs.getInt(6);
        NguoiDoc nd = new NguoiDoc(MaNguoiDoc, TaiKhoan, MatKhau,TenNguoiDoc,GioiTinh,SoDT);
        return nd;
    }

    public  void insert(NguoiDoc s){
        String sql = "insert into nguoidoc values(?,?,?,?,?,?)";
        jdbcHelper.executeUpdate(sql, 
                s.getMaNguoiDoc(),
                s.getTaiKhoan(),
                s.getMatKhau(),
                s.getTenNguoiDoc(),
                s.isGioiTinh(),
                s.getSoDT());
    }

    public  void update(NguoiDoc s){
        String sql = "update nguoidoc set TaiKhoan=?,MatKhau=?,TenNguoiDoc=?,GioiTinh=?,SoDT=? where MaNguoiDoc=?";
        jdbcHelper.executeUpdate(sql, 
                s.getTaiKhoan(),
                s.getMatKhau(),
                s.getTenNguoiDoc(),
                s.isGioiTinh(),
                s.getSoDT(),
                s.getMaNguoiDoc());
    }

    public  void Delete(String MaNguoiDoc){
        String sql = "delete nguoidoc where MaNguoiDoc = ?";
        jdbcHelper.executeUpdate(sql, MaNguoiDoc);
    }
    
    public List<NguoiDoc> select(){
        String sql = "select * from NguoiDoc order by MaNguoiDoc";
        return select(sql);
    }
    private List<NguoiDoc> select(String sql,Object... args){
        List<NguoiDoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql, args);
                while (rs.next()) {                    
                    NguoiDoc s = readFromResultSet(rs);
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
