package android.login;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;

public class LoginCheckService {
	private DaoForAndroid dao = new DaoForAndroid();
	public String memberInfo(String key) throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			String returnStr= dao.checkToKey(conn, key);
			return returnStr;
		}
	}
}
