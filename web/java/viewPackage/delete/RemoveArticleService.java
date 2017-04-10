package viewPackage.delete;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import viewPackage.ArticleList;
import viewPackage.ListDao;

public class RemoveArticleService {
	private ListDao listDao = new ListDao();
	public boolean removeReq(String type, int no)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			conn.setAutoCommit(false);
			
			boolean isDeleted = listDao.deleteById(conn, type, no);
			if(isDeleted){
				conn.commit();
				return true;
			}else{
				conn.rollback();
				return false;
			}
		}
	}
	public ArticleList select(String type, int no)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			ArticleList articleList = listDao.selectById(conn, no, type);
			return articleList;
		}
	}
}
