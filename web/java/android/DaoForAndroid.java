package android;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import android.admin.AdminContents;
import android.list.AdminMenuList;
import android.list.ItemList;
import android.read.AdminReadItem;
import android.read.ItemRead;
import jdbc.JDBCUtil;

public class DaoForAndroid {
	public int selectCountOfItemList(Connection conn, String type) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select count(*) from "+type+"iteminfo");
			rs = pstmt.executeQuery();
			int size = 0;
			while(rs.next()){
				size = rs.getInt(1);
			}
			return size;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int selectCountOfNoticeList(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select count(*) from notice");
			rs = pstmt.executeQuery();
			int size = 0;
			while(rs.next()){
				size = rs.getInt(1);
			}
			return size;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int selectCountOfFaqList(Connection conn) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select count(*) from faq");
			rs = pstmt.executeQuery();
			int size = 0;
			while(rs.next()){
				size = rs.getInt(1);
			}
			return size;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public List<ItemList> getItemList(Connection conn, int startRow, int size, String type) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "+type+"iteminfo order by "+type+"_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<ItemList> itemList = new ArrayList<ItemList>();
			while(rs.next())
				itemList.add(convertItemList(rs));
			return itemList;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public List<AdminMenuList> getAdminItemList(Connection conn, int startRow, int size, String type) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "+type+" order by "+type+"_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			List<AdminMenuList> itemList = new ArrayList<AdminMenuList>();
			while(rs.next())
				itemList.add(convertAdminItemList(rs));
			return itemList;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private AdminMenuList convertAdminItemList(ResultSet rs) throws SQLException{
		Timestamp temp = rs.getTimestamp(6);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String updateDate = df.format(temp);
		return new AdminMenuList(rs.getInt(1), rs.getString(2), updateDate, rs.getInt(7) );
	}
	private ItemList convertItemList(ResultSet rs) throws SQLException{
		Timestamp temp = rs.getTimestamp(12);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String updateDate = df.format(temp);
		return new ItemList(rs.getInt(1), rs.getString(8), rs.getString(9), rs.getString(6), updateDate);
	}
	public ItemRead getListSelectById(Connection conn, int no, String type)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "+type+"iteminfo where "+type+"_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ItemRead itemRead = null;
			if(rs.next()){
				itemRead = convertReadItem(rs);
			}
			return itemRead;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public AdminReadItem getAdminListSelectById(Connection conn, int no, String type)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "+type+" where "+type+"_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			AdminReadItem adminReadItem = null;
			if(rs.next()){
				adminReadItem = new AdminReadItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
			}
			return adminReadItem;
		} finally {
			// TODO: handle finally clause
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private ItemRead convertReadItem(ResultSet rs)throws SQLException{
		Timestamp temp = rs.getTimestamp(12);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateDate = df.format(temp);
		return new ItemRead(rs.getInt(1), rs.getString(8), rs.getString(9), rs.getString(3),rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(7),rs.getString(6),updateDate,rs.getString(10),rs.getString(11));
	}
	private AdminReadItem convertAdminReadItem(ResultSet rs)throws SQLException{
		Timestamp temp = rs.getTimestamp(6);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateDate = df.format(temp);
		return new AdminReadItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), updateDate, rs.getInt(7));
	}
	public String matchingIdPwd(Connection conn,String id,String pwd)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = "";
		try {
			pstmt = conn.prepareStatement("select count(*) from student where st_num=? AND st_pwd =?");
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			int result = 0;
			while(rs.next())
				result = rs.getInt(1);
			if(result==1){
				if(isExistKey(conn, id)){
					return "keyExist";
				}else{
					key = generateKey();
					keyInsert(conn, id, key);
				}
			}
			return key;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private boolean isExistKey(Connection conn,String id) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from androiduser where st_num = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString(2)!=null){
					return true;
				}
			}
			return false;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private void keyInsert(Connection conn, String id, String key) throws SQLException{
		PreparedStatement pstmt = null;
		conn.setAutoCommit(false);
		try {
			pstmt = conn.prepareStatement("insert into androiduser(st_num, userkey) values(?,?) ");
			pstmt.setString(1, id);
			pstmt.setString(2, key);
			int result = pstmt.executeUpdate();
			if(result!=0){
				conn.commit();
			}else{
				JDBCUtil.rollback(conn);
			}
		} finally {
			
			JDBCUtil.close(pstmt);
		}
	}
	private String generateKey(){
		RandomString rs = new RandomString(128);
		String key = rs.nextString();
		return key;
	}
	public String checkToKey(Connection conn, String key){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String st_num = getStnumFromKey(conn,key);
			if(st_num!=null){
				pstmt = conn.prepareStatement("select st_name from student where st_num = ? ");
				pstmt.setString(1, st_num);
				rs = pstmt.executeQuery();
				String name = null;
				while(rs.next()){
					name = rs.getString(1);
				}
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("st_num", st_num);
				jsonObject.put("st_name",name);
				String returnStr = jsonObject.toJSONString();
				return returnStr;
			}else{
				return null;
			}
		} catch(SQLException e){
			return null;
		}
		finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	private String getStnumFromKey(Connection conn, String key)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select st_num from androiduser where userkey = ?");
			pstmt.setString(1, key);
			rs = pstmt.executeQuery();
			String st_num = null;
			while(rs.next()){
				st_num = rs.getString(1);
			}
			return st_num;
		}
		finally{
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public boolean deleteKey(Connection conn, String key){
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from androiduser where userkey = ?");
			pstmt.setString(1, key);
			int resultRow = pstmt.executeUpdate();
			if(resultRow>0)
				return true;
			else return false;
		}catch(SQLException e){
			return false;
		}	
		finally {
			JDBCUtil.close(pstmt);
		}
	}
	public boolean deleteFromId(Connection conn, String id){
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from androiduser where st_num = ?");
			pstmt.setString(1, id);
			int resultRow = pstmt.executeUpdate();
			if(resultRow>0)
				return true;
			else return false;
		}catch(SQLException e){
			return false;
		}	
		finally {
			JDBCUtil.close(pstmt);
		}
	}
	public int updateDB(Connection conn,String type,String[] data){
		PreparedStatement pstmt = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(date);
		try {
			pstmt = conn.prepareStatement("insert into "+type+"iteminfo (st_num,name,phone,kakao,"+type+"_date,"+type+"_place,"+type+"_classify,"+type+"_classifyDetail,"+type+"_imgPath,"+type+"_more,"+type+"_updatetime)"
					+"values (?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, data[0]);
			pstmt.setString(2, data[1]);
			pstmt.setString(3, data[2].equals("$null")?"":data[2]);
			pstmt.setString(4, data[3].equals("$null")||data[3].equals("$null ")?"":data[3]);
			pstmt.setString(5, data[4]);
			pstmt.setString(6, data[5]);
			pstmt.setString(7, data[6]);
			pstmt.setString(8, data[7]);
			pstmt.setString(9, data[8]==null?"":data[8]);
			pstmt.setString(10, data[9].equals("$null")||data[9].equals("$null ")?"":data[9]);
			pstmt.setString(11, currentTime);
			int resultRow = pstmt.executeUpdate();
			return resultRow;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			JDBCUtil.close(pstmt);
		}
	}
	public int tableLastNo(Connection conn, String type)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement("SHOW TABLE STATUS LIKE '"+type+"iteminfo'");
			rs = pstmt.executeQuery();
			int lastNo = 0;
			while(rs.next()){
				lastNo = rs.getInt(11);
			}
			return lastNo;
		}finally{
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public ArrayList<AdminContents> getNoticeListByType(Connection conn,String type, int startRow, int size){
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		ArrayList<AdminContents> list = new ArrayList<AdminContents>();
		try{
			pstmt = conn.prepareStatement("select * from "+type+" order by "+type+"_no desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(setAdminContents(rs));
			}
			return list;
		}catch(SQLException e){
			return null;
		}finally{
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}    
	private AdminContents setAdminContents(ResultSet rs) throws SQLException
	{
		Timestamp temp = rs.getTimestamp(6);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String updateDate = df.format(temp);
		return new AdminContents(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), updateDate, rs.getInt(7));
	}
	public boolean matchingWithStnum(Connection conn, String stnum,String type,int no)throws SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String query = "select st_num from "+type+"iteminfo where "+type+"_no = ?";	
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			String db_stnum = null;
			while(rs.next())
				db_stnum = rs.getString(1);
			if(db_stnum==null)
				return false;
			else if(db_stnum.equals(stnum)) 
				return true;
			else return false;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
	public int modifyData(Connection conn, String type, String data[]){
		PreparedStatement pstmt = null;
		try {
			String imgPathQuery = null;
			if(data[7]==null)
			{
				imgPathQuery = "";
			}
			else
				imgPathQuery = type+"_imgPath = \'"+data[7]+"\',";
			if(data[9].equals("true"))
				imgPathQuery = deleteFileAndDB(type, data[0], data[10]);
			String query = "update "+type+"iteminfo set phone = ?, kakao = ?, "
					+type+"_date = ?, "+type+"_place = ?, "+type+"_classify = ?, "
					+type+"_classifyDetail = ?,"+imgPathQuery+type+"_more = ? "
					+"where "+type+"_no= ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, data[1].equals("$null")?"":data[1]);
			pstmt.setString(2, data[2].equals("$null")?"":data[2]);
			pstmt.setString(3, data[3]);
			pstmt.setString(4, data[4]);
			pstmt.setString(5, data[5]);
			pstmt.setString(6, data[6]);
			pstmt.setString(7, data[8].equals("$null")?"":data[8]);
			pstmt.setInt(8, Integer.parseInt(data[0]));
			int resultRow = pstmt.executeUpdate();
			return resultRow;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			JDBCUtil.close(pstmt);
		}
	}
	private String deleteFileAndDB(String type,String noStr, String servletPath){
		int no = Integer.parseInt(noStr);
		String folderPath = servletPath+"/uploadImg/"+type+"/"+type+"_No."+no;
		File targetFolder = new File(folderPath);
		File[] files = targetFolder.listFiles();
		if(files!=null)
		{
			for(int i=0;i<files.length;++i)
			{
				boolean isDeleted = files[i].delete();
				if(!isDeleted)
					return null;
			}
			return type+"_imgPath = \'\',";
		}
		else return type+"_imgPath = \'\',";
	}
	public int deleteByNo(Connection conn, String type, String stnum, int no, String servletPath)
	{
		PreparedStatement pstmt = null;
		int resultRow = 0;
		try {
			if(matchingWithStnum(conn, stnum, type, no))
			{
				if(deleteFile(type, no, servletPath))
				{
					pstmt = conn.prepareStatement("delete from "+type+"iteminfo where "+type+"_no = ?");
					pstmt.setInt(1, no);
					resultRow = pstmt.executeUpdate();
				}
			}
			else
				return -2;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}finally {
			JDBCUtil.close(pstmt);
		}
		return resultRow;
	}
	public boolean deleteFile(String type,int no, String servletPath) throws SQLException
	{
		String folderPath = servletPath+"/uploadImg/"+type+"/"+type+"_No."+no;
		File targetFolder = new File(folderPath);
		File[] files = targetFolder.listFiles();
		if(files!=null)
		{
			for(int i=0;i<files.length;++i)
			{
				boolean isDeleted = files[i].delete();
				if(!isDeleted)
					return false;
			}
			return targetFolder.delete();
		}
		else return true;
	}
	public List<ItemList> searchFromKey(Connection conn, int startRow, int size, String type,String keyword) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from "+type+"iteminfo where "+type+"_classifyDetail like ? order by "+type+"_no desc limit ?,?");
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<ItemList> itemList = new ArrayList<ItemList>();
			while(rs.next())
				itemList.add(convertItemList(rs));
			return itemList;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
	}
}
