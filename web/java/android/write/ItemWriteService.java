package android.write;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class ItemWriteService  {
	DaoForAndroid dao = new DaoForAndroid();
	public int getLastNo(String type) throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			return dao.tableLastNo(conn, type);
		}
	}
	public int updateDataService(String type, String[] data)throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			int resultRow = dao.updateDB(conn, type, data);
			if(resultRow>0){
				conn.commit();
				return resultRow;
			}else{
				return 0;
			}
		} finally {
			JDBCUtil.close(conn);
		}
	}
}

