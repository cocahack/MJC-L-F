package android.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns="/checkLogin.do"
		)
public class LoginCheckHandler extends HttpServlet{
	private LoginCheckService service = new LoginCheckService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = req.getParameter("key");
		PrintWriter out = resp.getWriter();
		if(key==null||key.equals("")){
			out.println("KEY_HAS_NOT_VLAUE");
			out.flush();
			out.close();
		}else{
			try {
				String respStr = service.memberInfo(key);
				out.println(respStr);
				out.flush();
				out.close();
			} catch (SQLException e) {
				out.println("MEMBER_NOT_EXIST");
				out.flush();
				out.close();
			}
		}
	}
	
}
