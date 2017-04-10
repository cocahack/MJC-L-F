package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.TabExpander;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UpdateDB extends HttpServlet {
	private String type, stnum, name, phonenum, kakao, place, classify, detail, filePath, explain, inputdate;
	private String filename, updateDate;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private Date regitdate;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = req.getRequestDispatcher("regit.jsp");
		dispatcher.forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain;charset=utf-8");
		int sizeLimit = 10*1024*1024; //1mb
		String savePath = req.getSession().getServletContext().getRealPath("/uploadImg/temp");/*"D:/JspProject/re_project/WebContent/uploadImg/temp";*/
		MultipartRequest multi = new MultipartRequest(req, savePath, sizeLimit,"utf-8", new DefaultFileRenamePolicy());
		type = multi.getParameter("type");
		stnum = multi.getParameter("stnum");
		name = multi.getParameter("name");
		phonenum = multi.getParameter("fullphonenum")==null?"null":multi.getParameter("fullphonenum");
		kakao = multi.getParameter("kakao")==null?"null":multi.getParameter("kakao");
		inputdate = multi.getParameter("fullDate");
		try {
			regitdate = df.parse(inputdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		place = multi.getParameter("place");
		classify = multi.getParameter("classify");
		detail = multi.getParameter("detail")==null?"null":multi.getParameter("detail");
		explain = multi.getParameter("explain")==null?"null":multi.getParameter("explain");
		Enumeration files = multi.getFileNames();
		
		while(files.hasMoreElements()){
			filename = (String)files.nextElement();
		}		
		if(multi.getFilesystemName(filename)==null||multi.getFilesystemName(filename).equals("")){
			filePath="";
		}
		else{
			filePath =   "/uploadImg/temp" + "/" +multi.getFilesystemName(filename);
		}
		if(updateDB(type, stnum, name, phonenum, kakao, inputdate, place, classify, detail, explain, filePath, updateDate)){
			resp.sendRedirect("list.do?type=lost");
		}
		else{
			/*PrintWriter out = resp.getWriter();
			out.println(inputdate+"   "+regitdate);*/
		}
	}
	
	
	private boolean updateDB(String type, String stnum, String name, String phonenum, String kakao, String inputdate, String place, String classify, String detail, String explain, String filePath, String updateDate) throws ServletException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String tableselect = null;
		String prefixDB = null;
		String query = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(date);
		if(type.equals("loss")){
			tableselect = "lostiteminfo";
			prefixDB = "lost_";
		}
		else if(type.equals("found")){
			tableselect = "founditeminfo";
			prefixDB = "found_";
		}
		try {
			String jdbcDriver = "jdbc:apache:commons:dbcp:webdb";
			query = "insert into "+ tableselect 
					+"(st_num,name,phone,kakao,"+prefixDB+"date,"
					+prefixDB+"place,"+prefixDB+"classify,"
					+prefixDB+"classifyDetail,"+prefixDB+"imgPath,"
					+prefixDB+"more,"+prefixDB+"updatetime) values('"
					+stnum+"','"+name+"','"+phonenum+"','"+kakao+"','"+inputdate+"','"
					+place+"','"+classify+"','"+detail+"','"+filePath+"','"+explain+"','"
					+currentTime+"');";
					
			conn = DriverManager.getConnection(jdbcDriver);
			pstmt = conn.prepareStatement(query);
			/*pstmt.setString(1, stnum);
			pstmt.setString(2, name);
			pstmt.setString(3, phonenum);
			pstmt.setString(4, kakao);
			pstmt.setString(5, inputdate);
			pstmt.setString(6, place);
			pstmt.setString(7, classify);
			pstmt.setString(8, detail);
			pstmt.setString(9, filePath);
			pstmt.setString(10, explain);
			pstmt.setString(11, currentTime);*/
			int rowNum = pstmt.executeUpdate(query);
			if(rowNum<1){
				throw new Exception("데이터를 DB에 입력할 수 없습니다.");
			}
			else{
				return true;
			}
		}catch(Exception e){
			throw new ServletException(e+"   "+query);
		} finally {
			try{
				pstmt.close();
			}
			catch(Exception ignored){
				
			}
			try{
				conn.close();
			}
			catch(Exception ignored){
				
			}
		}
	}
}
