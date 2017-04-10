package admin.notice.delete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.notice.NoticeService;
import mvc.CommandHandler;

public class DeleteNoticeHandler implements CommandHandler {
	private NoticeService service = new NoticeService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String noVal = req.getParameter("no");
		String pageNoVal = req.getParameter("pageNo");
		int no = Integer.parseInt(noVal);
		int pageNo;
		if(pageNoVal==null){
			pageNo = 1;
		}else{
			pageNo = Integer.parseInt(pageNoVal);
		}
		String servletPath = req.getSession().getServletContext().getRealPath("");
		boolean getDeleteResult = service.delete(servletPath, no);
		if(getDeleteResult){
			return "notice.do?pageNo="+pageNo;
		}else{
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "내부 에러");
			req.setAttribute("errorMsg", "게시물 삭제 도중 에러가 발생했습니다.");
			return "noticeRead.do?no="+no+"&pageNo="+pageNo;
		}
	}

}
