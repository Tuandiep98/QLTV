package QLTV.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import QLTV.Model.MuonSach;
import QLTV.tien_ich.jdbcHelper;

public class MuonSach_DB {
	

	    private  MuonSach readFromResultSet(ResultSet rs) throws SQLException {    	
	    	 String mathe = rs.getString(1);
		        String tennguoimuon = rs.getString(2);
		        String tieude = rs.getString(3);
		        String ngaymuon = rs.getString(4);
		        int soluongmuon = rs.getInt(5);
		        String tensachtra = rs.getString(6);
		        String ngaytra = rs.getString(7);
		        int soluongtra = rs.getInt(8);
		        MuonSach ms = new MuonSach(mathe,tennguoimuon,tieude,ngaymuon,soluongmuon,tensachtra,ngaytra,soluongtra);
		        return ms;
	    }
	    public void insert(MuonSach s) {
	        String sql = "insert into muonsach values(?,?,?,?,?,?,?,?)";
	        jdbcHelper.executeUpdate(sql,
	                s.getMathe(),
	                s.getTennguoimuon(),
	                s.getTieude(),
	                s.getNgaymuon(),
	                s.getSobanluu(),
	                s.getTensachtra(),
	                s.getNgaytra(),
	                s.getSoluongtra());
	             

	    }
	public void update(MuonSach s) {
		String sql = "update muonsach set tennguoimuon=?, tieude=?, ngaymuon=?, sobanluu=?, tieudetra=?, ngaytra=?, soluongtra=? where mathe=?";
		jdbcHelper.executeUpdate(sql, s.getTennguoimuon(), s.getTieude(), s.getNgaymuon(), s.getSobanluu(),
				s.getTensachtra(), s.getNgaytra(), s.getSoluongtra(), s.getMathe());
	}

    public  void Delete(String mathe){
        String sql = "delete muonsach where mathe = ?";
        jdbcHelper.executeUpdate(sql, mathe);
    }

	public List<MuonSach> select() {
		String sql = "select * from muonsach";
		return select(sql);
	}

	private List<MuonSach> select(String sql, Object... args) {
		List<MuonSach> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			try {
				rs = jdbcHelper.executeQuery(sql, args);
				while (rs.next()) {
					MuonSach s = readFromResultSet(rs);
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
