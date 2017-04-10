package android.list;

public class AdminMenuList {
	private String title, regitDate;
	private int no, hit;
	
	public AdminMenuList(int no, String title, String regitDate, int hit) {
		this.no = no;
		this.title = title; 
		this.regitDate = regitDate;
		this.hit = hit;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
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
