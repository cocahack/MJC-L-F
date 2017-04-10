package mainList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import viewPackage.ArticleList;
import viewPackage.Writer;


public class SelectDao {
	private ListValues convertList(ResultSet rs) throws SQLException{
		Timestamp temp = rs.getTimestamp(12);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = df.format(temp);
		return new ListValues(rs.getString(8), rs.getString(9),updateDate,rs.getInt(1));
	}
	public List<ListValues> extractList(Connection conn,String type)throws SQLException{
		ResultSet rs = null;
		try(PreparedStatement pstmt = conn.prepareStatement("select * from "+type+"iteminfo order by "+type+ "_no desc limit 0,8 ")){
			rs = pstmt.executeQuery();
			List<ListValues> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertList(rs));
			}
			return result;
		}
	}
	public List<AdminValues> extractAdminList(Connection conn,String type)throws SQLException{
		ResultSet rs = null;
		try(PreparedStatement pstmt = conn.prepareStatement("select * from "+type+" order by "+type+ "_no desc limit 0,8 ")){
			rs = pstmt.executeQuery();
			List<AdminValues> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertAdminList(rs));
			}
			return result;
		}
	}
	private AdminValues convertAdminList(ResultSet rs) throws SQLException{
		Timestamp temp = rs.getTimestamp(6);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = df.format(temp);
		return new AdminValues(rs.getInt(1),rs.getString(2),updateDate);
	}
}
