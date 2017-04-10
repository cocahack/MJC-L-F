package viewPackage;

public class Writer {
	private String stnum, name;
	
	public Writer(String stnum, String name){
		this.stnum = stnum;
		this.name = name;
	}
	public String getStnum() {
		return stnum;
	}

	public void setStnum(String stnum) {
		this.stnum = stnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
