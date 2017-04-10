package android.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import android.DaoForAndroid;
import android.list.ItemList;
import android.list.ListPage;
import jdbc.ConnectionProvider;

public class SearchContentService {
	private DaoForAndroid dao = new DaoForAndroid();
	int size = 9;
	public ListPage getListFromKeyword(String type,int pageNo, String keyword)	{
		try(Connection conn = ConnectionProvider.getConnection())
		{
			List<ItemList> itemList = dao.searchFromKey(conn, (pageNo-1)*size, size, type,keyword);
			int total = itemList.size();
			return new ListPage(total, pageNo, size, itemList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
