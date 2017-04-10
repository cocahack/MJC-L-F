package admin.faq.modify;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.faq.FaqService;
import admin.faq.read.FaqData;
import mvc.CommandHandler;

public class ModifyFaqHandler implements CommandHandler{
	private static final String FORM_VIEW = "WEB-INF/jsps/faqModify.jsp";
	private FaqService faqService = new FaqService();
	
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
		}
		else{
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	public String processForm(HttpServletRequest req,HttpServletResponse resp)throws IOException, ServletException{
		String noVal = req.getParameter("no");
		String pageNoVal = req.getParameter("pageNo");
		int no = Integer.parseInt(noVal);
		int pageNo = Integer.parseInt(pageNoVal);
		String adminId = (String)req.getSession().getAttribute("adminId");
		if(adminId==null){
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "권한 없음");
			req.setAttribute("errorMsg", "해당 서비스에 대한 권한이 없습니다.");
			return "faqRead.do?no="+no+"&pageNo="+pageNo;
		}
		FaqData faqData = faqService.getFaq(no, false);
		ModifyFaqValues mfv = new ModifyFaqValues(faqData.getFaqContent().getContent(), faqData.getFaqContent().getImgPath(), faqData.getFaqList().getRegitDate());
		ModifyFaqRequest mfReq = new ModifyFaqRequest(faqData.getFaqList().getTitle(), faqData.getFaqList().getWriter(), faqData.getFaqList().getNo(), mfv);
		req.setAttribute("mfReq", mfReq);
		return FORM_VIEW+"?no="+no+"&pageNo="+pageNo;
	}
	public String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String p_no = req.getParameter("paramno");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		int no = Integer.parseInt(p_no);
		String filePath = "";
		try {
			filePath = faqService.getImgPath(no);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			String [] deleteFileNames = req.getParameterValues("deleteFile[]");
			if(deleteFileNames!=null){
				filePath = fileDelete(req, no, deleteFileNames);
			}
		}catch(NullPointerException e){}
		String moveFilePaths = moveFile(req,no);
		if(moveFilePaths!=null||!moveFilePaths.equals("")){
			filePath += moveFilePaths;
		}
		faqService.modify(no,title,content,filePath);
		return "faq.do";
	}
	private String fileDelete(HttpServletRequest req, int no, String[] deleteFileNames){
		String userId = (String)req.getSession().getAttribute("adminId");
		if(userId!=null){
			String fileDir = req.getSession().getServletContext().getRealPath("/uploadImg/faq/faq_No."+no);
			File tmpDirs[] = new File(fileDir).listFiles();
			for(int i=0;i<tmpDirs.length;i++)
			{
				for(int j=0;j<deleteFileNames.length;j++){
					if(tmpDirs[i].getName().equals(deleteFileNames[j])){
						tmpDirs[i].delete();
					}
				}
			}
			String filePath = "";
			tmpDirs = new File(fileDir).listFiles();
			for(int k=0;k<tmpDirs.length;k++){
				filePath += "/uploadImg/faq/faq_No."+no+"/"+tmpDirs[k].getName()+";";
			}
			return filePath;
		}
		else{
			return "";
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
		String moveDir = req.getSession().getServletContext().getRealPath("/uploadImg/faq/faq_No."+no);
		String filePaths = "";
		makeDir(moveDir);
		File[] tempfiles = new File(tempDir).listFiles();
		if(tempfiles==null){
			return "";
		}else{
			for(int i=0; i<tempfiles.length;i++){
				tempfiles[i].renameTo(new File(moveDir+"/"+tempfiles[i].getName()));
				filePaths += "/uploadImg/faq/faq_No."+no+"/"+tempfiles[i].getName()+";";
			}
			File tempDirectory = new File(tempDir);
			tempDirectory.delete();
			return filePaths;
		}
	}
}
