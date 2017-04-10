package android.list;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import android.DaoForAndroid;
import jdbc.ConnectionProvider;

public class GenerateListService {
	private DaoForAndroid dfa = new DaoForAndroid();
	private int size = 15;
	public ListPage itemListService(int pageNum, String type){
		try(Connection conn = ConnectionProvider.getConnection()){
			int total = dfa.selectCountOfItemList(conn, type);
			List<ItemList> itemList = dfa.getItemList(conn, (pageNum-1)*size, size, type);
			return new ListPage(total, pageNum, size, itemList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public ListPage adminItemListService(int pageNum, String type){
		try(Connection conn = ConnectionProvider.getConnection()){
			int total = 0;
			if(type.equals("notice")){
				total = dfa.selectCountOfNoticeList(conn);
			}
			if(type.equals("faq")){
				total = dfa.selectCountOfFaqList(conn);
			}
			List<AdminMenuList> itemList = dfa.getAdminItemList(conn, (pageNum-1)*size, size, type);
			return new ListPage(total, pageNum, size, itemList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
