package admin.notice.modify;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class DeleteExistImage  {

	public String fileDelete(HttpServletRequest req,int no, String[] deleteFileNames) {
		// TODO Auto-generated method stub
		String userId = (String)req.getSession().getAttribute("adminId");
		if(userId!=null){
			String fileDir = req.getSession().getServletContext().getRealPath("/uploadImg/notice/notice_No."+no);
			File tmpDirs[] = new File(fileDir).listFiles();
			for(int i=0;i<tmpDirs.length;i++)
			{
				for(int j=0;j<deleteFileNames.length;j++){
					if(tmpDirs[i].getName().equals(deleteFileNames[j])){
						tmpDirs[i].delete();
					}
				}
			}
			String filePath = "";
			tmpDirs = new File(fileDir).listFiles();
			for(int k=0;k<tmpDirs.length;k++){
				filePath += "/uploadImg/notice/notice_No."+no+"/"+tmpDirs[k].getName()+";";
			}
			return filePath;
		}
		else{
			return "";
		}
	}

}
