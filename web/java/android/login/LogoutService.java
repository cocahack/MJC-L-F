package android.login;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class LogoutService {
	private DaoForAndroid dao = new DaoForAndroid();
	public boolean deleteService(String key){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			boolean deleteResult = dao.deleteKey(conn, key);
			if(deleteResult)
				conn.commit();
			return deleteResult;
		} catch (SQLException e) {
			return false;
		}finally {
			JDBCUtil.close(conn);
		}
	}
}
