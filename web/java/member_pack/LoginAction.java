package member_pack;

import java.io.IOException;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rsa.RSA;

public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String pwd, id, name, dbid, beforeURL;
	boolean isLogin;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession httpSession = req.getSession();
		id = (String)req.getParameter("id");
		pwd = (String)req.getParameter("pwd");
		beforeURL = (String)req.getParameter("beforeURL");
		try {
			isLogin = accessDB(id, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isLogin){
			httpSession.setAttribute("stnum", dbid);
			httpSession.setAttribute("name", name);
			httpSession.setAttribute("AUTH", "OK");
			resp.sendRedirect(beforeURL);
		}
		else{
			httpSession.setAttribute("AUTH", "FAIL");
			req.setAttribute("beforeURL", beforeURL);
			RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, resp);
		}
		
	}
	private boolean accessDB(String id, String pwd) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String jdbcDriver = "jdbc:apache:commons:dbcp:webdb";
			String query = "select * from student where st_num='"+id
					+"' AND st_pwd='"+pwd+"';";
			conn = DriverManager.getConnection(jdbcDriver);
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()){
				dbid = rs.getString(1);
				name = rs.getString(3);
				return true;
			}
			else{
				return false;
			}
		}
		finally{
			if(rs != null)try{rs.close();}catch (SQLException e) {
				// TODO: handle exception
			}
			if(pstmt != null)try{pstmt.close();}catch (SQLException e) {
				// TODO: handle exception
			}
			if(conn != null)try{conn.close();}catch (SQLException e) {
				// TODO: handle exception
			}
		}
	}
}
