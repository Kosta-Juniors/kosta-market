<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h3>회원탈퇴</h3>

비밀번호를 다시 한 번 입력해주세요<br><br>
<form action="/user/delete" method="post" >
  비밀번호: <input type="password" id="password" name="password">
    <input type="submit" value="회원탈퇴">

</form>
</body>

</html>