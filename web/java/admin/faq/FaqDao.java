package admin.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import admin.faq.read.FaqContent;
import admin.faq.write.FaqWriteEle;
import jdbc.JDBCUtil;

public class FaqDao {
	public boolean insert(Connection conn,FaqWriteEle ele) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into faq(faq_title, faq_writer, faq_content, faq_imgPath, faq_regitDate,faq_hit)"
					+"values(?,?,?,?,?,?)");
			pstmt.setString(1,ele.getTitle());
			pstmt.setString(2, ele.getName());
			pstmt.setString(3,ele.getContent());
			pstmt.setString(4, ele.getFilePath());
			pstmt.setString(5, ele.getRegitDate());
			pstmt.setInt(6, 0);
			int doInsert = pstmt.executeUpdate();
			if(doInsert>0){
				return true;
			}else{
				return false;
			}
			
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(pstmt);
		}
	}
	public int selectLastNo(Connection conn)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("Select faq_no from faq order by faq_no desc limit 0,1");
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int selectCount(Connection conn)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("Select count(*) from faq");
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
			return 0;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public List<FaqList> select(Connection conn, int startRow, int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from faq order by faq_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<FaqList> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertList(rs));
			}
			return result;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	
	private FaqList convertList(ResultSet rs)throws SQLException{
		Timestamp temp = rs.getTimestamp(6);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateDate = df.format(temp);
		return new FaqList(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(7), updateDate);
	}
	public FaqList faqListSelectById(Connection conn, int faqNum)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from faq where faq_no = ?");
			pstmt.setInt(1, faqNum);
			rs = pstmt.executeQuery();
			FaqList faqList = null;
			if(rs.next()){
				faqList = convertList(rs);
			}
			return faqList;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public FaqContent faqContentSelectById(Connection conn,int faqNum)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from faq where faq_no = ?");
			pstmt.setInt(1, faqNum);
			rs = pstmt.executeQuery();
			FaqContent faqContent = null;
			if(rs.next()){
				faqContent = new FaqContent(rs.getString(4), rs.getString(5));
			}
			return faqContent;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public void increaseReadCount(Connection conn, int no) throws SQLException{
		try(PreparedStatement pstmt = conn.prepareStatement("update faq set faq_hit"
				+ " = faq_hit + 1 where faq_no = ?")){
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	public int update(Connection conn, int no, String title, String content, String filePaths)throws SQLException{
		if(filePaths==null){
			String query = "update faq set faq_title = ?, faq_content = ? where faq_no = ?";
			try(PreparedStatement pstmt = conn.prepareStatement(query)){
				pstmt.setString(1,title);
				pstmt.setString(2, content);
				pstmt.setInt(3, no);
				return pstmt.executeUpdate();
			}
		}else{
			String query = "update faq set faq_title = ?, faq_content = ?, faq_imgPath= ? where faq_no = ?";
			try(PreparedStatement pstmt = conn.prepareStatement(query)){
				pstmt.setString(1,title);
				pstmt.setString(2, content);
				pstmt.setString(3, filePaths);
				pstmt.setInt(4, no);
				return pstmt.executeUpdate();
			}
		}	
	}
	public int deleteById(Connection conn,int no)throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from faq where faq_no = ?");
			pstmt.setInt(1, no);
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			return 0;
		}finally {
			JDBCUtil.close(pstmt);
		}
	}
	public List<FaqList> searchList(Connection conn, String keyword, int startRow, int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from faq where faq_title like ? order by faq_no desc limit ?,?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+keyword+"%");	
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<FaqList> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertList(rs));
			}
			return result;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public String getImgPath(Connection conn, int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select faq_imgPath from faq where faq_no = ?");
			pstmt.setInt(1, no);
			String imgPath = null;
			rs = pstmt.executeQuery();
			while(rs.next()){
				imgPath = rs.getString(1);
			}
			return imgPath;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
}
