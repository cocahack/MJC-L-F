package viewPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchData {
	private String name, stnum, classify, detail, startRegitDate, endRegitDate, startEventDate, endEventDate;
	public SearchData(String name, String stnum, String classify, String detail, String startRegitDate, String endRegitDate, String startEventDate, String endEventDate) throws ParseException{
		this.name = name;
		this.stnum = stnum;
		this.classify = classify;
		this.detail = detail;
		this.startRegitDate = setStartDateTime(startRegitDate);
		this.endRegitDate = setEndtDateTime(endRegitDate);
		this.startEventDate = setStartDate(startEventDate);
		this.endEventDate = setEndDate(endEventDate);
	}
	private String setStartDate(String value)throws ParseException{
		if(value==null||value.equals("")){
			Calendar c = Calendar.getInstance();
			c.set(1970,0,1);
			Date date = new Date(c.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			value = sdf.format(date);
		}
		return value;
	}
	private String setEndDate(String value) throws ParseException{
		if(value==null||value.equals("")){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			value = sdf.format(date);
		}
		return value;
	}
	private String setStartDateTime(String value) throws ParseException{
		if(value==null||value.equals("")){
			Calendar c = Calendar.getInstance();
			c.set(1970, 0, 1, 0, 0, 0);
			Date date = new Date(c.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = sdf.format(date);
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = sdf.format(value);
		}
		return value;
	}
	private String setEndtDateTime(String value) throws ParseException{
		if(value==null||value.equals("")){
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			date = new Date(c.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = sdf.format(date);
		}
		else{
			Calendar c= Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(value);
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			date = new Date(c.getTimeInMillis());
			value = sdf.format(date);
		}
		return value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStnum() {
		return stnum;
	}
	public void setStnum(String stnum) {
		this.stnum = stnum;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getStartRegitDate() {
		return startRegitDate;
	}
	public void setStartRegitDate(String startRegitDate) {
		this.startRegitDate = startRegitDate;
	}
	public String getEndRegitDate() {
		return endRegitDate;
	}
	public void setEndRegitDate(String endRegitDate) {
		this.endRegitDate = endRegitDate;
	}
	public String getStartEventDate() {
		return startEventDate;
	}
	public void setStartEventDate(String startEventDate) {
		this.startEventDate = startEventDate;
	}
	public String getEndEventDate() {
		return endEventDate;
	}
	public void setEndEventDate(String endEventDate) {
		this.endEventDate = endEventDate;
	}
}
