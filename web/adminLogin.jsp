<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String beforeURL;
	if(request.getAttribute("beforeURL")==null){
		beforeURL = request.getHeader("REFERER");
	}
	else{
		beforeURL = (String)request.getAttribute("beforeURL");
	}
	
%>
<!DOCTYPE html>
<html>
<head>
   <!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
    <title>관리자 로그인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminForm.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jsbn.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/rsa.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/prng4.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/rng.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>
</head>
<body>
   <div class="login-page">
  <div class="form">  
      <form name="frm" class="login-form">
		<input type="text" id="id" name="id" placeholder="아이디"/>
      <input type="password" id="pwd" name="pwd" placeholder="비밀번호"/>
      <input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}" />
            <input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}" />
      <button id="btn" href="#" onclick="validateEncryptedForm(); return false;">로그인</button>
      <p class="message">COPYRIGHT(C)2016 MYONGJI COLLEGE ALL RIGHTS RESERVED.  <a href="#">개인정보처리방침</a></p>
    </form>
    <form id="securedLoginForm" name="securedLoginForm" action="<%=request.getContextPath()%>/admin.do" method="post" style="display: none;">
            <input type="hidden" name="securedUsername" id="securedUsername" value="" />
            <input type="hidden" name="securedPassword" id="securedPassword" value="" />
            <input type="hidden" name="beforeURL" value="<%=beforeURL %>"/>
        </form>
  </div>
</div>
<c:if test="${requestScope.AUTH=='FAIL'}">
	<script>
		alert('로그인에 실패했습니다. 아이디와 비밀번호를 다시 입력하세요.');
	</script>
</c:if>
</body>
</html>