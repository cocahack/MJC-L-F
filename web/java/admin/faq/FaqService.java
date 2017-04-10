package admin.faq;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import admin.faq.read.FaqContent;
import admin.faq.read.FaqData;
import admin.faq.write.FaqWriteEle;
import exception.NoticeContentNotFoundException;
import exception.NoticeListNotFoundException;
import exception.PermissionDeniedException;
import jdbc.ConnectionProvider;
import jdbc.JDBCUtil;

public class FaqService {
	private FaqDao faqDao = new FaqDao();
	private int size = 15;
	
	//List FAQ
	public FaqPage getFaqPage(int pageNum){
		try(Connection conn = ConnectionProvider.getConnection()){
			int total = faqDao.selectCount(conn);
			List<FaqList> content = faqDao.select(conn, (pageNum-1)*size,size);
			return new FaqPage(total,pageNum,size,content);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public int getNumberOfList(){
		try(Connection conn = ConnectionProvider.getConnection()){
			int numberOfList = faqDao.selectLastNo(conn);
			return numberOfList;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	//write FAQ
	public boolean putDB(FaqWriteEle ele){
		try(Connection conn = ConnectionProvider.getConnection()){
			boolean returnVal = faqDao.insert(conn,ele);
			return returnVal;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	//read FAQ
	public FaqData getFaq(int faqNum, boolean increaseReadCount){
		try(Connection conn = ConnectionProvider.getConnection()){
			FaqList faqList = faqDao.faqListSelectById(conn, faqNum);
			if(faqList == null){
				throw new NoticeListNotFoundException();
			}
			FaqContent faqContent = faqDao.faqContentSelectById(conn, faqNum);
			if(faqContent == null){
				throw new NoticeContentNotFoundException();
			}
			if(increaseReadCount){
				faqDao.increaseReadCount(conn, faqNum);
			}
			return new FaqData(faqList, faqContent);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	//modify FAQ
	public void modify(int no, String title, String content, String filePaths ){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			faqDao.update(conn,no, title, content, filePaths );
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
	
	//delete FAQ
	public boolean delete(String servletPath, int no)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			conn.setAutoCommit(false);

			String[] filePaths = faqDao.faqContentSelectById(conn, no).getImgPath().split(";");
			boolean isFiledeleted = deleteFile(filePaths, servletPath, no);
			if(!isFiledeleted){
				return false;
			}
			int result = faqDao.deleteById(conn, no);
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
		file = new File(servletPath+"/uploadImg/faq/faq_No."+no);
		boolean m_isDeleted = file.delete();
		if(m_isDeleted){
			return true;
		}else{
			return false;
		}
	}
	public String getImgPath(int no) throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			String imgPath = faqDao.getImgPath(conn, no);
			return imgPath;
		}
	}
}
