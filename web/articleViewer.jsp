<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<c:set var="historyURL" value="${header.REFERER }"/>
<c:set var="type" value="${param.type }"/>
<c:set var="no" value="${param.no }"/>
<c:set var="pageNo" value="${param.pageNo }"/>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>${articleData.viewContent.detail }</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pgwmodal.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/community.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/pgwmodal.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js"></script>
</head>
<body>
	<jsp:include page="template/header.jsp" flush="false"/>
	<div id="wrap">
        <jsp:include page="template/leftSide.html"></jsp:include>
        <div class="board">
            <article class="viewFrame">
                <h2>세부 내용</h2>
                <div class="content_wrapper">
                    <div class="half">
                    	<c:choose>
                    		<c:when test="${empty articleData.viewContent.imgPath}">
                    			<img src="${pageContext.request.contextPath}/img/image-not-found.png" alt="업로드된 이미지가 없습니다.">  			
                    		</c:when>
                    		<c:otherwise>
                    			<img class="regit_img" src="${pageContext.request.contextPath}${articleData.viewContent.imgPath}" alt="물건 이미지">
                    		</c:otherwise>
                    	</c:choose>
                        
                    </div>
                    <div class="half">
                        <ul>
                            <li>
                               <p class="category">번호</p>
                               <p class="category_content">${articleData.viewContent.no }</p>
                            </li>
                            <li>
                               <p class="category">분류</p>
                               <p class="category_content">${articleData.viewContent.classify }</p>
                            </li>
                            <li>
                               <p class="category">세부 분류</p>
                               <p class="category_content">${articleData.viewContent.detail }</p>
                            </li>
                            <li>
                               <p class="category">학번</p>
                               <p class="category_content">${articleData.viewContent.stnum }</p>
                            </li>
                            <li>
                               <p class="category">이름</p>
                               <p class="category_content">${articleData.viewContent.name }</p>
                            </li>
                            <li>
                               <p class="category">전화번호</p>
                               <p class="category_content">${articleData.viewContent.phone }</p>
                            </li>
                            <li>
                               <p class="category">Kakao</p>
                               <p class="category_content">${articleData.viewContent.kakao }</p>
                            </li>
                            <li>
                               <p class="category">분실일</p>
                               <p class="category_content">${articleData.viewContent.date }</p>
                            </li>
                            <li>
                               <p class="category">분실장소</p>
                               <p class="category_content">${articleData.viewContent.place }</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </article>
            <article class="viewFrame">
                <h2>특이사항</h2>
                <div class="textarea" disabled="disabled">
                    <u:pre value='${articleData.viewContent.explain }'/>
                </div>
            </article>
            <div class="button_div">
            	<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo }"/>
            	<div class="left">
                <div class="search_button">
                	
                	<c:choose>
                		<c:when test="${pv ne null}">
                			<a href="list.do?type=${pv.type }&pageNo=${pv.pageNo}">목록</a>
                		</c:when>
                		<c:when test="${param.pageNo eq null }">
                			<a href="list.do?type=${param.type }&pageNo=1">목록</a>
                		</c:when>
                		<c:when test="${notAvailable eq 'notAvailable' }">
                			<a href="list.do?type=${param.type }&pageNo=1">목록</a>
                		</c:when>
                		<c:otherwise>
                			<a href="${historyURL }">목록</a>
                		</c:otherwise>
                	</c:choose>
                	
                </div>
                </div>
                <div class="right">
                <div class="search_button"><a href="modify.do?type=${type }&no=${no}&pageNo=${pageNo}">수정</a></div>
                <div class="search_button"><a href="remove.do?type=${type }&no=${no }&pageNo=${pageNo}">삭제</a></div>
                </div>
			</div>
        </div>
    </div>
    <jsp:include page="template/footer.html" flush="false"/>
    <c:if test="${notAvailable eq 'notAvailable' }">
	<div id="modalContent" style="display: none;">
    <strong>${errorMsg }</strong>
	</div>
	<script>
		$.pgwModal({
    		target: '#modalContent',
    		title: "${errorTitle}",
    		maxWidth: 400
		});
	</script>
</c:if>
</body>
</html>