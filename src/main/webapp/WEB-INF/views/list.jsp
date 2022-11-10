<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판</title>
</head>
<body>
	<%
		int idflag	= Integer.parseInt(request.getAttribute("idflag").toString());
		if(idflag == 1) {		
	%>	
	${sid }님 로그인 중 <input type="button" value="로그아웃" onclick="javascript:window.location='logout'">
	<%
		} else {
	%>
	<input type="button" value="로그인" onclick="javascript:window.location='login'">
	<%
		}
	%>

	<h2>글 목록</h2>
	<hr>
	총 게시글 개수 : ${boardSum}<br>
	
	<table width="1000" border="1" cellspacing="0" cellpadding="0">
		<tr>
			<th>번호</th>
			<th>아이디</th>
			<th>글쓴이</th>
			<th width="600">글제목</th>
			<th>조회수</th>
			<th>등록일</th>
		</tr>
		<c:forEach items="${list }" var="fbdto">
		<tr align="center">
			<td>${fbdto.fnum }</td>
			<td>${fbdto.fid }</td>
			<td>${fbdto.fname }</td>
			<td align="left">&nbsp;<a href="contentView?fnum=${fbdto.fnum }">${fbdto.ftitle }</a></td>
			<td>${fbdto.fhit }</td>
			<td>${fbdto.fdate }</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="6" align="right"><a href="writeForm">글쓰기</a></td>
		</tr>	
	</table>
</body>
</html>