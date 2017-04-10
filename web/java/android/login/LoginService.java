package android.login;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class LoginService {
	private DaoForAndroid daoForAndroid = new DaoForAndroid();
	public String doLogin(String id, String pwd)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			String key = daoForAndroid.matchingIdPwd(conn, id, pwd);
			return key;
		}
	}
	public String doLoginAfterDeleteKey(String id,String pwd)throws SQLException{
		Connection conn = null;
		try{
			String key = "";
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			if(daoForAndroid.deleteFromId(conn, id)){
				conn.commit();
				key = daoForAndroid.matchingIdPwd(conn, id, pwd);
			}
			return key;
		}finally{
			JDBCUtil.close(conn);
		}
	}
}
