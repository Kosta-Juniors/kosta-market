<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<h3>(구매자)전체 주문 목록</h3>

<c:forEach var="o" items="${orderlist}">

<div style="margin-top:10px">

    <div>주문번호 : <a href="/order/sheet/${o.order_id}"> ${o.order_id} </a> </div>

    <div> 상품명 : ${o.product_name} </div>

    <div> 구매개수 : ${o.order_quantity} </div>

    <div> 주문금액 : ${o.payment_price} </div>

    <div> 주문상태 : ${o.order_state} </div>

</div>

</c:forEach>

</body>
</html>