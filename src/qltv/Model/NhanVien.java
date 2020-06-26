/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.Model;

/**
 *
 * @author ASUS
 */
public class NhanVien {
	String manv, tennv, pass, taikhoan;
	boolean role;

	public NhanVien(String manv, String tennv, String taikhoan, String pass, boolean role ) {
		this.manv = manv;
		this.tennv = tennv;
		this.taikhoan = taikhoan;
		this.pass = pass;
		this.role = role;
		

	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public NhanVien() {

	}

	public String getManv() {
		return manv;
	}

	public void setManv(String manv) {
		this.manv = manv;
	}

	public String getTennv() {
		return tennv;
	}

	public void setTennv(String tennv) {
		this.tennv = tennv;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(String taikhoan) {
		this.taikhoan = taikhoan;
	}

}