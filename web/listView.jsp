<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="type" value="${param.type }"/>
<c:if test="${type eq null }">
	<c:set var="type" value="lost"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>분실물 목록</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/community.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/view.js"></script>
</head>
<body>
	<jsp:include page="template/header.jsp" flush="false"/>
	<div id="wrap">
        <jsp:include page="template/leftSide.html" flush="false"/>
        <div class="board">
            <div class="search">
                <div class="bar">
                    <!--<span>상세 검색</span>-->
                    <a id="slidedown" href="#"><img src="${pageContext.request.contextPath}/img/arrow_down.png" alt="slidedown_button"></a>
                </div>
                <div class="detail_search">
                    <form class="search_form" method="get" action="search.do">
                    	<input type="hidden" name="type" value="${type }">
                        <ul>
                            <li>
                                <span class="divide">
                                    <label for="name">작성자</label>
                                    <input type="text" name="name" id="name" class="search_inputText">
                                </span>
                                <span class="divide">
                                    <label for="stnum">작성자 학번</label>
                                    <input type="text" name="stnum" id="stnum" class="search_inputText" maxlength="10">
                                </span>
                            </li>
                            <li>
                                <span class="divide">
                                    <label for="classify">분류</label>
                                    <select name="classify" id="classify">
                                        <option value="">선택하세요</option>
                            			<option value="핸드폰">핸드폰</option>
                            			<option value="지갑">지갑</option>
                            			<option value="카드">카드</option>
                            			<option value="의류">의류</option>
                            			<option value="신발">신발</option>
                            			<option value="가방">가방</option>
                            			<option value="학생증">학생증</option>
                            			<option value="usb">USB</option>
                            			<option value="기타">기타</option>
                                    </select>
                                </span>
                                <span class="divide">
                                    <label for="classify">세부 분류</label>
                                    <input type="type" id="detail" name="detail" class="search_inputText"
                                    placeholder="물건 이름을 입력하세요">
                                </span>
                            </li>
                            <li>
                                <span class="divide">
                                    <label for="start_date">분실 날짜</label>
                                    <input type="text" class="date search_inputText" id="start_date" name="start_date">
                                    <label for="end_date" class="swun_dash">~</label>
                                    <input type="text" class="date search_inputText" id="end_date" name="end_date">
                                </span>
                                <span class="divide">
                                    <label for="start_regitdate">등록일</label>
                                    <input type="text" class="date search_inputText" id="start_regitdate" name="start_regitdate">
                                    <label for="end_regitdate" class="swun_dash">~</label>
                                    <input type="text" class="date search_inputText" id="end_regitdate" name="end_regitdate">
                                </span>
                            </li>
                            <li>
                                <input type="hidden" name="type" value="${param.type }">
                                <input type="hidden" name="searchtype" value="detailSearch">
                                <button id="submit" class="search_button">검색</button>
                            </li>
                        </ul>
                        
                    </form>   
                </div>
            </div>
            <table>
                <tr class="board_head">
                    <th>번호</th>
                    <th>물품명</th>
                    <th>분실일</th>
                    <th>등록일/시간</th>
                </tr>
                <c:if test="${articlePage.hasNoArticles()}">
                	<tr>
                		<td colspan="4">목록이 없습니다.</td>
                	</tr>
                </c:if>
                <c:forEach var="article" items="${articlePage.content}">
                	<tr class="board_body">
                		<td>
                			${article.no}
                		</td>
                		<td>
                			<span class="classify">[${article.classify }]</span>
                			<a href="read.do?type=${param.type }&no=${article.no}&pageNo=${articlePage.currentPage}">
                				<c:out value="${article.detailClassify}"/>
                			</a>
                		</td>
                		<td>
                			${article.eventDate }
                		</td>
                		<td>
                			${article.regitDate }
                		</td>
                	</tr>
                </c:forEach>
            </table>
            <c:if test="${articlePage.hasArticles() }">
    	<div id="pagination">
    		<ul>
    			<%-- <c:if test="${articlePage.startPage>5 }">
    				<li><a href="list.do?type=${ param.type}&pageNo=${articlePage.startPage-5}"><</a></li>
    			</c:if> --%>
    			<!--시작 페이지와 현재 페이지  -->
    			<c:set var="startPage" value="${articlePage.startPage}"/>
    			<c:set var="currentPage" value="${param.pageNo ne null ? param.pageNo : 1 }"/>
    			<!--페이징처리 태그  -->
    			<c:choose>
    				<c:when test="${param.searchtype eq 'normalSearch' }">
    					<c:set var="queryString" value="keyword=${param.keyword }&type=${param.type }&searchtype=normalSearch"/>
    					<c:if test="${articlePage.startPage>5 }">
    						<li><a href="simplesearch.do?type=${ param.type}&pageNo=${articlePage.startPage-5}&${queryString}">&lt;</a></li>
    					</c:if>
    					<c:forEach var="pNo" begin="${startPage}" end="${articlePage.endPage }">
    						<c:choose>
    							<c:when test="${pNo eq currentPage}">
                					<li><a href="simplesearch.do?type=${param.type}&pageNo=${pNo}&${queryString}" class="current">${pNo}</a></li>
                				</c:when>
                				<c:otherwise>
                					<li><a href="simplesearch.do?type=${param.type}&pageNo=${pNo}&${queryString}">${pNo}</a></li>
                				</c:otherwise>
    						</c:choose>
                		</c:forEach>
                		<c:if test="${articlePage.endPage<articlePage.totalPages }">
                			<li><a href="simplesearch.do?type=${param.type }&pageNo=${articlePage.startPage+5}&${queryString}">></a></li>
    					</c:if> 
    				</c:when>
    				<c:when test="${param.searchtype eq 'detailSearch' }">
    					<c:set var="queryString" value="name=${param.name }&stnum=${param.stnum }&classify=${param.classify }&detail=${param.detail }&start_date=${param.start_date }&end_date=${param.end_date }&start_regitdate=${start_regitdate }&end_regitdate=${end_regitdate }&searchtype=detailSearch"/>
    					<c:if test="${articlePage.startPage>5 }">
    						<li><a href="search.do?type=${ param.type}&pageNo=${articlePage.startPage-5}&${queryString}">%lt;</a></li>
    					</c:if>
    					<c:forEach var="pNo" begin="${startPage}" end="${articlePage.endPage }">
    						<c:choose>
    							<c:when test="${pNo eq currentPage}">
                					<li><a href="search.do?type=${param.type}&pageNo=${pNo}&${queryString}" class="current">${pNo}</a></li>
                				</c:when>
                				<c:otherwise>
                					<li><a href="search.do?type=${param.type}&pageNo=${pNo}&${queryString}">${pNo}</a></li>
                				</c:otherwise>
    						</c:choose>
                		</c:forEach>
                		<c:if test="${articlePage.endPage<articlePage.totalPages }">
                			<li><a href="search.do?type=${param.type }&pageNo=${articlePage.startPage+5}&${queryString}">></a></li>
    					</c:if> 
    				</c:when>
    				
    				<c:otherwise>
    					<c:if test="${articlePage.startPage>5 }">
    						<li><a href="list.do?type=${ param.type}&pageNo=${articlePage.startPage-5}">%lt;</a></li>
    					</c:if>
    					<c:forEach var="pNo" begin="${startPage}" end="${articlePage.endPage }">
    						<c:choose>
    							<c:when test="${pNo eq currentPage}">
                					<li><a href="list.do?type=${param.type }&pageNo=${pNo}" class="current">${pNo}</a></li>
                				</c:when>
                				<c:otherwise>
                					<li><a href="list.do?type=${param.type }&pageNo=${pNo}">${pNo}</a></li>
                				</c:otherwise>
    						</c:choose>
                		</c:forEach>
                		<c:if test="${articlePage.endPage<articlePage.totalPages }">
                			<li><a href="list.do?type=${param.type }&pageNo=${articlePage.startPage+5}">&gt;</a></li>
    					</c:if> 
    				</c:otherwise>
    			</c:choose>
                <%-- <c:if test="${articlePage.endPage<articlePage.totalPages }">
                	<li><a href="list.do?type=${param.type }&pageNo=${articlePage.startPage+5}">></a></li>
    			</c:if> --%>
            </ul>
    	</div>
    </c:if>
            <div id="bottom_button">
                <form action="simplesearch.do">
                    <input type="text" class="search_inputText" id="keyword" name="keyword">
                    <input type="hidden" name="searchtype" value="normalSearch">
                    <input type="hidden" name="type" value="${param.type }">
                    <button class="search_button">검색</button>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="template/footer.html" flush="false"/>
</body>
</html>