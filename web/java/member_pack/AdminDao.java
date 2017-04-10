package member_pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JDBCUtil;
import member_pack.admin.Admin;

public class AdminDao {
	public Admin selectById(Connection conn, String id) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select * from admin where admin_id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			Admin admin = null;
			if(rs.next()){
				admin = new Admin(rs.getString(1), rs.getString(2),rs.getString(3));
			}
			return admin;
		}finally{
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
}
