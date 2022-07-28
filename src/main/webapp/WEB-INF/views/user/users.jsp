<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<table>
  <tr>
    <th> 아이디</th>
    <th>비밀번호</th>
    <th>사용자명</th>
    <th>연락처</th>
  <tr>
  <c:forEach items="${userList}" var="user">
  <tr>
    <td>${user.username}</td>
    <td>${user.password}</td>
    <td>${user.name}</td>
    <td>${user.contact}</td>
    <td><input type="button" value="수정"></td>
    <td><input type="button" value="삭제"></td>
  </tr>
  </c:forEach>

  <a href="/signin">로그인</a><nbsp>
  <a href="/signup">회원가입</a><nbsp>
  <a href="/post">게시글</a><nbsp>
</table>

</body>
</html>
