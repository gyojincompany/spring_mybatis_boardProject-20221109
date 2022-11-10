<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글내용</title>
</head>
<body>
	<h2>자유게시판 글내용</h2>
	<hr>
	글번호 : ${fbdto.fnum }<br><br>
	아이디 : ${fbdto.fid } <br><br>
	이 름 : ${fbdto.fname } <br><br>
	제 목 : ${fbdto.ftitle }<br><br>
	내 용 : ${fbdto.fcontent }<br><br>
	등록일 : ${fbdto.fdate }<br><br>
	<input type="button" value="수정" onclick="javascript:window.location='modifyView?fnum=${fbdto.fnum }'">
	<input type="button" value="삭제" onclick="javascript:window.location='delete?fnum=${fbdto.fnum }'">  
	<input type="button" value="목록" onclick="javascript:window.location='list'">
	
</body>
</html>