package member_pack;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member_pack.admin.Admin;
import member_pack.admin.AdminLoginService;
import member_pack.admin.AuthAdmin;
import mvc.CommandHandler;
import rsa.RSADecrypto;

public class MemberLoginHandler implements CommandHandler{
	private static final String FORM_VIEW = "/login.jsp";
	MemberLoginService memberLoginService = new MemberLoginService();
	RSADecrypto rd = new RSADecrypto();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String beforeURL = req.getParameter("beforeURL");
		PrivateInfoSet pis = rd.processRequest(req, resp);
		Member member = memberLoginService.login(pis);
		if(member==null){
			req.setAttribute("beforeURL", beforeURL);
			req.setAttribute("AUTH", "FAIL");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/initLogin?type=member");
			dispatcher.forward(req, resp);
		}else{
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("stnum", member.getSt_num());
			httpSession.setAttribute("name", member.getName());
			httpSession.setAttribute("AUTH", "OK");
			resp.sendRedirect(beforeURL);
		}
		return null;
	}

}
