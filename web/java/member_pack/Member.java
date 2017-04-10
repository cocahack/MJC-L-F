package member_pack;

public class Member {
	String name,pwd, st_num;
    
	
	public Member(String name,String pwd, String st_num) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.pwd = pwd;
		this.st_num = st_num;
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

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
