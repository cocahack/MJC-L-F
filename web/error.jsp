<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:set var="code" value="${errorCode }"/>
<c:if test="${errorCode eq '403' }">
	<c:set var="title" value="403"/>
	<c:set var="head" value="권한이 없습니다"/>
	<c:set var="message" value="해당 서비스에 대한 권한이 없습니다."/>
</c:if> --%>
<!DOCTYPE html>
<html>
<head>
<title>에러</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <!-- <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,600' rel='stylesheet' type='text/css'> -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error/normalize.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error/style.css">
</head>
<body>
<div class="row">
	<div class="container">
		<h1 class="title">${errorCode }</h1>
		<p class="sub-heading"> ${errorHead }</p>
		<hr class="hr-style-404"/>
		<p class="redirect-style-404">${errorMsg}</p>
	</div>
</div>
</body>
</html>