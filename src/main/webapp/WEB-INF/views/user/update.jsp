<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h3>회원정보 변경</h3>
<form action = "/user/update" method="post">

    아이디 : <input type="text" name="username" value="${user.username}" readonly><br>
    비밀번호 : <input type="password" name="password" value="${user.password}"><br>
    이름 : <input type="text" name="name" value="${user.name}" readonly><br>
    전화번호 : <input type="text" name="contact" value="${user.contact}"><br>

<%--    사업자등록번호: input type="text" name="business_reg_no" ><br>  판매자 일때만 c:if test 사용--%>

<%--    <c:if test="${!empty user.business_reg_no}">--%>
<%--    사업자등록번호 : <input type="text" name="business_reg_no" value="${user.business_reg_no}"><br/>--%>
<%--    </c:if>--%>
    <input type="submit" value="수정">
</form>
</body>
</html>

