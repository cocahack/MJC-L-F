package member_pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JDBCUtil;
import member_pack.admin.Admin;

public class MemberDao {
	public Member selectById(Connection conn, String id) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("select * from student where st_num = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			Member member = null;
			if(rs.next()){
				member = new Member(rs.getString(3), rs.getString(2),rs.getString(1));
			}
			return member;
		}finally{
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	} 
}
