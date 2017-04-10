<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	/* StringBuffer sb = request.getRequestURL();
	String url = sb.toString(); */
	Cookie urlCookie = new Cookie("historyURL",request.getServletPath());
	response.addCookie(urlCookie);
%>
<aside id="top_bar">
        <div>
            <ul>
                <li><a href="http://www.mjc.ac.kr">Home</a></li>
                <c:if test="${(sessionScope.AUTH == ''||sessionScope.AUTH == null) && sessionScope.authAdmin == null}">
                	<li><a href="${pageContext.request.contextPath}/initLogin?type=member" id="login">Login</a></li>
                	<li><a href="${pageContext.request.contextPath}/initLogin?type=admin" id="admin">Admin</a></li>
                </c:if>
                <c:if test="${sessionScope.AUTH == 'OK' || sessionScope.authAdmin ne null}">
                <li><a href="${pageContext.request.contextPath}/logout" id="logout">Logout</a></li>
                </c:if>
            </ul>
        </div>
    </aside>
    <header class="limitWidth">
            <h1 id="head"><a href="${pageContext.request.contextPath}/index.do"><img src="img/logo_ver2.png" alt="명지전문대학 로고" class="logo"></a></h1>
            <nav id="mainNav">
                <ul>
                    <li><a id="onRegit" href="${pageContext.request.contextPath}/regit.do">등록</a></li>
                    <li><a href="${pageContext.request.contextPath}/list.do?type=lost" id="onList">목록</a>
                        <ul class="list">
                            <li><a href="${pageContext.request.contextPath}/list.do?type=lost">분실물 목록</a></li>
                            <li><a href="${pageContext.request.contextPath}/list.do?type=found">습득물 목록</a></li>
                        </ul>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/notice.do" id="onSub">커뮤니티</a>
                        <ul class="subNav">
                        <li><a href="${pageContext.request.contextPath}/notice.do">공지사항</a></li>
                        <li><a href="${pageContext.request.contextPath}/faq.do">FAQ</a></li>
                        <li><a href="${pageContext.request.contextPath}/loc.do">방문하기</a></li>
                        <li><a href="${pageContext.request.contextPath}/other.do">Other</a></li>
                </ul>
                    </li>
                </ul>
            </nav>
        </header>
        <div id="navLine" class="fullWidth"></div>