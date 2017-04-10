package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns="/looof.do"
		)
public class Looof extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Connection conn = ConnectionProvider.getConnection())
		{
			PrintWriter out = resp.getWriter();
			long startTime = System.currentTimeMillis();
			out.println("작업 시작!");
			out.flush();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO student (st_num, st_pwd, st_name,st_age,st_gender,st_code) VALUES (?, '1234', '사용자','20','남','081')");
			for(int i=0;i<=6;++i)
			{
				for(int j=1;j<=200;++j){
					String stnum = "201"+i+"081";
					if(j<10)
						stnum+="00"+j;
					else if(j<100)
						stnum+="0"+j;
					else
						stnum+=j;
					pstmt.setString(1, stnum);
					int row = pstmt.executeUpdate();
				}
			}
			long endTime = System.currentTimeMillis();
			out.println("작업 끝!");
			out.println("소요시간: "+(endTime-startTime)/1000.0f+"초");
			out.flush();
			out.close();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
