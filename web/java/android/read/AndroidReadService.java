package android.read;

import java.sql.Connection;
import java.sql.SQLException;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;

public class AndroidReadService {
	private DaoForAndroid dfa = new DaoForAndroid();
	public ItemRead getItemArticleById(int no, String type)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			ItemRead itemRead = dfa.getListSelectById(conn, no, type);
			return itemRead;
		}
	}
	public AdminReadItem getAdminItemArticleById(int no, String type)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			AdminReadItem adminReadItem = dfa.getAdminListSelectById(conn, no, type);
			return adminReadItem;
		}
	}
}
