package android.list;

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


@WebServlet(
		urlPatterns="/getList.do"
		)
public class GenerateListHandler extends HttpServlet {
	private GenerateListService service = new GenerateListService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String pageNoVal = req.getParameter("pageNo");
		String type = req.getParameter("type");
		String respString = "";
		int pageNo = 1;
		if(pageNoVal!=null){
			pageNo = Integer.parseInt(pageNoVal);
		}
		if(type==null){
			JSONObject jsonObject = new JSONObject();
			JSONArray listArray = new JSONArray();
			JSONObject listInfo = null;
			listInfo = new JSONObject();
			listInfo.put("state", 4);
			listInfo.put("msg", "서버가 필요한 정보를 전달 받지 못했습니다.");
			listArray.add(listInfo);
			jsonObject.put("header", listArray);
		}
		else if(type.equals("lost")||type.equals("found")){
			ListPage listPage = service.itemListService(pageNo, type);
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
		}
		else if(type.equals("notice")){
			ListPage listPage = service.adminItemListService(pageNo, type);
			@SuppressWarnings("unchecked")
			List<AdminMenuList> itemList = (List<AdminMenuList>) listPage.getContent();
			JSONObject jsonObject = new JSONObject();
			JSONArray listArray = new JSONArray();
			JSONArray headerArray = new JSONArray();
			JSONObject listInfo = null;
			JSONObject header = new JSONObject();
			for(AdminMenuList i:itemList){
				listInfo = new JSONObject();
				listInfo.put("no",i.getNo());
				listInfo.put("title", i.getTitle());
				listInfo.put("regitDate", i.getRegitDate());
				listInfo.put("hit", i.getHit());
				listArray.add(listInfo);
			}
			header.put("msg", "리스트 목록을 받았습니다.");
			header.put("state", 0);
			headerArray.add(header);
			jsonObject.put("header", headerArray);
			jsonObject.put("lists", listArray);
			respString += jsonObject.toJSONString();
			out.println(respString);
		}
		else if(type.equals("faq")){
			ListPage listPage = service.adminItemListService(pageNo, type);
			@SuppressWarnings("unchecked")
			List<AdminMenuList> itemList = (List<AdminMenuList>) listPage.getContent();
			JSONObject jsonObject = new JSONObject();
			JSONArray listArray = new JSONArray();
			JSONArray headerArray = new JSONArray();
			JSONObject listInfo = null;
			JSONObject header = new JSONObject();
			for(AdminMenuList i:itemList){
				listInfo = new JSONObject();
				listInfo.put("no",i.getNo());
				listInfo.put("title", i.getTitle());
				listInfo.put("regitDate", i.getRegitDate());
				listInfo.put("hit", i.getHit());
				listArray.add(listInfo);
			}
			header.put("msg", "리스트 목록을 받았습니다.");
			header.put("state", 0);
			headerArray.add(header);
			jsonObject.put("header", headerArray);
			jsonObject.put("lists", listArray);
			respString += jsonObject.toJSONString();
			out.println(respString);
		}
	}

}
