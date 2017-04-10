package admin.faq.modify;

public class ModifyFaqValues {
	private String content, filePaths, regitDate;
	
	
	public ModifyFaqValues(String content, String filePaths, String regitDate) {
		this.content = content;
		this.filePaths = filePaths;
		this.regitDate = regitDate;
	}
	public void setRegitDate(String regitDate) {
		this.regitDate = regitDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilePaths() {
		return filePaths;
	}
	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
	}
	public String getRegitDate() {
		return regitDate;
	}
}
