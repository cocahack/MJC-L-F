<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>분실물/습득물 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/regit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/regit.js?ver=1.1" type="text/javascript"></script>
</head>
<body>
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
            <h1 id="head"><a href="${pageContext.request.contextPath}/index.do"><img src="img/logo_ver2.png" alt="명지전문대학 로고"></a></h1>
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
	<div id="wrap">
            <div class="form">
               <h2>분실물/습득물 등록</h2>
               <div class="h_deco"></div>
                <form class="regit_form form ver1" action="regit.do" method="post" enctype="multipart/form-data">
                <ul>
                    <li>
                        <label for="loss"><span class="required">*</span>분류</label>
                        <label for="loss">분실물</label><input name="type" type="radio" id="loss" value="loss" required>
                        <label for="found">습득물</label><input name="type" type="radio" id="found" value="found" required>
                    </li>
                    <li>
                        <div class="divide">
                            <label for="stnum"><span class="required">*</span>학번</label><input type="text" id="stnum" name="stnum" value="${sessionScope.stnum}" maxlength="10">
                        </div>
                        <div class="divide">
                            <label for="name"><span class="required">*</span>이름</label><input type="text" id="name" name="name" value="${sessionScope.name}">
                        </div>
                    </li>
                    <li>
                        <div class="divide">
                            <label for="phonenum1"><span class="select-required">*</span>전화번호</label>
                            <input type="text" id="phonenum0" name="phonenum0" value="010" maxlength="3">
                            <label for="phonenum1" class="slash">-</label>
                            <input type="text" id="phonenum1" name="phonenum1" maxlength="4">
                            <label for="phonenum2" class="slash">-</label>
                            <input type="text" id="phonenum2" name="phonenum2"
                        maxlength="4">
                        	<input type="hidden" id="fullphonenum" name="fullphonenum">
                        </div>
                        <div class="divide">
                            <label for="kakao"><span class="select-required">*</span>Kakao ID</label><input type="text" id="kakao" name="kakao">
                        </div>
                    </li>
                    <li>
                        <div class="divide">
                            <label for="year"><span class="required">*</span>분실/습득일</label>
                            <input type="text" id="year" name="year" pattern="[0-9]{4}" required title="연도를 잘못 입력하셨습니다." maxlength="4">
                            <label for="year" class="slash">년</label>
                            <div class="select-style dateClass">
                                <select id="month" name="month" required>
                                <option value="">-</option>
                                <option value="01">1</option>
                                <option value="02">2</option>
                                <option value="03">3</option>
                                <option value="04">4</option>
                                <option value="05">5</option>
                                <option value="06">6</option>
                                <option value="07">7</option>
                                <option value="08">8</option>
                                <option value="09">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                            </div>
                            <label for="month" class="slash">월</label>
                            <div class="select-style dateClass">
                            <select id="day" name="day" required>
                                <option value="">-</option>
                            </select>
                            </div>
                            <label for="day" class="slash">일</label>
                            <input type="hidden" name="fullDate" id="fullDate">
                        </div>
                        <div class="divide">
                            <label for="place"><span class="required">*</span>분실/습득 장소</label><input type="text"
                        id="place" name="place" required>
                        </div>
                    </li>
                    <li>
                        <div class="divide">
                            <label for="classify"><span class="required">*</span>분류</label>
                            <div class="select-style">
                                <select id="classify" name="classify" required>
                            <option value="">선택하세요</option>
                            <option value="핸드폰">핸드폰</option>
                            <option value="지갑">지갑</option>
                            <option value="카드">카드</option>
                            <option value="의류">의류</option>
                            <option value="신발">신발</option>
                            <option value="가방">가방</option>
                            <option value="학생증">학생증</option>
                            <option value="USB">USB</option>
                            <option value="기타">기타</option>
                        </select>
                            </div>
                        </div>
                        <div class="divide">
                            <label for="detail"><span class="required">*</span>세부 분류</label>
                            <input type="text" id="detail" name="detail" placeholder="물품의 명칭등을 입력해주세요" required>
                        </div>
                    </li>
                    <li>
                       <label for="input-file">이미지 첨부</label> 
                        <div class="filebox preview-image">
                          <input class="upload-name" value="파일선택" disabled="disabled" >
                          <label for="input-file">업로드</label> 
                          <input type="file" name="file" id="input-file" class="upload-hidden"> 
                        </div>
                    </li>
                    <li>
                        <label for="explain">특이 사항</label>
                        <div id="textareaWrap">
                        <textarea id="explain" name="explain"></textarea>
                        </div>
                    </li>
                </ul>
                <span class="required">*</span> 필수입력<br>
                <span class="select-required">*</span> 선택입력(둘 중 하나는 반드시 입력)
                <br>
                <br>
                <br>
                <button id="submit">등록하기</button>
            </form>
            </div>
        </div>
      <footer>
            <div class="limitWidth">
                <p id="p_logo">
                    <img src="img/footer_logo.png" alt="명지전문대학 Grayscale 로고">
                </p>
                <p class="address"><span class="privacy_btn"><a href="http://www.mjc.ac.kr/privacy.do?menuNo=1" target="_blank"><strong>개인정보처리방침</strong></a></span> 우)03656 서울특별시 서대문구 가좌로 134 (서울특별시 서대문구 홍은 2동 356-1) 본관 학생서비스팀 (1층)<br>대표전화 (02)300-1106&nbsp;&nbsp;FAX (02)300-8831<br> 
						Copyright 2015 MYONGJI College All Rights Reserved.
				</p>
            </div>
       </footer>
</body> 
</html>