<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>게시물 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/regit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/regit.js?ver=1.28" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="template/header.jsp" flush="false"/>
	<div id="wrap">
            <div class="form">
               <h2>게시물 수정</h2>
               <div class="h_deco"></div>
                <form class="regit_form form ver1" action="modify.do" method="post" enctype="multipart/form-data">
                <ul>
                    <li>
                        <div class="divide">
                            <label for="stnum"><span class="required">*</span>학번</label><input class="search_inputText" type="text" id="stnum" name="stnum" value="${modReq.st_num}" maxlength="10">
                        </div>
                        <div class="divide">
                            <label for="name"><span class="required">*</span>이름</label><input class="search_inputText" type="text" id="name" name="name" value="${modReq.name}">
                        </div>
                    </li>
                    <li>
                        <div class="divide">
                        <c:set var="phonenum" value="${modReq.mv.phone }"/>
                        <c:set var="phonenumlength" value="${fn:length(phonenum) }"/>
                        <c:set var="phonehead" value="${fn:substring(phonenum,0,3)}"/>
                        <c:set var="phonetail" value="${fn:substring(phonenum,phonenumlength-4,phonenumlength)}"/>
                        <c:set var="temp" value="${fn:substringAfter(phonenum,phonehead) }"/>
                        <c:set var="phonebody" value="${fn:substringBefore(temp,phonetail) }"/>
                            <label for="phonenum1"><span class="select-required">*</span>전화번호</label>
                            <input type="text" class="search_inputText" id="phonenum0" name="phonenum0" value="${phonehead }" maxlength="3">
                            <label for="phonenum1" class="slash">-</label>
                            <input type="text" class="search_inputText" id="phonenum1" name="phonenum1" value="${phonebody }" maxlength="4">
                            <label for="phonenum2" class="slash">-</label>
                            <input type="text" class="search_inputText" id="phonenum2" name="phonenum2" value="${phonetail }" maxlength="4">
                        	<input type="hidden" id="fullphonenum" name="fullphonenum">
                        </div>
                        <div class="divide">
                            <label for="kakao"><span class="select-required">*</span>Kakao ID</label><input type="text" class="search_inputText" id="kakao" name="kakao" value="${modReq.mv.kakao}">
                        </div>
                    </li>
                    <li>
                        <div class="divide">
                            <label for="year"><span class="required">*</span>분실/습득일</label>
                            <input type="text" class="search_inputText" id="year" name="year" pattern="[0-9]{4}" required title="연도를 잘못 입력하셨습니다." maxlength="4">
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
                            <label for="place"><span class="required">*</span>분실/습득 장소</label><input type="text" class="search_inputText" 
                        id="place" name="place" value="${modReq.mv.place }" required>
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
                            <input type="text" class="search_inputText" id="detail" name="detail" placeholder="물품의 명칭등을 입력해주세요" value="${modReq.title }" required>
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
                        <textarea id="explain" class="search_inputText" name="explain">${modReq.mv.explain }</textarea>
                        </div>
                    </li>
                </ul>
                <input type="hidden" name="type" value="${param.type }">
                <input type="hidden" name="no" value="${param.no }">
                <input type="hidden" name="pageNo" value="${param.pageNo }">
                <span class="required">*</span> 필수입력<br>
                <span class="select-required">*</span> 선택입력(둘 중 하나는 반드시 입력)
                <br>
                <br>
                <br>
                <button id="submit">등록하기</button>
            </form>
            </div>
        </div>
        <jsp:include page="template/footer.html" flush="false"/>
</body>
</html>