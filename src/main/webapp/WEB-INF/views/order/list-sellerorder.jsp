<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <a href="/"><h2> 홈으로 </h2></a>
  <h2> 내 판매 목록 </h2>
  ${sellerorderList}
  <c:forEach var="sellerorder" items="${sellerorderList}">
    <div style="border: 1px solid black; padding:10px 10px 10px 10px; margin-top: 10px">

    <div>
      <a href="/order/detail/${sellerorder.productName}">
        <h3> 상품명 : ${sellerorder.productName} </h3>
      </a>
        <p> <span style="font-size:22px"> 총 구매가격 : ${sellerorder.paymentPrice} </span> - <span style="font-size:15px"> ${sellerorder.orderDate} </span> </p>
    </div>
    <div> 구매자 : ${sellerorder.orderer} </div>
    <div> 배송지 : ${sellerorder.deliveryPlace} </div>
    <div style="margin-top:10px"> 주문상태 : ${sellerorder.orderState} </div>

    </div>

  </c:forEach>

</body>

</html>
