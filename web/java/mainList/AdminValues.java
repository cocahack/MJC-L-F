package mainList;

public class AdminValues {
	private String title, regitDate;
	private int no;
	
	public AdminValues(int no, String title, String regitDate) {
		this.no = no;
		this.title = title;
		this.regitDate = regitDate;
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
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
}
