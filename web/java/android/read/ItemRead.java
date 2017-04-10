package android.read;

public class ItemRead {
	private int no;
	private String classify, detail, name, stnum, phone, kakao;
	private String place, eventDate, regitDate, imgPath, explain;
	
	public ItemRead(int no, String classify, String detail, String name, String stnum, String phone, String kakao, String place, String eventDate,String regitDate, String imgPath, String explain){
		this.no = no;
		this.classify = classify;
		this.detail = detail;
		this.name = name;
		this.stnum = stnum;
		this.phone = phone;
		this.kakao = kakao;
		this.place = place;
		this.eventDate = eventDate;
		this.regitDate = regitDate;
		this.imgPath = imgPath;
		this.explain = explain;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getKakao() {
		return kakao;
	}
	public void setKakao(String kakao) {
		this.kakao = kakao;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
}
