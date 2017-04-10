package testarea;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns="/removeall.do"
		)
public class RemoveTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = (String) req.getSession().getAttribute("adminId");
		String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/"+userId);
		File tmpDir = new File(tempDir);
		tmpDir.delete();
		/*for(int i=0;i<tmpDirs.length;i++)

		{

		System.out.println( tmpDirs[i].getName() );

		tmpDirs[i].delete();

		}*/
	}

}
