package android.write;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(
		urlPatterns="/writeForAndroid.do"
		)
@MultipartConfig(
		maxFileSize = 1048576*5,
		maxRequestSize = -1,
		fileSizeThreshold = -1
		)
public class ItemWriteHandler extends HttpServlet {
	public final static String EMPTY_SPACE = "&null";
	private ItemWriteService service = new ItemWriteService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Collection<Part> parts = req.getParts();
		String type = null;
	    String stnum = null;
	    String name = null;
	    String phonenum = null;
        String kakao = null;
        String eventDate = null;
        String place = null;
        String classify = null;
        String detail = null;
        String explain = null;
        String filePath = null;
        String fileName = null;
        String tempPath = null;
        int resultRow = 0;
		for(Part part: parts){
			String contentDisp = part.getHeader("content-disposition");
			if(contentDisp.contains("filename=")){
				fileName = extractFileName(part,contentDisp);
				String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/android");
				makeDir(tempDir);
				tempPath = tempDir+"/"+fileName;
				if(part.getSize()>0){
					part.write(tempPath);
				}
			}else{
				switch(part.getName()){
					case "type":
						type = req.getParameter(part.getName());
						break;
					case "stnum":
						stnum = req.getParameter(part.getName());
						break;
					case "name":
						name = getStringFromStream(part.getInputStream());
						break;
					case "phone":
						phonenum = req.getParameter(part.getName());
						break;
					case "kakao":
						kakao = getStringFromStream(part.getInputStream());
						break;
					case "eventDate":
						eventDate = req.getParameter(part.getName());
						break;
					case "place":
						place = getStringFromStream(part.getInputStream());
						break;
					case "classify":
						classify = getStringFromStream(part.getInputStream());
						break;
					case "detail":
						detail = getStringFromStream(part.getInputStream());
						break;
					case "explain":
						explain = getStringFromStreamForLine(part.getInputStream());
						break;
				}
			}
		}
		try {	
			if(tempPath!=null){
				int lastNo = service.getLastNo(type);
				filePath = moveFile(req, lastNo,type);
				
			}
			String data[] ={stnum,name,phonenum,kakao,eventDate,place,classify,detail,filePath,explain};
			resultRow = service.updateDataService(type, data);
		} catch (SQLException e) {
			e.printStackTrace();
			resultRow = -1;
		}
		PrintWriter out = resp.getWriter();
		out.print(resultRow);	
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
	private String moveFile(HttpServletRequest req,int no,String type){
		String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/android");
		String moveDir = req.getSession().getServletContext().getRealPath("/uploadImg/"+type+"/"+type+"_No."+no);
		String filePaths = "";
		makeDir(moveDir);
		File[] tempfiles = new File(tempDir).listFiles();
		for(int i=0; i<tempfiles.length;i++){
			tempfiles[i].renameTo(new File(moveDir+"/"+tempfiles[i].getName()));
			filePaths += "/uploadImg/"+type+"/"+type+"_No."+no+"/"+tempfiles[i].getName();
		}
		File tempDirectory = new File(tempDir);
		tempDirectory.delete();
		return filePaths;
	}
	public String getStringFromStream(InputStream stream) 
            throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8")); 
        StringBuilder sb = new StringBuilder(); 
        String line = null; 

        try { 
            while ((line = br.readLine()) != null) { 
                sb.append(line);
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                stream.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        return sb.toString(); 
    } 
	public String getStringFromStreamForLine(InputStream stream) 
            throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8")); 
        StringBuilder sb = new StringBuilder(); 
        String line = null; 

        try { 
            while ((line = br.readLine()) != null) { 
                sb.append(line);
                sb.append("\n");
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                stream.close(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        String text = sb.toString();
        int length = text.length();
        text= text.substring(0, length-1);
        return text; 
    } 
}
