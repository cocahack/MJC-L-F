package android.list;

public class ItemList {
	private String title,classify,regitDate,eventDate;
	private int no;
	
	public ItemList(int no, String classify, String title, String eventDate, String regitDate) {
		this.no = no;
		this.title = title;
		this.classify = classify;
		this.eventDate = eventDate;
		this.regitDate = regitDate;
	}
	
	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
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
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
}
