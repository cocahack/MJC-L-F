package admin.faq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class FaqListHandler implements CommandHandler {
	FaqService service = new FaqService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String pageNoVal = req.getParameter("pagaNo");
		int pageNo = 1;
		if(pageNoVal!= null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		FaqPage faqPage = service.getFaqPage(pageNo);
		req.setAttribute("faqList", faqPage);
		return "WEB-INF/jsps/FAQ.jsp";
	}

}
