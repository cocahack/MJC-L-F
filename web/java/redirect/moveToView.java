package redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class moveToView implements CommandHandler{

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String moveToPage = req.getParameter("dstn");
		if(moveToPage.equals("1")){
			return "regit.jsp";
		}else if(moveToPage.equals("2")){
			return "WEB-INF/jsps/locationInfo.jsp";
		}else{
			req.setAttribute("errorCode",400);
			req.setAttribute("errorHead","잘못된 요청입니다.");
			req.setAttribute("errorMsg","페이지 접근이 정상적이지 않습니다.");
			return "error.jsp";
		}		
	}
	
}
