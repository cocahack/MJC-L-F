package viewPackage.delete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;
import viewPackage.ArticleList;
public class RemoveArticleHandler implements CommandHandler{
	private RemoveArticleService removeArticleService = new RemoveArticleService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String stnum = (String)req.getSession().getAttribute("stnum");
		String type = req.getParameter("type");
		String strNo = req.getParameter("no");
		String strPageNo = req.getParameter("pageNo");
		String admin_id = (String) req.getSession().getAttribute("adminId");
		int pageNo;
		if(strPageNo==null){
			pageNo = 1;
		}else{
			pageNo = Integer.parseInt(strPageNo);
		}
		int no = Integer.parseInt(strNo);
		
		ArticleList list = removeArticleService.select(type, no);
		if(stnum==null){
			if(admin_id==null){
				req.setAttribute("notAvailable", "notAvailable");
				req.setAttribute("errorTitle", "권한 없음");
				req.setAttribute("errorMsg", "해당 서비스에 대한 권한이 없습니다.");
				return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
			}else{
				boolean isDeleted = removeArticleService.removeReq(type, no);
				if(isDeleted){
					return "list.do?type="+type+"&pageNo="+pageNo;
				}else{
					req.setAttribute("notAvailable", "notAvailable");
					req.setAttribute("errorTitle", "서버 오류");
					req.setAttribute("errorMsg", "서버 오류로 삭제 할 수 없었습니다. 관리자에게 문의해주세요.");
					return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
				}
				
			}
		}else if(!stnum.equals(list.getWriter().getStnum())){
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "권한 없음");
			req.setAttribute("errorMsg", "해당 서비스에 대한 권한이 없습니다.");
			return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
		}
		else{
			boolean isDeleted = removeArticleService.removeReq(type, no);
			if(isDeleted){
				return "list.do?type="+type+"&pageNo="+pageNo;
			}else{
				req.setAttribute("notAvailable", "notAvailable");
				req.setAttribute("errorTitle", "서버 오류");
				req.setAttribute("errorMsg", "서버 오류로 삭제 할 수 없었습니다. 관리자에게 문의해주세요.");
				return "read.do?type="+type+"&no="+no+"&pageNo="+pageNo;
			}
			
		}
	}
}
