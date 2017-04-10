package android.delete;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns="/deleteForAndroid.do"
		)
public class DeleteContentHandler extends HttpServlet{
	private DeleteContentService service = new DeleteContentService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String stnum = req.getParameter("stnum");
		String type = req.getParameter("type");
		String noVal = req.getParameter("no");
		String servletPath = req.getSession().getServletContext().getRealPath("");
		int no = 0;
		PrintWriter out = resp.getWriter();
		if(noVal==null){
			out.println("REQUEST_ERROR");
			out.flush();
			out.close();
		}else no = Integer.parseInt(noVal);
		String returnMsg = null;
		try {
			returnMsg = service.deleteContent(type, stnum, no, servletPath);
			out.println(returnMsg);
			out.flush();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
