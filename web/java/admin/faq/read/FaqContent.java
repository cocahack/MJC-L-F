package admin.faq.read;

public class FaqContent {
	private String content, imgPath;
	
	public FaqContent(String content,String imgPath) {
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
