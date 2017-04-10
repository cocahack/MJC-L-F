package viewPackage;

public class ViewContent {
	private int no;
	private String classify, detail, name, stnum, phone, kakao;
	private String place, date, imgPath, explain;
	
	public ViewContent(int no, String classify, String detail, String name, String stnum, String phone, String kakao, String place, String date, String imgPath, String explain){
		this.no = no;
		this.classify = classify;
		this.detail = detail;
		this.name = name;
		this.stnum = stnum;
		this.phone = phone;
		this.kakao = kakao;
		this.place = place;
		this.date = date;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
