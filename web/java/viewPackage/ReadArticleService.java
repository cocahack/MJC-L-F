package viewPackage;

import java.sql.Connection;
import java.sql.SQLException;

import exception.ArticleListNotFoundException;
import exception.ViewContentNotFoundException;
import jdbc.ConnectionProvider;

public class ReadArticleService {
	private ListDao listDao = new ListDao();
	private ViewContentDao viewContentDao = new ViewContentDao();
	
	public ArticleData getArticle(int articleNum, String type){
		try(Connection conn = ConnectionProvider.getConnection()) {
			ArticleList articleList = listDao.selectById(conn, articleNum, type);
			if(articleList==null){
				throw new ArticleListNotFoundException();
			}
			ViewContent viewContent = viewContentDao.selectById(conn, articleNum, type);
			if(viewContent==null){
				throw new ViewContentNotFoundException();
			}
			return new ArticleData(articleList,viewContent);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
}
