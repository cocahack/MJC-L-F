package viewPackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.ConnectionProvider;

public class ListArticleService {
	private ListDao listDao = new ListDao();
	private int size = 15;

	public ArticlePage getArticlePage(int pageNum, String type){
		try(Connection conn = ConnectionProvider.getConnection()){
			int total = listDao.selectCount(conn, type);
			List<ArticleList> content = listDao.select(conn, (pageNum-1)*size, size, type);
			return new ArticlePage(total, pageNum, size, content);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
