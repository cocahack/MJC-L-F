<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>오시는 길</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/community.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice.css?ver=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/locationInfo.css?ver=1.2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAtkDI9VFvNkgbw-dc2IWqgKRtAEONUKDg&sensor=false" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js"></script>
    <script src="${pageContext.request.contextPath}/js/notice/notice.js"></script>
    <script src="${pageContext.request.contextPath}/js/locationInfo.js?ver=1.2"></script>
</head>
<body>
	<jsp:include page="../../template/header.jsp" flush="false"/>
	<div id="wrap">
            <jsp:include page="../../template/adminLeft.html" flush="false"/>
            <div class="board">
                <h2>오시는 길</h2>
                <div class="h_deco"></div>
                <div id="map_canvas">
                </div>
                <br>
                <p>
                	본관 1층에서 우측 하나은행 ATM기 옆에 위치하고 있습니다. 학교 곳곳에서 발견된 습득물들을 보관하고 있습니다.
                </p>
                <br>
                <p>
                	<img id="icon" src="img/telephone.png" alt="telephone icon" />학생서비스센터 : 02-300-0000
                </p>
            </div>
        </div>
        <jsp:include page="../../template/footer.html" flush="false"/>
</body>
</html>