package mainList;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class ListValues {
	private String classify, title,regitDate;
	private int no;
	
	public ListValues(String classify, String title, String regitDate, int no) {
		// TODO Auto-generated constructor stub
		this.classify = classify;
		this.title = title;
		this.regitDate = regitDate;
		this.no = no;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
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
