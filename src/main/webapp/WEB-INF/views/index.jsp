<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/product/create">상품등록</a><br/>
<a href="${pageContext.request.contextPath}/product/list?user-type=seller">등록상품조회</a><br/>
<a href="${pageContext.request.contextPath}/product/list?user-type=user">전체상품조회</a><br/>
</body>
</html>