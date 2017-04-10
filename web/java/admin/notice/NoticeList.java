package admin.notice;

public class NoticeList {
	private int no,viewCount;
	private String title, regitDate, writer;
	
	public NoticeList(int no, String title, String writer, int viewCount, String regitDate) {
		// TODO Auto-generated constructor stub
		this.no = no;
		this.title = title;
		this.writer = writer;
		this.viewCount = viewCount;
		this.regitDate = regitDate;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegitDate() {
		return regitDate;
	}
	public void setRegitDate(String regitDate) {
		this.regitDate = regitDate;
	}
}
