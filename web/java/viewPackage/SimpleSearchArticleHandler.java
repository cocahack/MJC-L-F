package viewPackage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class SimpleSearchArticleHandler implements CommandHandler {
	SearchArticleService searchService = new SearchArticleService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String pageNoVal = req.getParameter("pageNo");
		String type = req.getParameter("type");
		String keyWord = req.getParameter("keyword");
		int pageNo = 1;
		if(type==null){
			type = "lost";
		}
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = searchService.getArticlePage(pageNo,type,keyWord);
		req.setAttribute("articlePage", articlePage);
		req.setAttribute("searchMode", true);
		req.setAttribute("type", type);

		return "listView.jsp";
	}
	
}
