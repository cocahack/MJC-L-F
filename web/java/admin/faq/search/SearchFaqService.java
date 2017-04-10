package admin.faq.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import admin.faq.FaqDao;
import admin.faq.FaqList;
import admin.faq.FaqPage;
import jdbc.ConnectionProvider;

public class SearchFaqService {
	private FaqDao faqDao = new FaqDao();
	private int size = 15;
	public FaqPage getArticlePage(int pageNum, String keyword){
		try(Connection conn = ConnectionProvider.getConnection()){
			List<FaqList> content = faqDao.searchList(conn, keyword ,(pageNum-1)*size, size);
			int total = content.size();
			return new FaqPage(total, pageNum, size, content);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
