package android.read;

public class AdminReadItem {
	private String title, writer, content, imgPath, regitDate;
	private int no,hit; 
	
	public AdminReadItem(int no, String title, String writer, String content, String imgPath, String regitDate, int hit) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.content = content;
		this.imgPath = imgPath;
		this.regitDate = regitDate;
		this.hit = hit;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getRegitDate() {
		return regitDate;
	}
	public void setRegitDate(String regitDate) {
		this.regitDate = regitDate;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
}
