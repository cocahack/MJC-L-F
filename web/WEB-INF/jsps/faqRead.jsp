<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<c:set var="imgPathAttr" value="${faqData.faqContent.imgPath }"/>
<c:set var="imgPathArr" value="${fn:split(imgPathAttr,';') }"/>
<c:set var="historyURL" value="${header.REFERER }"/>
<!DOCTYPE html> 
<html>
<head>
<title>${faqData.faqList.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/community_for_read.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeRead.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer_for_read.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/notice/notice.js?ver=1"></script>
</head>
<body>
	<jsp:include page="../../template/header.jsp" flush="false"/>
	<div id="wrap">
		<jsp:include page="../../template/adminLeft.html" flush="false"/>
		<div class="board">
		
			<ul>
                <li class="content_header">
                    <span class="title">
                        ${faqData.faqList.title }
                    </span>
                    <span class="name">
                        ${faqData.faqList.writer }
                    </span>
                    <span class="regitDate">
                        ${faqData.faqList.regitDate }
                    </span>
                    <span class="count">
                    	조회 :${faqData.faqList.viewCount}	
                    </span>
                </li>
                <li class="content_body">
                	<c:forEach begin="0" end="${fn:length(imgPathArr)-1}" var="i">
                	<c:choose>
                			<c:when test="${imgPathArr[i]eq''}">
                			</c:when>
                			<c:otherwise>
                				<img src="${pageContext.request.contextPath}${imgPathArr[i]}" alt="FAQ 이미지 ${i}" class="content_img">
                			</c:otherwise>
                		</c:choose>
	                	
                	</c:forEach>
                    
                    <p><u:pre value='${faqData.faqContent.content }'/></p>
                </li>
                <li class="content_footer">
                    <div class="left">
                    	<c:choose>
                			<c:when test="${param.pageNo eq null }">
                				<a href="faq.do?type=${param.type }&pageNo=1" class="search_button">목록</a>
                			</c:when>
                			<c:when test="${fn:contains(historyURL,'faqSearch')}">
                				<a href="${historyURL }" class="search_button">목록</a>
                			</c:when>
                			<c:when test="${param.pageNo ne null }">
                				<a href="faq.do?type=${param.type }&pageNo=${param.pageNo}" class="search_button">목록</a>
                			</c:when>
                			<c:otherwise>
                				<a href="${historyURL }" class="search_button">목록</a>
                			</c:otherwise>
                		</c:choose>
                    </div>
                    <div class="right">
                        <a class="search_button" href="faqModify.do?no=${param.no }&pageNo=${param.pageNo}">수정</a>
                        <a class="search_button" href="faqDelete.do?no=${param.no }&pageNo=${param.pageNo}">삭제</a>
                    </div>
                    
                </li>
            </ul>
            <jsp:include page="../../template/footer.html"/>
		</div>
	</div>
	
	<c:if test="${notAvailable eq 'notAvailable' }">
		<div id="modalContent" style="display: none;">
    	<strong>${errorMsg }</strong>
		</div>
			<script>
				$.pgwModal({
    				target: '#modalContent',
    				title: '${errorTitle}',
    				maxWidth: 400
				});
			</script>
		</c:if>
		
</body>
</html>