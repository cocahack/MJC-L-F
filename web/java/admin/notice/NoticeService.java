package admin.notice;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import admin.notice.modify.ModifyNoticeRequest;
import admin.notice.modify.ModifyNoticeValues;
import admin.notice.read.NoticeContent;
import admin.notice.read.NoticeData;
import admin.notice.write.NoticeWriteEle;
import exception.ArticleListNotFoundException;
import exception.NoticeContentNotFoundException;
import exception.NoticeListNotFoundException;
import exception.PermissionDeniedException;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class NoticeService {
	private NoticeDao noticeDao = new NoticeDao();
	private int size = 15;
	
	// list service
	public NoticePage getNoticePage(int pageNum){
		try(Connection conn = ConnectionProvider.getConnection()){
			int total = noticeDao.selectCount(conn);
			List<NoticeList> content = noticeDao.select(conn,(pageNum-1)*size,size);
			return new NoticePage(total,pageNum,size,content);
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	// read service
	public NoticeData getNotice(int noticeNum, boolean increaseReadCount){
		try(Connection conn = ConnectionProvider.getConnection()){
			NoticeList noticeList = noticeDao.noticeListSelectById(conn, noticeNum);
			if(noticeList == null){
				throw new NoticeListNotFoundException();
			}
			NoticeContent noticeContent = noticeDao.noticeContentSelectById(conn, noticeNum);
			if(noticeContent == null){
				throw new NoticeContentNotFoundException();
			}
			if(increaseReadCount){
				noticeDao.increaseReadCount(conn, noticeNum);
			}
			return new NoticeData(noticeList, noticeContent);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	// write service
	public int getNumberOfList(){
		try(Connection conn = ConnectionProvider.getConnection()){
			int numberOfList = noticeDao.selectLastNo(conn);
			return numberOfList;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	public boolean putDB(NoticeWriteEle ele){
		try(Connection conn = ConnectionProvider.getConnection()){
			boolean returnVal = noticeDao.insert(conn,ele);
			return returnVal;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	public NoticeList select(int no)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			NoticeList list = noticeDao.selectById(conn, no);
			return list;
		}
	}
	public void modify(int no, String title, String content, String filePaths ){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			noticeDao.update(conn,no, title, content, filePaths );
			conn.commit();

		}
		catch(SQLException e){
			JDBCUtil.rollback(conn);
			throw new RuntimeException(e);
		}catch (PermissionDeniedException e) {
			// TODO: handle exception
			throw e;
		}
			finally {
				JDBCUtil.close(conn);
		}
	}
	
	public boolean delete(String servletPath, int no)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			conn.setAutoCommit(false);

			String[] filePaths = noticeDao.noticeContentSelectById(conn, no).getImgPath().split(";");
			boolean isFiledeleted = deleteFile(filePaths, servletPath, no);
			if(!isFiledeleted){
				return false;
			}
			int result = noticeDao.deleteById(conn, no);
			if(result>0){
				conn.commit();
				return true;
			}else{
				conn.rollback();
				return false;
			}
		}
	}
	private boolean deleteFile(String[] filePaths, String servletPath, int no){
		File file;
		for(int i=0;i<filePaths.length;i++){
			file = new File(servletPath+filePaths[i]);
			file.delete();
		}
		file = new File(servletPath+"/uploadImg/notice/notice_No."+no);
		boolean m_isDeleted = file.delete();
		if(m_isDeleted){
			return true;
		}else{
			return false;
		}
	}
	public String getImgPath(int no) throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			String imgPath = noticeDao.getImgPath(conn, no);
			return imgPath;
		}
	}
}
