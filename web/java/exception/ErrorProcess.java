package exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class ErrorProcess implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		int errorCode = (int)req.getAttribute("errorCode");
		if(errorCode==403){
			req.setAttribute("errorCode", errorCode);
			req.setAttribute("errorHead", ErrorCodeMsg.ERROR_HEAD_403);
			req.setAttribute("errorMsg", ErrorCodeMsg.ERROR_MSG_403);
		}
		if(errorCode==401){
			req.setAttribute("errorCode", errorCode);
			req.setAttribute("errorHead", ErrorCodeMsg.ERROR_HEAD_401);
			req.setAttribute("errorMsg", ErrorCodeMsg.ERROR_MSG_401);
		}
		if(errorCode==404){
			req.setAttribute("errorCode", errorCode);
			req.setAttribute("errorHead", ErrorCodeMsg.ERROR_HEAD_404);
			req.setAttribute("errorMsg", ErrorCodeMsg.ERROR_MSG_404);
		}
		return "error.jsp";
	}

}
