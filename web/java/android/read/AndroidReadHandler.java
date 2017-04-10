package android.read;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(
		urlPatterns="/getReadItem.do"
		)
public class AndroidReadHandler extends HttpServlet {
	private AndroidReadService service = new AndroidReadService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String noVal = req.getParameter("no");
		String type = req.getParameter("type");
		PrintWriter out = resp.getWriter();
		if(type==null||noVal==null){
			JSONObject jsonObject = new JSONObject();
			JSONArray headerArr= new JSONArray();
			JSONObject header = new JSONObject();
			header.put("state", "4");
			header.put("msg","게시물 데이터를 받아오는데 실패했습니다.");
			headerArr.add(header);
			jsonObject.put("header", headerArr);
			String jsonText = jsonObject.toJSONString();
			out.print(jsonText);
		}else{
			int no = Integer.parseInt(noVal);
			if(type.equals("lost")||type.equals("found")){
				try {
					ItemRead itemRead = service.getItemArticleById(no, type);
					JSONObject jsonObject = new JSONObject();
					JSONArray headerArr= new JSONArray();
					JSONObject header = new JSONObject();
					header.put("state", "0");
					header.put("msg","게시물 데이터를 성공적으로 받아왔습니다.");
					headerArr.add(header);
					jsonObject.put("header", headerArr);
					JSONArray readArr = new JSONArray();
					JSONObject readItems = new JSONObject();
					readItems.put("no", itemRead.getNo());
					readItems.put("title", itemRead.getDetail());
					readItems.put("category", itemRead.getClassify());
					readItems.put("stnum", itemRead.getStnum());
					readItems.put("name", itemRead.getName());
					readItems.put("phone", itemRead.getPhone());
					readItems.put("kakao", itemRead.getKakao());
					readItems.put("place", itemRead.getPlace());
					readItems.put("eventDate", itemRead.getEventDate());
					readItems.put("regitDate", itemRead.getRegitDate());
					readItems.put("imgPath", itemRead.getImgPath());
					readItems.put("explain", itemRead.getExplain());
					readArr.add(readItems);
					jsonObject.put("readItems", readArr);
					String jsonText = jsonObject.toJSONString();
					out.print(jsonText);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch(NullPointerException e){
					out.println("잘못된 요청입니다. 게시물 번호를 확인해주세요.");
				}
			}
			else if(type.equals("notice")||type.equals("faq")){
				try {
					AdminReadItem itemRead = service.getAdminItemArticleById(no, type);
					JSONObject jsonObject = new JSONObject();
					JSONArray headerArr= new JSONArray();
					JSONObject header = new JSONObject();
					header.put("state", "0");
					header.put("msg","게시물 데이터를 성공적으로 받아왔습니다.");
					headerArr.add(header);
					jsonObject.put("header", headerArr);
					JSONArray readArr = new JSONArray();
					JSONObject readItems = new JSONObject();
					readItems.put("no", itemRead.getNo());
					readItems.put("title", itemRead.getTitle());
					readItems.put("writer", itemRead.getWriter());
					readItems.put("content", itemRead.getContent());
					readItems.put("imgPath", itemRead.getImgPath());
					readItems.put("regitDate", itemRead.getRegitDate());
					readItems.put("hit", itemRead.getHit());
					readArr.add(readItems);
					jsonObject.put("readItems", readArr);
					String jsonText = jsonObject.toJSONString();
					out.print(jsonText);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch(NullPointerException e){
					out.println("잘못된 요청입니다. 게시물 번호를 확인해주세요.");
				}
			}
			
		}
		
		
	}
	
}
