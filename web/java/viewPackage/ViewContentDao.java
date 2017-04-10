package viewPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JDBCUtil;

public class ViewContentDao {
	public ViewContent selectById(Connection conn, int no, String type) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from "+type+"iteminfo where "+type+"_no = ?"; 
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ViewContent viewContent = null;
			if(rs.next()){
				viewContent = convertViewContent(rs);
			}
			return viewContent;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private ViewContent convertViewContent(ResultSet rs)throws SQLException{
		return new ViewContent(rs.getInt(1), rs.getString(8), rs.getString(9), rs.getString(3), rs.getString(2), rs.getString(4),rs.getString(5),rs.getString(7), rs.getString(6),rs.getString(10),rs.getString(11));
	}
}
