package mainList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.ConnectionProvider;
import member_pack.admin.Admin;

public class SelectedListExtractService {
	private SelectDao selectDao = new SelectDao();
	public List<ListValues> getItemList(String type){
		try(Connection conn = ConnectionProvider.getConnection()){
			List<ListValues> values = selectDao.extractList(conn, type);
			return values;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public List<AdminValues> getAdminItemList(String type){
		try(Connection conn = ConnectionProvider.getConnection()){
			List<AdminValues> values = selectDao.extractAdminList(conn, type);
			return values;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}
