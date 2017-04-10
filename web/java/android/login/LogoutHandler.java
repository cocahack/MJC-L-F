package android.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns="/androidLogout.do"
		)
public class LogoutHandler extends HttpServlet{
	private LogoutService service = new LogoutService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String key = req.getParameter("key");
		PrintWriter out = resp.getWriter();
		boolean isDelete = service.deleteService(key);
		if(isDelete){
			out.println("REQ_SUCCESS");
			out.flush();
			out.close();
		}else{
			out.println("REQ_FAIL");
			out.flush();
			out.close();
		}
	}
	
}
