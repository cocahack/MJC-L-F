package viewPackage;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ArticleListNotFoundException;
import exception.ViewContentNotFoundException;
import mvc.CommandHandler;

public class ReadArticleHandler implements CommandHandler{
	private ReadArticleService readService = new ReadArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String noVal = req.getParameter("no");
		String type = req.getParameter("type");
		int articleNum = Integer.parseInt(noVal);
		try {
			ArticleData articleData = readService.getArticle(articleNum, type);
			req.setAttribute("articleData", articleData);
			return "articleViewer.jsp";
		} catch (ArticleListNotFoundException | ViewContentNotFoundException e) {
			// TODO: handle exception
			req.getServletContext().log("no article",e);
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
	}
	
	
}
