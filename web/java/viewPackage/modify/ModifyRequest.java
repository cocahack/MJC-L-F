package viewPackage.modify;

public class ModifyRequest {
	private String title, st_num, name;
	private int articleNumber;
	private ModifyValues mv;
	
	
	public ModifyRequest(String title,String name,String st_num, int articleNumber){
		this.title = title;
		this.name = name;
		this.st_num = st_num;
		this.articleNumber = articleNumber;
		mv = null;
	}
	public ModifyRequest(String title,String name, String st_num, int articleNumber, ModifyValues mv){
		this(title,name,st_num,articleNumber);
		this.mv = mv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSt_num() {
		return st_num;
	}
	public void setSt_num(String st_num) {
		this.st_num = st_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getArticleNumber() {
		return articleNumber;
	}
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}
	
	public ModifyValues getMv() {
		return mv;
	}
	public void validate(java.util.Map<String, Boolean>errors){
		if(title==null||title.trim().isEmpty()){
			errors.put("title",Boolean.TRUE);
		}
	}
}
