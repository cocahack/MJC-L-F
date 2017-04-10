package android.search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.list.ItemList;
import android.list.ListPage;

@SuppressWarnings("serial")
@WebServlet(
		urlPatterns="/searchForAndroid.do"
		)
public class SearchContentHandler extends HttpServlet{
	private SearchContentService service = new SearchContentService();
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type= req.getParameter("type");
		String pageNoVal = req.getParameter("pageNo");
		String keyword = req.getParameter("keyword");
		PrintWriter out = resp.getWriter();
		String respString = "";
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		ListPage listPage = service.getListFromKeyword(type, pageNo, keyword);
		@SuppressWarnings("unchecked")
		List<ItemList> itemList = (List<ItemList>) listPage.getContent();
		JSONObject jsonObject = new JSONObject();
		JSONArray listArray = new JSONArray();
		JSONArray headerArray = new JSONArray();
		JSONObject listInfo = null;
		JSONObject header = new JSONObject();
		for(ItemList i:itemList){
			listInfo = new JSONObject();
			listInfo.put("no",i.getNo());
			listInfo.put("class", i.getClassify());
			listInfo.put("title", i.getTitle());
			listInfo.put("eventDate", i.getEventDate());
			listInfo.put("regitDate", i.getRegitDate());
			listArray.add(listInfo);
		}
		header.put("msg", "리스트 목록을 받았습니다.");
		header.put("state", 0);
		headerArray.add(header);
		jsonObject.put("header", headerArray);
		jsonObject.put("lists", listArray);
		respString += jsonObject.toJSONString();
		out.println(respString);
		out.flush();
		out.close();
	}
	
}
