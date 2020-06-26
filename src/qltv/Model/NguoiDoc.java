package QLTV.Model;

public class NguoiDoc {
	String MaNguoiDoc,TaiKhoan,MatKhau,TenNguoiDoc;
	int SoDT;
	boolean GioiTinh;
	
	public NguoiDoc(String MaNguoiDoc,String TaiKhoan,String MatKhau,String TenNguoiDoc,boolean GioiTinh,int SoDT) {
		this.MaNguoiDoc = MaNguoiDoc;
		this.TaiKhoan = TaiKhoan;
		this.MatKhau = MatKhau;
		this.TenNguoiDoc = TenNguoiDoc;
		this.GioiTinh = GioiTinh;
		this.SoDT = SoDT;
	}

	public String getMaNguoiDoc() {
		return MaNguoiDoc;
	}

	public void setMaNguoiDoc(String maNguoiDoc) {
		MaNguoiDoc = maNguoiDoc;
	}

	public String getTaiKhoan() {
		return TaiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		TaiKhoan = taiKhoan;
	}

	public String getMatKhau() {
		return MatKhau;
	}

	public void setMatKhau(String matKhau) {
		MatKhau = matKhau;
	}

	public String getTenNguoiDoc() {
		return TenNguoiDoc;
	}

	public void setTenNguoiDoc(String tenNguoiDoc) {
		TenNguoiDoc = tenNguoiDoc;
	}

//	public int getGioiTinh() {
//		return GioiTinh;
//	}
//
//	public void setGioiTinh(int gioiTinh) {
//		GioiTinh = gioiTinh;
//	}

	public int getSoDT() {
		return SoDT;
	}

	public boolean isGioiTinh() {
		return GioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		GioiTinh = gioiTinh;
	}

	public void setSoDT(int soDT) {
		SoDT = soDT;
	}
	


}
