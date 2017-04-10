package member_pack.admin;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member_pack.PrivateInfoSet;
import mvc.CommandHandler;
import rsa.RSADecrypto;

public class AdminLoginHandler implements CommandHandler {
	private static final String FORM_VIEW = "/adminLogin.jsp";
	AdminLoginService loginService = new AdminLoginService();
	RSADecrypto rd = new RSADecrypto();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String beforeURL = req.getParameter("beforeURL");
		PrivateInfoSet pis = rd.processRequest(req, resp);
		Admin admin = new Admin(pis.getId(), pis.getPwd());
		AuthAdmin authAdmin = loginService.login(admin.getId(), admin.getPwd());
		if(authAdmin==null){
			req.setAttribute("beforeURL", beforeURL);
			req.setAttribute("AUTH", "FAIL");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/initLogin?type=admin");
			dispatcher.forward(req, resp);
		}else{
			HttpSession httpSession = req.getSession();
			httpSession.setAttribute("authAdmin", authAdmin);
			httpSession.setAttribute("adminId", authAdmin.getId());
			resp.sendRedirect(beforeURL);
		}
		return null;
	}

}
