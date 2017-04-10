package viewPackage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class ListArticleHandler implements CommandHandler{
	private ListArticleService listService = new ListArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String pageNoVal = req.getParameter("pageNo");
		String type = req.getParameter("type");
		int pageNo = 1;
		if(type==null){
			type = "lost";
		}
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo,type);
		req.setAttribute("articlePage", articlePage);
		return "listView.jsp";
	}
	
}
