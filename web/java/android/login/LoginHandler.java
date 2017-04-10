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
		urlPatterns="/androidLogin.do"
		)
public class LoginHandler extends HttpServlet {
	private LoginService service = new LoginService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		String mode = req.getParameter("mode");
		PrintWriter out = resp.getWriter();
		try {
			if(mode!=null){
				String key = service.doLoginAfterDeleteKey(id, pwd);
				out.print("Success"+key);
			}
			else{
				String key = service.doLogin(id, pwd);
				if(key.equals("")||key==null)
					out.print("ID_PWD_NOT_MATCHED");
				else if(key.equals("keyExist"))
					out.print("KEY_EXIST");
				else
					out.print("Success"+key);
			}
		} catch (SQLException e) {
			out.print("INTERNAL_SERVER_ERR");
		}
		out.flush();
		out.close();
	}

}
