package android.admin;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(
		urlPatterns="/getAdminContents.do"
		)
public class AdminContentHandler extends HttpServlet
{
	private AdminContentService service = new AdminContentService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		String pageNoStr= req.getParameter("pageNo");
		PrintWriter out = resp.getWriter();
		ArrayList<AdminContents> list = null;
		int pageNo = 1;
		if(pageNoStr!=null)
			pageNo = Integer.parseInt(pageNoStr);
		try {
			list = service.getContents(type, pageNo);
		} catch (SQLException e) {
			out.println("INTERNAL_ERROR");
			out.flush();
			out.close();
		}
		if(list!=null&&list.size()>0){
			String printStr = "";
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject listInfo = null;
			for(AdminContents i:list)
			{
				listInfo = new JSONObject();
				listInfo.put("no", i.getNo());
				listInfo.put("title", i.getTitle());
				listInfo.put("writer", i.getWriter());
				listInfo.put("content", i.getContent());
				String imgPaths = i.getImagePath();
				if(imgPaths!=null){
					String[] imgPath = imgPaths.split(";");
					listInfo.put("img_num",imgPath.length);
					for(int j=0;j<imgPath.length;++j)
					{
						listInfo.put("imagePath"+j, imgPath[j]);
					}
				}
				listInfo.put("regitDate", i.getRegitDate());
				listInfo.put("hit", i.getHit());
				jsonArray.add(listInfo);
			}
			jsonObject.put("contents", jsonArray);
			printStr += jsonObject.toJSONString();
			BufferedWriter bw = new BufferedWriter(out);
			bw.write(printStr);
			bw.flush();
			bw.close();
			out.close();
		}
		else if(list.size()==0){
			out.println("THERE_IS_NO_CONTENT");
			out.flush();
			out.close();
		}
		else{
			out.println("INTERNAL_ERROR");
			out.flush();
			out.close();
		}
	}

}
