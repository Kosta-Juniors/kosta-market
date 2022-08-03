<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h3>회원가입</h3>
<form action="/user/signup" method="post">
    아이디: <input type="text" name="username"><br>
    패스워드: <input type="password" name="password"><br>
    이름: <input type="text" name="name"><br>
    전화번호: <input type="text" name="contact"><br>
    <input type="submit" value="가입">
</form>

</body>
</html>