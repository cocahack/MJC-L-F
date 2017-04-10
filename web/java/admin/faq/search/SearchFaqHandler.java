package admin.faq.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.faq.FaqPage;
import mvc.CommandHandler;

public class SearchFaqHandler implements CommandHandler {
	private SearchFaqService service = new SearchFaqService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String keyword=req.getParameter("keyword");
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		FaqPage faqPage = service.getArticlePage(pageNo, keyword);
		req.setAttribute("faqList", faqPage);
		req.setAttribute("searchMode", true);
		return "/WEB-INF/jsps/FAQ.jsp";
	}

}
