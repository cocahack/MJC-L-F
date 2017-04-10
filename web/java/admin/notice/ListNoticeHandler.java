package admin.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class ListNoticeHandler implements CommandHandler{
	NoticeService service = new NoticeService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		NoticePage noticePage = service.getNoticePage(pageNo);
		req.setAttribute("noticeList", noticePage);
		return "notice.jsp";
	}
	
}
