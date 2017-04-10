package member_pack;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		Enumeration<String> sessionNames = session.getAttributeNames();
		while(sessionNames.hasMoreElements()){
			session.removeAttribute(sessionNames.nextElement());
		}
		resp.sendRedirect("index.do");
	}

	
}
