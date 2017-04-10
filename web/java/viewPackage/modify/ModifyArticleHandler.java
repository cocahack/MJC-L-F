package viewPackage.modify;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import exception.ArticleListNotFoundException;
import exception.ErrorCodeMsg;
import exception.ErrorProcess;
import exception.PermissionDeniedException;
import member_pack.admin.AuthAdmin;
import mvc.CommandHandler;
import viewPackage.ArticleData;
import viewPackage.ArticleList;
import viewPackage.PageValues;
import viewPackage.ReadArticleService;
import viewPackage.Writer;

public class ModifyArticleHandler implements CommandHandler{
	private static final String FORM_VIEW = "modify.jsp";
	private ErrorProcess ep = new ErrorProcess();
	private ReadArticleService readService = new ReadArticleService();
	private ModifyArticleService modifyArticleService = new ModifyArticleService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub	
		if(req.getMethod().equalsIgnoreCase("GET")){
			return processForm(req,resp);
		}
		else if(req.getMethod().equalsIgnoreCase("POST")){
			String result = processSubmit(req,resp);
			if(result==null){
				return null;
			}
			else{
				resp.sendRedirect(req.getContextPath()+"/"+result);
				return null;
			}
		}else{
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String noVal = req.getParameter("no");
		String type = req.getParameter("type");
		String pageNoVal = req.getParameter("pageNo");
		String name = (String)req.getSession().getAttribute("name");
		String stnum = (String)req.getSession().getAttribute("stnum");
		String admin_id = (String) req.getSession().getAttribute("adminId");
		int no = Integer.parseInt(noVal);
		int pageNo = Integer.parseInt(pageNoVal);
		ArticleData articleData = readService.getArticle(no, type);
		if(stnum==null){
			if(admin_id==null){
				req.setAttribute("notAvailable", "notAvailable");
				req.setAttribute("errorTitle", "권한 없음");
				req.setAttribute("errorMsg", "해당 서비스에 대한 권한이 없습니다.");
				return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
			}else{
				ModifyValues mv = new ModifyValues(articleData.getViewContent().getPhone(), articleData.getViewContent().getKakao(), articleData.getViewContent().getPlace(), articleData.getViewContent().getClassify(), articleData.getViewContent().getDetail(), articleData.getViewContent().getImgPath(), articleData.getViewContent().getExplain(), articleData.getViewContent().getDate(), type);
				ModifyRequest modReq = new ModifyRequest(articleData.getArticleList().getDetailClassify(),name, stnum, articleData.getArticleList().getNo(),mv);
				req.setAttribute("modReq", modReq);
				return FORM_VIEW+"?type="+type+"&no="+no+"&pageNo="+pageNo;
			}
		}
		else if(!canModify(stnum, articleData)){
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "권한 없음");
			req.setAttribute("errorMsg", "해당 서비스에 대한 권한이 없습니다.");
			return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
		}
		ModifyValues mv = new ModifyValues(articleData.getViewContent().getPhone(), articleData.getViewContent().getKakao(), articleData.getViewContent().getPlace(), articleData.getViewContent().getClassify(), articleData.getViewContent().getDetail(), articleData.getViewContent().getImgPath(), articleData.getViewContent().getExplain(), articleData.getViewContent().getDate(), type);
		ModifyRequest modReq = new ModifyRequest(articleData.getArticleList().getDetailClassify(),name, stnum, articleData.getArticleList().getNo(),mv);
		req.setAttribute("modReq", modReq);
		return FORM_VIEW+"?type="+type+"&no="+no+"&pageNo="+pageNo;
	}
	private boolean canModify(String stnum, ArticleData articleData){
		String writerStnum = articleData.getArticleList().getWriter().getStnum();
		return stnum.equals(writerStnum);
	}
	private String processSubmit(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		int sizeLimit = 10*1024*1024; //1mb
		String savePath = req.getSession().getServletContext().getRealPath("/uploadImg/temp");/*"D:/JspProject/re_project/WebContent/uploadImg/temp";*/
		MultipartRequest multi = new MultipartRequest(req, savePath, sizeLimit,"utf-8", new DefaultFileRenamePolicy());
		String stnum = (String)req.getSession().getAttribute("stnum");
		String type = multi.getParameter("type");
		String noVal  = multi.getParameter("no");
		int no, pageNo;
		if(noVal==null){
			no = 0;
		}else{
			no = Integer.parseInt(noVal);
		}
		String pageNoVal  = multi.getParameter("pageNo");
		if(pageNoVal==null){
			pageNo = 0;
		}else{
			pageNo = Integer.parseInt(pageNoVal);
		}
		String req_stnum, name, phonenum, kakao, place, classify, detail, filePath, explain, inputdate;
		String filename=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date regitdate;
		
		type = multi.getParameter("type");
		stnum = multi.getParameter("stnum");
		name = multi.getParameter("name");
		phonenum = multi.getParameter("fullphonenum")==null?"":multi.getParameter("fullphonenum");
		kakao = multi.getParameter("kakao")==null?"":multi.getParameter("kakao");
		inputdate = multi.getParameter("fullDate");
		try {
			regitdate = df.parse(inputdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		place = multi.getParameter("place");
		classify = multi.getParameter("classify");
		detail = multi.getParameter("detail")==null?"":multi.getParameter("detail");
		explain = multi.getParameter("explain")==null?"":multi.getParameter("explain");
		Enumeration files = multi.getFileNames();
		
		while(files.hasMoreElements()){
			filename = (String)files.nextElement();
		}		
		if(filename==null){
			filePath = null;
		}else{
			filePath = getFilePath(multi, filename);
		}
		
	
		ModifyValues mv = new ModifyValues(phonenum, kakao, place, classify, detail, filePath, explain, inputdate, type);
		ModifyRequest modReq = new ModifyRequest(detail,name, stnum, no,mv);
		req.setAttribute("modReq", modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		modReq.validate(errors);
		
		if(!errors.isEmpty()){
			return FORM_VIEW+"?type="+type+"&no="+no+"&pageNo="+pageNo;
		}
		try {
			modifyArticleService.modify(modReq, type, mv);
			PageValues pv = new PageValues(type, no, pageNo, "update");
			req.setAttribute("pageValues", pv);
			return "list.do"+"?type="+type+"&no="+no+"&pageNo="+pageNo;
		} catch (ArticleListNotFoundException e) {
			// TODO: handle exception
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "내부 서버 에러");
			req.setAttribute("errorMsg", "게시물 수정 작업에서 에러가 발생했습니다. 다시 시도해보시고 그래도 에러가 발생하면 관리자에게 문의해 주세요.");
			return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
		} catch (PermissionDeniedException e) {
			// TODO: handle exception
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "권한 에러");
			req.setAttribute("errorMsg", "게시물 수정 작업에서 에러가 발생했습니다. 다시 시도해보시고 그래도 에러가 발생하면 관리자에게 문의해 주세요.");
			return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
		}
	}
	private String getFilePath(MultipartRequest multi, String filename){
		String filePath = null;
		if(multi.getFilesystemName(filename)==null||multi.getFilesystemName(filename).equals("")){
			filePath="";
		}
		else{
			filePath =   "/uploadImg/temp" + "/" +multi.getFilesystemName(filename);
		}
		return filePath;
	}
	private String setErrorPage(HttpServletRequest req, HttpServletResponse resp, int errorCode){
		if(errorCode==403){
			req.setAttribute("errorCode", errorCode);
			req.setAttribute("errorHead", ErrorCodeMsg.ERROR_HEAD_403);
			req.setAttribute("errorMsg", ErrorCodeMsg.ERROR_MSG_403);
		}
		if(errorCode==401){
			req.setAttribute("errorCode", errorCode);
			req.setAttribute("errorHead", ErrorCodeMsg.ERROR_HEAD_401);
			req.setAttribute("errorMsg", ErrorCodeMsg.ERROR_MSG_401);
		}
		if(errorCode==404){
			req.setAttribute("errorCode", errorCode);
			req.setAttribute("errorHead", ErrorCodeMsg.ERROR_HEAD_404);
			req.setAttribute("errorMsg", ErrorCodeMsg.ERROR_MSG_404);
		}
		return "error.jsp";
	}
}
