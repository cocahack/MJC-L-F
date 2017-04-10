package admin.notice.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import admin.notice.NoticeDao;
import admin.notice.NoticeList;
import admin.notice.NoticePage;
import jdbc.ConnectionProvider;

public class NoticeSearchService {
	private NoticeDao noticeDao = new NoticeDao();
	private int size = 15;
	public NoticePage getArticlePage(int pageNum, String keyword){
		try(Connection conn = ConnectionProvider.getConnection()){
			List<NoticeList> content = noticeDao.searchList(conn, keyword ,(pageNum-1)*size, size);
			int total = content.size();
			return new NoticePage(total, pageNum, size, content);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
