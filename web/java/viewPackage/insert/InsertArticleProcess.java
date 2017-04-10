/*package viewPackage.insert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(
		urlPatterns={"/regit.do"}
		)
@MultipartConfig(
		location = "D:\\JspProject\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\re_project\\uploadImg\\temp",
		maxFileSize = -1,
		maxRequestSize = -1,
		fileSizeThreshold = 1024
		) 
public class InsertArticleProcess extends HttpServlet {
	InsertArticleService service = new InsertArticleService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int lastNoOf = service.getNumberOfList()+1;
		String type, stnum, name, phonenum, kakao, place, classify, detail, filePath, explain, inputdate;
		String filename, updateDate;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date regitdate;
		
		Collection<Part> parts = req.getParts();
		for(Part part:parts){
			String contentDisp = part.getHeader("content-disposition");
			if(contentDisp.contains("filename=")){
				String fileName = extractFileName(part,contentDisp);
				String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/notice/notice_No."+numberOfNotice);
				makeDir(tempDir);
				String tempPath = tempDir+"/"+fileName;
				if(part.getSize()>0){
					part.write(tempPath);
					filePaths += "/uploadImg/notice/notice_No."+numberOfNotice+"/"+fileName+";";
				}
			}else{
				String paramName = part.getName();
				if(paramName.equals("title")){
					title = req.getParameter(paramName);
				}
				if(paramName.equals("content")){
					content = req.getParameter(paramName);
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
*/