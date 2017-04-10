package testarea;

import java.io.File;

public class FileTest {
	public static void main(String[] args) {
		File file = new File(".\\uploadImg\\45464564");
		if(!file.exists()){
			System.out.println(file.exists());
			file.mkdir();
		}
		try {
		       System.out.println ("Current dir : " + file.getCanonicalPath());
		       
		} catch(Exception e) {
		       e.printStackTrace();
		}
	}
}
