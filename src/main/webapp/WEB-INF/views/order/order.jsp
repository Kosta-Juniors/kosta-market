<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <a href="/"><h2> 홈으로 </h2></a>
  <h2> 주문 상세 정보 </h2>
  주문번호<br>
  <div style="border: 2px solid black; padding:50px 50px 50px 50px; margin-top: 10px">
    주문시간 : ${product.productName} <h5> 주문번호</h5> 주문상태 <br>
    상품정보
     - 물품명 _판매자
     - 금액,
     - 수량,
     - 배송지정보
    <input type="button" id="btn-confirm" value="주문확정" onclick="confirmOrder(${order.orderId})">
    <input type="button" id="btn-exchange" value="교환신청" onclick="exchangeRequest(${order.orderId}))">
    <input type="button" id="btn-refund" value="환불신청" onclick="refundRequest(${order.orderId}))">
  </div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
 <script>
 $("#btn-submit").click(function(){
   const data = {
     "categoryId" : $('#categoryId').val(),
   };
   $.ajax({
     type: "POST",
     url: "/",
     contentType: 'application/json; charset=utf-8',

     data: JSON.stringify(data),
     success: function(data){
       alert("123 " + data);
       window.location.href = "/";
     },
   });
 })
</script>
</html>
