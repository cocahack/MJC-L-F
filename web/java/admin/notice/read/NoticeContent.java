package admin.notice.read;


public class NoticeContent {
	private String content,imgPath;
	public NoticeContent(String content,String imgPath) {
		// TODO Auto-generated constructor stub
		this.content = content;
		this.imgPath = imgPath;
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
	
}
