package admin.notice;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(
		urlPatterns="/noticeImageCancel.do"
		)
@MultipartConfig(
		maxFileSize = 1048576*5,
		maxRequestSize = -1,
		fileSizeThreshold = -1
		)
public class RemoveAjaxInNotice extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = (String)req.getSession().getAttribute("adminId");
		if(userId!=null){
			String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/"+userId);
			File tmpDir = new File(tempDir);
			File tmpDirs[] = new File(tempDir).listFiles();
			for(int i=0;i<tmpDirs.length;i++)
			{
				tmpDirs[i].delete();
			}
			tmpDir.delete();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authAdmin = (String) req.getSession().getAttribute("adminId");
		if(authAdmin==null){
			authAdmin = "";
		}
		Collection<Part> parts = req.getParts();
		for(Part part: parts){
			String contentDisp = part.getHeader("content-disposition");
			if(contentDisp.contains("filename=")){
				String fileName = extractFileName(part,contentDisp);
				String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/"+authAdmin);
				makeDir(tempDir);
				String tempPath = tempDir+"/"+fileName;
				if(part.getSize()>0){
					part.write(tempPath);
				}
			}
		}
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
