package testarea;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(
		urlPatterns="/testAndroid.do"
		)
@MultipartConfig(
		maxFileSize = 1048576*5,
		maxRequestSize = -1,
		fileSizeThreshold = -1
		)
public class RequestTest extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Collection<Part> parts = req.getParts();
		String type = "";
		for(Part part: parts){
			String contentDisp = part.getHeader("content-disposition");
			if(contentDisp.contains("filename=")){
				System.out.println(extractFileName(part,contentDisp));
				String fileName = extractFileName(part,contentDisp);
				String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/android");
				makeDir(tempDir);
				String tempPath = tempDir+"/"+fileName;
				if(part.getSize()>0){
					part.write(tempPath);
				}
			}else{
				if(part.getName().equals("type"))
					type = req.getParameter(part.getName());
			}
		}
		PrintWriter out = resp.getWriter();
		out.print("받은 메시지: "+type);	
		out.close();
	}
	private void makeDir(String dir){
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	private String extractFileName(Part part, String contentDisp){
	    String[] items = contentDisp.split(";");
	    for (String s : items) {
	        if (s.trim().startsWith("filename")) {
	            return s.substring(s.indexOf("=") + 2, s.length()-1);
	        }
	    }
	    return "";
	}
}
