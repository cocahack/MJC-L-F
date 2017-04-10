package admin.notice.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.notice.NoticePage;
import mvc.CommandHandler;

public class NoticeSearchHandler implements CommandHandler{
	private NoticeSearchService service = new NoticeSearchService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String keyword = req.getParameter("keyword");
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		NoticePage noticePage = service.getArticlePage(pageNo, keyword);
		req.setAttribute("noticeList", noticePage);
		req.setAttribute("searchMode", true);
		return "notice.jsp";
	}
	
}
