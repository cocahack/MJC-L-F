package viewPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jdbc.JDBCUtil;
import viewPackage.modify.ModifyValues;

public class ListDao {
	public int selectCount(Connection conn,String type)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select count(*) from " + type+"iteminfo";
		try {
			pstmt = conn.prepareStatement(query);
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
	public List<ArticleList> select(Connection conn, int startRow, int size, String type) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "+type+"iteminfo order by "+type+ "_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<ArticleList> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private ArticleList convertArticle(ResultSet rs)throws SQLException{
		Timestamp temp = rs.getTimestamp(12);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateDate = df.format(temp);
		return new ArticleList(rs.getInt(1), rs.getString(8), rs.getString(9), rs.getString(6), updateDate, new Writer(rs.getString(2), rs.getString(3)));
	}

	public ArticleList selectById(Connection conn, int no, String type) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from "+type+"iteminfo where "+type+"_no = ?"; 
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ArticleList articleList = null;
			while(rs.next()){
				articleList = convertArticle(rs);
			}
			return articleList;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public ArrayList<ArticleList> searchArticle(Connection conn, SearchData sd, String type, int startRow, int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from "+type+"iteminfo where name like ? AND st_num like ? AND "
		+type+"_classify like ? AND "+type+"_classifyDetail like ? AND "+ type + "_date BETWEEN ? AND ? AND "
		+type+"_updatetime BETWEEN ? AND ?"+
					"order by "+type+ "_no desc limit ?,?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+sd.getName()+"%");	
			pstmt.setString(2, "%"+sd.getStnum()+"%");
			pstmt.setString(3, "%"+sd.getClassify()+"%");
			pstmt.setString(4, "%"+sd.getDetail()+"%");
			pstmt.setString(5, sd.getStartEventDate());
			pstmt.setString(6, sd.getEndEventDate());
			pstmt.setString(7, sd.getStartRegitDate());
			pstmt.setString(8, sd.getEndRegitDate());
			pstmt.setInt(9, startRow);
			pstmt.setInt(10, size);
			rs = pstmt.executeQuery();
			ArrayList<ArticleList> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int resultRowsForDetail(Connection conn, SearchData sd, String type) throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select count(*) from "+type+"iteminfo where name like ? AND st_num like ? AND "
		+type+"_classify like ? AND "+type+"_classifyDetail like ? AND "+ type + "_date BETWEEN ? AND ? AND "
		+type+"_updatetime BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+sd.getName()+"%");	
			pstmt.setString(2, "%"+sd.getStnum()+"%");
			pstmt.setString(3, "%"+sd.getClassify()+"%");
			pstmt.setString(4, "%"+sd.getDetail()+"%");
			pstmt.setString(5, sd.getStartEventDate());
			pstmt.setString(6, sd.getEndEventDate());
			pstmt.setString(7, sd.getStartRegitDate());
			pstmt.setString(8, sd.getEndRegitDate());
			rs = pstmt.executeQuery();
			int total = 0;
			while(rs.next()){
				total = rs.getInt(1);
			}
			return total;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public List<ArticleList> searchArticle(Connection conn, String keyword, String type, int startRow, int size)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from "+type+"iteminfo where "+type+"_classifyDetail like ? "+"order by "+type+ "_no desc limit ?,?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+keyword+"%");	
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<ArticleList> result = new ArrayList<>();
			while(rs.next()){
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int resultRowsForNormal(Connection conn, String keyword, String type)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select count(*) from "+type+"iteminfo where "+type+"_classifyDetail like ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+keyword+"%");	
			rs = pstmt.executeQuery();
			int result = 0;
			while(rs.next()){
				result = rs.getInt(1);
			}
			return result;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	/**
	 * @param conn
	 * @param no
	 * @param title
	 * @param type
	 * @param mv
	 * @return
	 * @throws SQLException
	 */
	public int update(Connection conn, int no, String title, String type, ModifyValues mv) throws SQLException{
			
			String query = "update "+type+"iteminfo set ";
			String setValues = generateQuery(mv, type);
			query+=setValues+ " where "+type+"_no = ?;";
			try(PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setInt(1, no);
//			pstmt.setString(2, title);
			return pstmt.executeUpdate();
		} 
	}
	private String generateQuery(ModifyValues mv,String type){
		final String[] DBARRAY= {"phone","kakao","place","classify","classifyDetail","imgPath","date","more"};
		String[] queryValues = {"phone='"+mv.getPhone()+"', ","kakao='"+mv.getKakao()+"', "
				,type+"_place='"+mv.getPlace()+"', ",type+"_classify='"+mv.getClassify()+"', "
				,type+"_classifyDetail='"+mv.getDetail()+"', ",type+"_imgPath='"+mv.getImgPath()+"', "
				,type+"_date='"+mv.getEventDate()+"', ",type+"_more='"+mv.getExplain()+"'"};
		if(mv.getPhone()==null||mv.getPhone()==""){
			queryValues[0]="";
		}
		if(mv.getKakao()==null||mv.getKakao()==""){
			queryValues[1]="";
		}
		if(mv.getImgPath()==null||mv.getImgPath()==""){
			queryValues[5]="";
		}
		if(mv.getEventDate()==null||mv.getEventDate()==""){
			queryValues[6]="";
		}
		if(mv.getExplain()==null||mv.getExplain()==""){
			queryValues[7]="";
		}
		String query = "";
		for(int i=0;i<queryValues.length;i++){
			query+=queryValues[i];
		}
		return query;
	}
	public boolean deleteById(Connection conn, String type, int no){
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from "+type+"iteminfo where "+type+"_no = ?");
			pstmt.setInt(1, no);
			int result = pstmt.executeUpdate();
			if(result>0){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			return false;
		}
		finally {
			JDBCUtil.close(pstmt);
		}
	}
}