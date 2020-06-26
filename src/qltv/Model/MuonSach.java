package QLTV.Model;

import java.util.Date;

public class MuonSach {
	String mathe,tieude,tennguoimuon,tensachtra,ngaymuon,ngaytra;
	int sobanluu,soluongtra;
	
	public MuonSach(String mathe, String tennguoimuon, String tieude, String ngaymuon, int sobanluu, String tensachtra, String ngaytra, int soluongtra) {
		this.mathe = mathe;
		this.tennguoimuon = tennguoimuon;
		this.tieude = tieude;
		this.ngaymuon = ngaymuon;
		this.sobanluu = sobanluu;
		this.tensachtra = tensachtra;
		this.ngaytra = ngaytra;
		this.soluongtra = soluongtra;
		
	}
	
	public MuonSach() {
		
	}

	public String getMathe() {
		return mathe;
	}

	public void setMathe(String mathe) {
		this.mathe = mathe;
	}
	public String getTieude() {
		return tieude;
	}

	public void setTieude(String tieude) {
		this.tieude = tieude;
	}

	public String getTennguoimuon() {
		return tennguoimuon;
	}

	public void setTennguoimuon(String tennguoimuon) {
		this.tennguoimuon = tennguoimuon;
	}

	public String getTensachtra() {
		return tensachtra;
	}

	public void setTensachtra(String tensachtra) {
		this.tensachtra = tensachtra;
	}

	public int getSobanluu() {
		return sobanluu;
	}

	public void setSobanluu(int sobanluu) {
		this.sobanluu = sobanluu;
	}

	public String getNgaymuon() {
		return ngaymuon;
	}

	public void setNgaymuon(String ngaymuon) {
		this.ngaymuon = ngaymuon;
	}

	public String getNgaytra() {
		return ngaytra;
	}

	public void setNgaytra(String ngaytra) {
		this.ngaytra = ngaytra;
	}

	public int getSoluongtra() {
		return soluongtra;
	}

	public void setSoluongtra(int soluongtra) {
		this.soluongtra = soluongtra;
	}
	








	
	

	

}
