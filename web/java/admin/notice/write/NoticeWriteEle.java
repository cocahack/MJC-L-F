package admin.notice.write;

public class NoticeWriteEle {
	String name, filePath, regitDate, title, content;

	public NoticeWriteEle(String title,String content, String name, String filePath, String regitDate){
		this.title = title;
		this.content = content;
		this.name = name;
		this.filePath = filePath;
		this.regitDate = regitDate;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRegitDate() {
		return regitDate;
	}

	public void setRegitDate(String regitDate) {
		this.regitDate = regitDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
