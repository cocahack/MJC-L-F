package exception;

public class ErrorCodeMsg {
	// 4XX error	
	public static final String ERROR_HEAD_401 = "권한이 없습니다";
	public static final String ERROR_MSG_401 = "해당 서비스에 대한 권한이 없습니다. 이상이 있다면 관리자에게 문의해주세요.";
	
	public static final String ERROR_HEAD_403 = "서버로부터 요청이 거절되었습니다.";
	public static final String ERROR_MSG_403 = "서버가 접근을 제한하고 있습니다. 자세한 사항은 관리자에게 문의해주세요.";
	
	public static final String ERROR_HEAD_404 = "페이지를 찾을 수 없습니다";
	public static final String ERROR_MSG_404 = "요청한 페이지에 대한 자료를 찾을 수 없습니다.";
	
}
