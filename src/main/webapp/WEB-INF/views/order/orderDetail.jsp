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

<h3> 주문상세정보 작업중</h3>   <!-- 주석되네 -->


<table border="1">

<tr> <th>주문번호</th> <td>${orderDetail.order_id}</td> </tr>

<tr> <th>상품명</th> <td>${orderDetail.product_name}</td> <tr>

<tr> <th>주문개수</th> <td>${orderDetail.order_quantity}개</td> <tr>

<tr> <th>결제가격</th> <td>${orderDetail.payment_price}원</td> <tr>

<tr> <th>주문상태</th>
    <td> <c:choose>
             <c:when test="${orderDetail.order_state == 0}">주문완료</c:when>
             <c:when test="${orderDetail.order_state == 1}">상품준비</c:when>
             <c:when test="${orderDetail.order_state == 2}">배송중</c:when>
             <c:when test="${orderDetail.order_state == 3}">교환신청</c:when>
             <c:when test="${orderDetail.order_state == 4}">취소/환불</c:when>
             <c:when test="${orderDetail.order_state == 5}">구매확정</c:when>
         </c:choose> </td>
<tr>

<!-- 위 5개는 list에서 보여지는 정보와 같음 -->

<tr> <th>이름(주문자)</th> <td>${orderDetail.name}</td> <tr>

<tr> <th>연락처(주문자)</th> <td>${orderDetail.contact}</td> <tr>

<tr> <th>배송지</th> <td>${orderDetail.delivery_place}</td> <tr>

<tr> <th>주문날자</th> <td>${orderDetail.order_date}</td> <tr>

<tr> <th>결제방법</th>
    <td> <c:choose>
             <c:when test="${orderDetail.payment_method == 0}">카드</c:when>
             <c:when test="${orderDetail.payment_method == 1}">무통장입금</c:when>
         </c:choose> </td>
<tr>

</table>
<br>
<a href="${pageContext.request.contextPath}/order/orderListByUser">(구매자)주문리스트</a>
<br>
<hr>
<!-- 추후 기능추가 -->
<a href="/order/sheet/${orderDetail.order_id}/exchange">상품교환신청</a> <br>
<a href="/order/sheet/${orderDetail.order_id}/cancel">구매취소</a> <br>
<a href="/order/sheet/${orderDetail.order_id}/confirm">구매확정</a> <br>

<hr>





</body>
</html>