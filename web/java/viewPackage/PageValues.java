package viewPackage;

public class PageValues {
	private String type, beforeMode;
	private int no, pageNo;
	public PageValues(String type, int no, int pageNo,String mode) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.no = no;
		this.pageNo = pageNo;
		this.beforeMode = mode;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getBeforeMode() {
		return beforeMode;
	}
	public void setBeforeMode(String beforeMode) {
		this.beforeMode = beforeMode;
	}
}
