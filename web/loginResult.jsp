<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
   		String auth = (String)session.getAttribute("AUTH");
    %>
<!DOCTYPE html>
<html>
<head>
<title>로그인 결과</title>
</head>
<body>
	<%
		if(auth=="OK"){
	%>
	<p>로그인에 성공하였습니다</p>
	<%
		}else{
	%>
	<p>로그인에 실패하였습니다</p>
	<%
		}
	%>
</body>
</html>