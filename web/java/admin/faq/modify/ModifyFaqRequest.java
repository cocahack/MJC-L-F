package admin.faq.modify;

public class ModifyFaqRequest {
	private String title, writer;
	private int articleNumber;
	private ModifyFaqValues mfv;
	
	public ModifyFaqRequest(String title,String writer, int articleNumber) {
		this.title= title;
		this.writer= writer;
		this.articleNumber =articleNumber;
		mfv = null;
	}
	public ModifyFaqRequest(String title,String writer, int articleNumber,ModifyFaqValues mfv) {
		this(title,writer,articleNumber);
		this.mfv = mfv;
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
	public ModifyFaqValues getMfv() {
		return mfv;
	}
	public void setMfv(ModifyFaqValues mfv) {
		this.mfv = mfv;
	}
}
