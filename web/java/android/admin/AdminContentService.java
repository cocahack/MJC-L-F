package android.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;

public class AdminContentService {
	private DaoForAndroid dao = new DaoForAndroid();
	private int size = 15;
	public ArrayList<AdminContents> getContents(String type, int pageNo) throws SQLException{
		ArrayList<AdminContents> list = new ArrayList<AdminContents>();
		try(Connection conn = ConnectionProvider.getConnection()){
			list = dao.getNoticeListByType(conn, type, (pageNo-1)*size, size);
		}
		return list;
	}
}
