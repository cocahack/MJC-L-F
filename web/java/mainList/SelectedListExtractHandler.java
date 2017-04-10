package mainList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.CommandHandler;

public class SelectedListExtractHandler implements CommandHandler {
	public SelectedListExtractService service = new SelectedListExtractService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		List<ListValues> lostItem = service.getItemList("lost");
		List<ListValues> foundItem = service.getItemList("found");
		List<AdminValues> noticeItem = service.getAdminItemList("notice");
		List<AdminValues> faqItem = service.getAdminItemList("faq");
		
		req.setAttribute("lostItem", lostItem);
		req.setAttribute("foundItem", foundItem);
		req.setAttribute("noticeItem", noticeItem);
		req.setAttribute("faqItem", faqItem);
		return "start.jsp";
	}

}
