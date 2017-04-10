package admin.notice.modify;

public class ModifyNoticeRequest {
	private String title,writer;
	private int articleNumber;
	private ModifyNoticeValues mnv;
	
	public ModifyNoticeRequest(String title, String writer, int articleNumber) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.writer = writer;
		this.articleNumber = articleNumber;
		mnv = null;
	}
	public ModifyNoticeRequest(String title, String writer, int articleNumber, ModifyNoticeValues mnv) {
		// TODO Auto-generated constructor stub
		this(title,writer,articleNumber);
		this.mnv =mnv;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getArticleNumber() {
		return articleNumber;
	}
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}
	public ModifyNoticeValues getMnv() {
		return mnv;
	}
	public void setMnv(ModifyNoticeValues mnv) {
		this.mnv = mnv;
	}
}
