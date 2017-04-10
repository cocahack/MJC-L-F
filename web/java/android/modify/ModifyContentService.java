package android.modify;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import android.read.ItemRead;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class ModifyContentService
{
	private DaoForAndroid dao = new DaoForAndroid();
	public ItemRead getContentById(String type,int no) throws SQLException
	{
		try(Connection conn=ConnectionProvider.getConnection())
		{
			ItemRead ir = dao.getListSelectById(conn, no, type);
			return ir;
		}
	}
	public boolean checkToService(String stnum,String type,int no) throws SQLException
	{
		try(Connection conn=ConnectionProvider.getConnection())
		{
			return dao.matchingWithStnum(conn, stnum, type, no);
		}
	}
	public int modifyService(String type, String[] data){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			int resultRow = dao.modifyData(conn, type, data);
			if(resultRow>0)
				conn.commit();
			return resultRow;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
		finally {
			JDBCUtil.close(conn);
		}
	}
}
