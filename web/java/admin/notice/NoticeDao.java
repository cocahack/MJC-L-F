package admin.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import admin.notice.read.NoticeContent;
import admin.notice.write.NoticeWriteEle;
import jdbc.JDBCUtil;

public class NoticeDao {
	public int selectLastNo(Connection conn)throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("Select notice_no from notice order by notice_no desc limit 0,1");
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
			pstmt = conn.prepareStatement("Select count(*) from notice");
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
	public List<NoticeList> select(Connection conn, int startRow, int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from notice order by notice_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<NoticeList> result = new ArrayList<>();
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
	public NoticeList noticeListSelectById(Connection conn, int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from notice where notice_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			NoticeList noticeList = null;
			if(rs.next()){
				noticeList = convertList(rs);
			}
			return noticeList;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
		
	}
	public void increaseReadCount(Connection conn, int no) throws SQLException{
		try(PreparedStatement pstmt = conn.prepareStatement("update notice set notice_hit"
				+ " = notice_hit + 1 where notice_no = ?")){
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	public NoticeContent noticeContentSelectById(Connection conn, int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from notice where notice_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			NoticeContent noticeContent = null;
			if(rs.next()){
				noticeContent = new NoticeContent(rs.getString(4), rs.getString(5));
			}
			return noticeContent;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
		
	}
	private NoticeList convertList(ResultSet rs)throws SQLException{
		Timestamp temp = rs.getTimestamp(6);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateDate = df.format(temp);
		return new NoticeList(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(7), updateDate);
	}
	public boolean insert(Connection conn,NoticeWriteEle ele) throws SQLException{
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into notice(notice_title, notice_writer, notice_content, notice_imgPath, notice_regitDate,notice_hit)"
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
	public NoticeList selectById(Connection conn, int no)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt  = conn.prepareStatement("select * from notice where notice_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			NoticeList noticeList = null;
			while(rs.next()){
				noticeList = convertList(rs);
			}
			return noticeList;
		}finally{
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int update(Connection conn, int no, String title, String content, String filePaths)throws SQLException{
		if(filePaths==null){
			String query = "update notice set notice_title = ?, notice_content = ? where notice_no = ?";
			try(PreparedStatement pstmt = conn.prepareStatement(query)){
				pstmt.setString(1,title);
				pstmt.setString(2, content);
				pstmt.setInt(3, no);
				return pstmt.executeUpdate();
			}
		}else{
			String query = "update notice set notice_title = ?, notice_content = ?, notice_imgPath= ? where notice_no = ?";
			try(PreparedStatement pstmt = conn.prepareStatement(query)){
				pstmt.setString(1,title);
				pstmt.setString(2, content);
				pstmt.setString(3, filePaths);
				pstmt.setInt(4, no);
				return pstmt.executeUpdate();
			}
		}
	}
	public int deleteById(Connection conn, int no){
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from notice where notice_no = ?");
			pstmt.setInt(1, no);
			int result = pstmt.executeUpdate();
			return result;
		}catch(SQLException e){
			return 0;
		} finally {
			JDBCUtil.close(pstmt);
		}
	}
	public List<NoticeList> searchList(Connection conn, String keyword, int startRow, int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from notice where notice_title like ? order by notice_no desc limit ?,?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+keyword+"%");	
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<NoticeList> result = new ArrayList<>();
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
			pstmt = conn.prepareStatement("select notice_imgPath from notice where notice_no = ?");
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