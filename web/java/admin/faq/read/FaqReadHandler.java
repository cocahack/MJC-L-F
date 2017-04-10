package admin.faq.read;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.faq.FaqService;
import exception.FaqContentNotFoundException;
import exception.FaqListNotFoundException;
import mvc.CommandHandler;

public class FaqReadHandler implements CommandHandler {
	private FaqService faqService = new FaqService(); 
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);
		try {
			FaqData faqData = faqService.getFaq(no, true);
			req.setAttribute("faqData", faqData);
			return "WEB-INF/jsps/faqRead.jsp";
		} catch (FaqListNotFoundException | FaqContentNotFoundException e) {
			req.getServletContext().log("no artice",e);
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return null;
	}

}
