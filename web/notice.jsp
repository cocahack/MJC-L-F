<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>공지사항</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/community.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice.css?ver=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js"></script>
    <script src="${pageContext.request.contextPath}/js/notice/notice.js"></script>
</head>
<body>
	<jsp:include page="template/header.jsp" flush="false"/>
	<div id="wrap">
            <jsp:include page="template/adminLeft.html" flush="false"/>
            <div class="board">
                <table>
                    <tr class="board_head">
                        <th>번호</th>
                        <th>제목</th>
                        <th>등록일</th>
                        <th>조회수</th>
                    </tr>
                    <c:if test="${noticeList.hasNoArticles() }">
                    	<tr>
                    		<td colspan="4">목록이 없습니다.</td>
                    	</tr>
                    </c:if>
                    <c:forEach var="notice" items="${noticeList.content }">
                    	<tr class="board_body">
                    		<td>
                    			${notice.no}
                    		</td>
                    		<td>
                    			<a href="noticeRead.do?no=${notice.no }&pageNo=${noticeList.currentPage}">${notice.title }</a>
                    		</td>
                    		<td>
                    			${notice.regitDate }
                    		</td> 
                    		<td>
                    			${notice.viewCount }
                    		</td>
                    	</tr>
                    </c:forEach>
                </table>
                <div id="pagination">
                    <ul>
                    	<c:set var="startPage" value="${noticeList.startPage }"/>
                    	<c:set var="currentPage" value="${param.pageNo ne null ? param.pageNo : 1 }"/>
                    	<c:choose>
                    		<c:when test="${param.searchtype eq 'search' }">
                    			<c:if test="${noticeList.hasArticles() }">
                    				<c:set var="queryString" value="keyword=${param.keyword }&searchtype=search"/>
                    				<c:if test="${noticeList.startPage>5 }">
                    					<li><a href="noticeSearch.do?pageNo=${noticeList.startPage-5 }&${queryString}">&lt;</a></li>
                    				</c:if>
                    				<c:forEach var="pNo" begin="${startPage }" end="${noticeList.endPage }">
                    					<c:choose>
                    						<c:when test="${pNo eq currentPage }">
                    							<li><a href="noticeSearch.do?pageNo=${pNo }&${queryString}" class="current">${pNo }</a></li>
                    						</c:when>
                    						<c:otherwise>
                    							<li><a href="noticeSearch.do?pageNo=${pNo }&${queryString }">${pNo }</a></li>
                    						</c:otherwise>
                    					</c:choose>
                    				</c:forEach>
                    				<c:if test="${noticeList.endPage<noticeList.totalPages }">
                    					<li><a href="noticeSearch.do?pageNo=${noticeList.startPage+5 }&${queryString }">&gt;</a></li>
                 					</c:if>
                 				</c:if>
                    		</c:when> 
                    		<c:otherwise>
                    			<c:if test="${noticeList.hasArticles() }">
                    				<c:if test="${noticeList.startPage>5 }">
                    					<li><a href="notice.do?pageNo=${noticeList.startPage-5 }">&lt;</a></li>
                    				</c:if>
                    				<c:forEach var="pNo" begin="${startPage }" end="${noticeList.endPage }">
                    					<c:choose>
                    						<c:when test="${pNo eq currentPage }">
                    							<li><a href="notice.do?pageNo=${pNo }" class="current">${pNo }</a></li>
                    						</c:when>
                    						<c:otherwise>
                    							<li><a href="notice.do?pageNo=${pNo }">${pNo }</a></li>
                    						</c:otherwise>
                    					</c:choose>
                    				</c:forEach>
                    				<c:if test="${noticeList.endPage<noticeList.totalPages }">
                    					<li><a href="notice.do?pageNo=${noticeList.startPage+5 }">&gt;</a></li>
                 					</c:if>
                 				</c:if>
                    		</c:otherwise>
                    	</c:choose>
                    </ul>
                </div>
                <div id="bottom_button">
                    <form action="noticeSearch.do">
                        <input type="text" class="search_inputText" id="keyword" name="keyword">
                        <input type="hidden" name="searchtype" value="normalSearch">
                        <button type="submit" class="search_button">검색</button>
                        <c:if test="${sessionScope.authAdmin ne null }">
                    		<a id="writebtn" class="search_button" href="noticeRegit.in">글쓰기</a>
                    	</c:if>
                    </form>
                    
                </div>
            </div>
        </div>
        <jsp:include page="template/footer.html" flush="false"/>
</body>
</html>