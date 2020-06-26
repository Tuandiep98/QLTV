/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.DAO;

import QLTV.Model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import QLTV.tien_ich.jdbcHelper;
import QLTV.tien_ich.dialoghelp;

/**
 *
 * @author ASUS
 */
public class NhanVien_DB {
	public static NhanVien users = null;

	private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
		String MaNhanVien = rs.getString(1);
		String TenNhanVien = rs.getString(2);
		String TaiKhoan = rs.getString(3);
		String MatKhau = rs.getString(4);
		boolean role = rs.getBoolean(5);
		NhanVien nv = new NhanVien(MaNhanVien,TenNhanVien,TaiKhoan,MatKhau,role);
		return nv;
	}

	public void insert(NhanVien s) {
		String sql = "insert into  taikhoan (manv,hoten,taikhoan,matkhau,role) values(?,?,?,?,?)";
		jdbcHelper.executeUpdate(sql, s.getManv(), s.getTennv(), s.getTaikhoan(), s.getPass(),s.isRole());
	}

	public void update(NhanVien s) {
		String sql = "update taikhoan set hoten=?,taikhoan=?,matkhau=?, role=? where manv=?";
		jdbcHelper.executeUpdate(sql, s.getTennv(), s.getTaikhoan(), s.getPass(), s.isRole(), s.getManv());
	}

	public void Delete(String manv) {
		String sql = "delete taikhoan where manv = ?";
		jdbcHelper.executeUpdate(sql, manv);
	}

	public NhanVien findById(String taikhoan) {
		String sql = "SELECT * FROM taikhoan WHERE taikhoan=?";
		List<NhanVien> list = select(sql, taikhoan);
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<NhanVien> select() {
		String sql = "select * from taikhoan where role = '0'";
		return select(sql);
	}

	private List<NhanVien> select(String sql, Object... args) {
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
