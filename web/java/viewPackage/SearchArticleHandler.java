package viewPackage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class SearchArticleHandler implements CommandHandler {
	private SearchArticleService searchService = new SearchArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		String pageNoVal = req.getParameter("pageNo");
		String type = req.getParameter("type");
		String name = req.getParameter("name");
		String stnum = req.getParameter("stnum");
		String classify = req.getParameter("classify");
		String detail = req.getParameter("detail");
		String startEventDate = req.getParameter("start_date");
		String endEventDate =req.getParameter("end_date");
		String startRegitDate = req.getParameter("start_regitdate");
		String endRegitDate = req.getParameter("end_regitdate");
		SearchData sd = new SearchData(name, stnum, classify, detail, startRegitDate, endRegitDate, startEventDate, endEventDate);
		int pageNo = 1;
		if(type==null){
			type = "lost";
		}
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = searchService.getArticlePage(pageNo,type,sd);
		req.setAttribute("articlePage", articlePage);
		req.setAttribute("searchMode", true);
		req.setAttribute("type", type);
		return "listView.jsp";
	}
}
