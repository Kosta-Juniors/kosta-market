<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

  <div>현재 인증된 유저아이디 : ${username} </div>
  <div>현재 인증된 유저명 : ${name} </div>
  <div>판매자 고유 아이디 : ${seller}-${businessNo} </div>

  <a href="/user/add" > 회원가입 하러가자 </a> <br>
  <a href="/login/" > 로그인 하러가자 </a> <br>
  <a href="/user/address/add" > 배송지 추가 하러가자 </a> <br>
  <a href="/logout/proc" > 로그아웃 하러가자 </a> <br>
  <a href="/user/delete" > 회원탈퇴 하러가자 </a>
</body>
</html>
