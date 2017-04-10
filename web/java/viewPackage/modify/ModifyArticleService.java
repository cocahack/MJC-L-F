package viewPackage.modify;

import java.sql.Connection;
import java.sql.SQLException;

import exception.ArticleListNotFoundException;
import exception.PermissionDeniedException;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;
import viewPackage.ArticleList;
import viewPackage.ListDao;

public class ModifyArticleService {
	private ListDao listDao = new ListDao();
	
	public void modify(ModifyRequest modReq,String type, ModifyValues mv){
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			ArticleList articleList = listDao.selectById(conn, modReq.getArticleNumber(), type);
			if(articleList==null){
				throw new ArticleListNotFoundException();
			}
			if(!canModify(modReq.getSt_num(), articleList)){
				throw new PermissionDeniedException();
			}
			listDao.update(conn, modReq.getArticleNumber(), modReq.getTitle(), type, mv);
			conn.commit();
		}catch(SQLException e){
			JDBCUtil.rollback(conn);
			throw new RuntimeException(e);
		}catch(PermissionDeniedException e){
			JDBCUtil.rollback(conn);
			throw e;
		}finally {
			JDBCUtil.close(conn);
		}
	}
	public boolean canModify(String modifyingSt_num, ArticleList articleList){
		return articleList.getWriter().getStnum().equals(modifyingSt_num);
	}
	public ArticleList select(String type,int no)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			ArticleList list = listDao.selectById(conn, no, type);
			return list;
		}
	}
}
