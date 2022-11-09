<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 여부 체크</title>
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
		} else {
	%>
		<script language="JavaScript">
			alert("사용하셔도 좋은 아이디입니다. 계속해서 가입 진행해주세요.");
			history.go(-1);
		</script>
	<%		
		}
	%>
</body>
</html>