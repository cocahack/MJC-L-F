package admin.notice.read;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.notice.NoticeService;
import exception.NoticeContentNotFoundException;
import exception.NoticeListNotFoundException;
import mvc.CommandHandler;

public class NoticeReadHandler implements CommandHandler{
	NoticeService service = new NoticeService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String noVal = req.getParameter("no");
		int noticeNum = Integer.parseInt(noVal);
		try {
			NoticeData noticeData = service.getNotice(noticeNum, true);
			req.setAttribute("noticeData", noticeData);
			return "noticeRead.jsp";
		} catch (NoticeListNotFoundException | NoticeContentNotFoundException e) {
			// TODO: handle exception
			req.getServletContext().log("no artice",e);
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
}
