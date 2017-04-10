package android.delete;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class DeleteContentService {
	private DaoForAndroid dao = new DaoForAndroid();
	public String deleteContent(String type, String stnum, int no, String servletPath)throws SQLException
	{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			int resultRow = dao.deleteByNo(conn, type, stnum, no, servletPath);
			if(resultRow==-2)
				return "ACCESS_DENY";
			else if(resultRow<1)
				return "INTERNAL_ERROR";
			else return "CONTENT_DELETED";
		} finally {
			JDBCUtil.close(conn);
		}
	}
}
