package viewPackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.ConnectionProvider;

public class SearchArticleService {
	private ListDao listDao = new ListDao();
	private int size = 15;

	public ArticlePage getArticlePage(int pageNum, String type, SearchData sd){
		try(Connection conn = ConnectionProvider.getConnection()){
			ArrayList<ArticleList> content = listDao.searchArticle(conn, sd , type,(pageNum-1)*size, size);
			int total = listDao.resultRowsForDetail(conn, sd, type);
			return new ArticlePage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public ArticlePage getArticlePage(int pageNum, String type, String keyword){
		try(Connection conn = ConnectionProvider.getConnection()){
			List<ArticleList> content = listDao.searchArticle(conn, keyword , type,(pageNum-1)*size, size);
			int total = listDao.resultRowsForNormal(conn, keyword, type);
			return new ArticlePage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
