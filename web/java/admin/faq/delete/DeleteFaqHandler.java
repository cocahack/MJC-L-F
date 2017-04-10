package admin.faq.delete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.faq.FaqService;
import mvc.CommandHandler;

public class DeleteFaqHandler implements CommandHandler{
	private FaqService faqService = new FaqService();

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
		boolean getDeleteResult = faqService.delete(servletPath,no);
		if(getDeleteResult){
			return "faq.do?pageNo="+pageNo;
		}else{
			req.setAttribute("notAvailable", "notAvailable");
			req.setAttribute("errorTitle", "내부 에러");
			req.setAttribute("errorMsg", "게시물 삭제 도중 에러가 발생했습니다.");
			return "faqRead.do?no="+no+"&pageNo="+pageNo;
		}
	}
	
}
