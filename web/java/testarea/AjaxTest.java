package testarea;

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
		urlPatterns="/test.do"
		)

@MultipartConfig(
		maxFileSize = 1048576*5,
		maxRequestSize = -1,
		fileSizeThreshold = -1
		)
public class AjaxTest extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		// TODO Auto-generated method stub
		String authAdmin = (String) req.getSession().getAttribute("adminId");
		if(authAdmin==null){
			authAdmin = "";
		}
		Collection<Part> parts = req.getParts();
		String filePaths = "";
		String title="";
		String content="";
		String fullForm = "";
		for(Part part: parts){
			String contentDisp = part.getHeader("content-disposition");
			if(contentDisp.contains("filename=")){
				System.out.println(extractFileName(part,contentDisp));
				String fileName = extractFileName(part,contentDisp);
				String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/"+authAdmin);
				makeDir(tempDir);
				String tempPath = tempDir+"/"+fileName;
				if(part.getSize()>0){
					part.write(tempPath);
					filePaths += "/uploadImg/notice"+"/"+fileName+";";
				}
			}else{
				String paramName = part.getName();
				if(paramName.equals("title")){
					title = req.getParameter(paramName);
				}
				if(paramName.equals("content")){
					content = req.getParameter(paramName);
				}
				if(paramName.equals("fullForm")){
					fullForm = req.getParameter(paramName);
				}
			}
		}
		if(fullForm.equals("fullsubmit")){
			moveFile(req, filePaths);
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
	private void moveFile(HttpServletRequest req, String filePaths){
		String arr[] = filePaths.split(";");
		String fileDir = req.getSession().getServletContext().getRealPath("/uploadImg/test");
	    for (int i=0;i<arr.length;i++) {
	    	File file = new File(arr[i]);
	    	file.renameTo(new File(fileDir+"/"+file.getName()));
	    }
	}
}
