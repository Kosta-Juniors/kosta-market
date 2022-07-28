<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <a href="/"><h2> 홈으로 </h2></a>
  <h2> 내 주문 목록 </h2>
  ${orderList}
  <c:forEach var="order" items="${orderList}">
    <div style="border: 1px solid black; padding:10px 10px 10px 10px; margin-top: 10px">


    <a href="/order/detail/${order.orderId}">

      <div>
      상품명 : ${order.orderId}
      총 구매가격 : ${product.productPrice}  <br>
      구매일자 : ${product.productQuantity}  <br>
      </div>
    </a>
    <div> 판매자 : ${1} </div>

    </div>

  </c:forEach>

</body>

</html>
