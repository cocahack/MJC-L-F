<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!--[if lt IE 9]>
    <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<title>등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/community.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/noticeRegit.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/input.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileupload.css?ver=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/regit.js?ver=1"></script>
    <script src="${pageContext.request.contextPath}/js/fileupload.js?ver=1"></script>
    <script src="${pageContext.request.contextPath}/js/notice/notice.js"></script>
</head>
<body>
	<jsp:include page="/template/header.jsp" flush="false"/>
	<div id="wrap">
        <jsp:include page="/template/adminLeft.html" flush="flase"/>
     	<div class="board">
            <div class="form">
                <h2>공지사항 등록</h2>
                <div class="h_deco"></div>
                <form class="regit_form form" id="mainform" action="noticeRegit.in" method="post">
                    <ul>
                        <li>
                            <label for="title">제목</label>
                            <input type="text" id="title" class="search_inputText" name="title" required>
                        </li>
                        <li>
                            <label for="content">내용</label>
                            <textarea id="content" class="search_inputText" name="content" required></textarea>
                        </li>
                        <li>
                            <label for="input-file">이미지 첨부</label> 
                        	<div class="fileList">
        						<button class="uploadbtn" type="button" id="upload">이미지 선택</button>
        						<button class="uploadbtn" type="button" id="submitbtn">업로드</button>
        						<button class="uploadbtn disabled" type="button" id="allcancelbtn" disabled="disabled">업로드 취소</button>
        						<div class="thumbnail-area">
								</div>
    						</div>
                        </li>
                        <li>
                            <button type="button" id="submitbutton" class="mainbtn">등록</button>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
     </div>
     <form id="fileform" method="get" enctype="multipart/form=data">
     	<input type="file" name="files" id="files" multiple="multiple"> 
     </form>
     <jsp:include page="/template/footer.html" flush="false"/>
</body>
</html>