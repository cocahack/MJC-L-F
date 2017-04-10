<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>명지전문대 유실물 관리센터</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
</head>
<body>
	<jsp:include page="template/header.jsp" flush="false"/>
        <div id="main">
            <div class="limitWidth">
                <div id="visual">
                    <img src="${pageContext.request.contextPath}/img/bg.png">
                </div>
                <div>
                    <div id="content">
                        <div class="tab_container">
                    <ul>
                        <li><h2 id="notice"><a href="#" class="tab_active">공지사항</a></h2>
                            <div class="main_notice">
                                <ul>
                                	<c:forEach var="noticeItem" items="${noticeItem }">
                                		<li>
                                			<a href="noticeRead.do?no=${noticeItem.no }"> ${noticeItem.title }</a>
                                			<span>[${noticeItem.regitDate}]</span>
                                		</li>
                                	</c:forEach>
                                </ul>
                            </div>
                        </li>
                        <li><h2 id="qna"><a href="#" >Q&amp;A</a></h2>
                            <div class="main_notice">
                                <ul>
                                    <c:forEach var="faqItem" items="${faqItem }">
                                		<li>
                                			<a href="faqRead.do?no=${faqItem.no }"> ${faqItem.title }</a>
                                			<span>[${faqItem.regitDate}]</span>
                                		</li>
                                	</c:forEach>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                    </div>
                    <div id="p_condition">
                        <div class="tab_container">
                    <ul>
                        <li><h2 id="loss"><a href="#" class="tab_active">분실물</a></h2>
                            <div class="main_notice">
                                <ul>
                                	<c:forEach var="lostItem" items="${lostItem }">
                                		<li>
                                			<a href="read.do?type=lost&no=${lostItem.no }"><p>[${lostItem.classify }]</p> ${lostItem.title }</a>
                                			<span>[${lostItem.regitDate}]</span>
                                		</li>
                                	</c:forEach>
                                </ul>
                            </div>
                        </li>
                        <li><h2 id="found"><a href="#">습득물</a></h2>
                            <div class="main_notice">
                                <ul>
                                    <c:forEach var="foundItem" items="${foundItem }">
                                		<li>
                                			<a href="read.do?type=found&no=${foundItem.no }"><p>[${foundItem.classify }]</p> ${foundItem.title }</a>
                                			<span>[${foundItem.regitDate}]</span>
                                		</li>
                                	</c:forEach>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                    </div>
                </div>
            </div>
        </div>
    
    <jsp:include page="template/footer.html" flush="false"/>
</body>
</html>