package admin.notice.write;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import admin.notice.NoticeService;
@WebServlet(
		urlPatterns="/noticeRegit.in"
		)
public class ProcessDoPost extends HttpServlet {
	NoticeService service = new NoticeService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = req.getRequestDispatcher("noticeRegit.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int numberOfNotice = service.getNumberOfList()+1;
		
		String title= req.getParameter("title");
		String content= req.getParameter("content");
		String filePaths = moveFile(req,numberOfNotice);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String regitDate = sdf.format(date);
		NoticeWriteEle ele = new NoticeWriteEle(title, content, "관리자", filePaths, regitDate);
		boolean isInsert = service.putDB(ele);
		if(isInsert){
			resp.sendRedirect("notice.do");
		}
		else{
			req.setAttribute("alert", "게시물 등록에 실패했습니다.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("noticeRegit.jsp");
			dispatcher.forward(req, resp);
		}
	}
	private void makeDir(String dir){
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	private String moveFile(HttpServletRequest req,int no){
		String authAdmin = (String)req.getSession().getAttribute("adminId");
		String tempDir = req.getSession().getServletContext().getRealPath("/uploadImg/temp/"+authAdmin);
		File[] tempfiles = new File(tempDir).listFiles();
		if(tempfiles!=null){
			String moveDir = req.getSession().getServletContext().getRealPath("/uploadImg/notice/notice_No."+no);
			String filePaths = "";
			makeDir(moveDir);
			for(int i=0; i<tempfiles.length;i++){
				tempfiles[i].renameTo(new File(moveDir+"/"+tempfiles[i].getName()));
				filePaths += "/uploadImg/notice/notice_No."+no+"/"+tempfiles[i].getName()+";";
			}
			File tempDirectory = new File(tempDir);
			tempDirectory.delete();
			return filePaths;
		}else
			return "";
		
	}
}
