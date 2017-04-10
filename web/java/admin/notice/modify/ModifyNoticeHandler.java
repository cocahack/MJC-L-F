package admin.notice.modify;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.notice.NoticeService;
import admin.notice.read.NoticeData;
import mvc.CommandHandler;

public class ModifyNoticeHandler implements CommandHandler {
	private static final String FORM_VIEW = "WEB-INF/jsps/noticeModify.jsp";
	private NoticeService service = new NoticeService();
	private DeleteExistImage dei = new DeleteExistImage();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		if(req.getMethod().equalsIgnoreCase("GET")){
			return processForm(req,resp);
		}
		else if(req.getMethod().equalsIgnoreCase("POST")){
			String result = processSubmit(req,resp);
			if(result == null){
				return null;
			}else{
				resp.sendRedirect(req.getContextPath()+"/"+result);
				return null;
			}
		}else{
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	public String processForm(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException{
		String noVal = req.getParameter("no");
		String pageNoVal = req.getParameter("pageNo");
		int no = Integer.parseInt(noVal);
		int pageNo = Integer.parseInt(pageNoVal);
		String adminId = (String)req.getSession().getAttribute("adminId");
		if(adminId==null){
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "권한 없음");
			req.setAttribute("errorMsg", "해당 서비스에 대한 권한이 없습니다.");
			return "noticeRead.do?no="+no+"&pageNo="+pageNo;
		}
		NoticeData noticeData = service.getNotice(no, false);
		ModifyNoticeValues mnv = new ModifyNoticeValues(noticeData.getNoticeContent().getContent(), noticeData.getNoticeContent().getImgPath(), noticeData.getNoticeList().getRegitDate());
		ModifyNoticeRequest mnReq = new ModifyNoticeRequest(noticeData.getNoticeList().getTitle(), noticeData.getNoticeList().getWriter(), noticeData.getNoticeList().getNo(), mnv);
		req.setAttribute("mnReq", mnReq);
		return FORM_VIEW+"?no="+no+"&pageNo="+pageNo;
	}
	public String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String p_no = req.getParameter("paramno");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		int no = Integer.parseInt(p_no);
		String filePath = "";
		try {
			filePath = service.getImgPath(no);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			String[] deleteFileNames = req.getParameterValues("deleteFile[]");
			if(deleteFileNames!=null){
				filePath = dei.fileDelete(req,no, deleteFileNames);
			}
		}catch(NullPointerException e){
			
		}
		String moveFilePaths = moveFile(req, no);
		if(moveFilePaths!=null||!moveFilePaths.equals("")){
			filePath += moveFilePaths;
		}	
		service.modify(no, title, content, filePath);
		return "notice.do";
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
		String moveDir = req.getSession().getServletContext().getRealPath("/uploadImg/notice/notice_No."+no);
		String filePaths = "";
		makeDir(moveDir);
		File[] tempfiles = new File(tempDir).listFiles();
		if(tempfiles==null){
			return "";
		}else{
			for(int i=0; i<tempfiles.length;i++){
				tempfiles[i].renameTo(new File(moveDir+"/"+tempfiles[i].getName()));
				filePaths += "/uploadImg/notice/notice_No."+no+"/"+tempfiles[i].getName()+";";
			}
			File tempDirectory = new File(tempDir);
			tempDirectory.delete();
			return filePaths;
		}
	}
}
