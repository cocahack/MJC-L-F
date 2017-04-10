package viewPackage.modify;

public class ModifyValues {
	String phone, kakao, place, classify, detail, imgPath, explain, eventDate, type;

	public ModifyValues(String phone, String kakao, String place, String classify, String detail, String imgPath, String explain, String eventDate, String type){
		this.phone = phone;
		this.kakao = kakao;
		this.place = place;
		this.classify = classify;
		this.detail = detail;
		this.imgPath = imgPath;
		this.eventDate = eventDate;
		this.explain = explain;
		this.type = type;
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

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
