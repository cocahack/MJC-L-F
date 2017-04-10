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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/other.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js?ver=1"></script>
    <script src="${pageContext.request.contextPath}/js/notice/notice.js?ver=1.1"></script>
</head>
<body>
	<jsp:include page="../../template/header.jsp" flush="false"/>
	<div id="wrap">
            <jsp:include page="../../template/adminLeft.html" flush="false"/>
            <div class="board">
                <h2>외부 링크</h2>
                <div class="h_deco"></div>
                <ul id="otherlist">
                	<li>
                		<div id="imgbox"><a href="https://www.lost112.go.kr/" title="경찰청 유실물센터"><img src="${pageContext.request.contextPath}/img/police.jpg" alt="경찰청 유실물센터"/></a></div>
                		<div id="content">
                			<h3>경찰청 유실물 종합안내</h3>
                			<p>경찰청에서 운영하는 유실물 서비스입니다. 학교에 찾으시는 물건이 없다면 여기서 검색해보세요.</p>
                		</div>
 					</li>
 					<li>
                		<div id="imgbox"><a href="http://www.seoul.go.kr/v2007/find.html" title="서울시 대중교통 통합분실물센터"><img src="${pageContext.request.contextPath}/img/seoul.jpg" alt="서울시 대중교통 통합분실물센터"/></a></div>
                		<div id="content">
                			<h3>서울시 대중교통 통합분실물센터</h3>
                			<p>서울시에서 운영되는 모든 대중교통에 대한 분실물과 습득물을 처리하고 있습니다.</p>
                		</div>
 					</li>
                </ul>
            </div>
        </div>
        <jsp:include page="../../template/footer.html" flush="false"/>
</body>
</html>