package member_pack.admin;

import java.sql.Connection;
import java.sql.SQLException;

import exception.LoginFailException;
import jdbc.ConnectionProvider;
import member_pack.AdminDao;

public class AdminLoginService {
	private AdminDao adminDao = new AdminDao();
	
	public AuthAdmin login(String id, String pwd)throws SQLException{
		try(Connection conn = ConnectionProvider.getConnection()){
			Admin admin = adminDao.selectById(conn, id);
			if(admin == null){
				return null;
			}
			if(!admin.getPwd().equals(pwd)){
				return null;
			}
			return new AuthAdmin(admin.getId(),admin.getName() );
		}
	}
}
