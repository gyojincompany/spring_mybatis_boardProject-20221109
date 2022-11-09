<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 완료</title>
</head>
<body>
	<%
		int checkId = Integer.parseInt(request.getAttribute("checkIdFlag").toString());//${checkIdFlag}
		if (checkId == 1) {	
	%>
		<script language="JavaScript">
			alert("입력하신 아이디는 이미 사용중입니다. 다른 아이디를 입력해주세요!");
			history.go(-1);
		</script>
	<%	
		}
	%>
	
	<h2>회원 가입을 축하드립니다.</h2>
	<h2>${mname }님 반갑습니다.</h2>
</body>
</html>