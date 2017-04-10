package viewPackage;

import java.util.Date;

public class ArticleList {
	private Integer no;
	private String classify;
	private String detailClassify;
	private String eventDate;
	private String regitDate;
	private Writer writer;

	public ArticleList(Integer no, String classify, String detailClassify, String eventDate, String regitDate, Writer writer) {
		// TODO Auto-generated constructor stub
		this.no = no;
		this.classify = classify;
		this.detailClassify = detailClassify;
		this.eventDate = eventDate;
		this.regitDate = regitDate;
		this.writer =  writer;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getDetailClassify() {
		return detailClassify;
	}
	public void setDetailClassify(String detailClassify) {
		this.detailClassify = detailClassify;
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
	public Writer getWriter() {
		return writer;
	}
	public void setWriter(Writer writer) {
		this.writer = writer;
	}
}
