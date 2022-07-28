<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <a href="/"> <h1> 홈으로 </h1> </a>
  <h1> 주문 / 결제 </h1>
  <h2> 주문 정보 </h2>
  <div>
    <div> 상품명 : ${product.productName} </div>
    <div> 구매수량 : <input type="number" id="orderQuantity" min="1" value="1" onchange=updateOrderQuantity()> </div>
    <div> 판매자 : ${user.username} </div>
    <div> 상품금액 : <input type="text" id="resultPrice" readonly> </div>
  </div>

  <h2> 배송지 정보 </h2>
  <div>
    내 배송지 목록 <br>
    <c:forEach var="address" items="${addressList}">
      <input type="radio" id="addressId" value="${address.addressId}" /> ${address.deliveryPlace} <br>
    </c:forEach>
    <a href="/user/address/add"> <input type="button" value="신규 배송지 등록하기"> </a>
  </div>

  <h2> 결제 방법 </h2>
  <div>
      <input type="radio" name="paymentMethod" value="00" checked> 카드결제 <br>
      <input type="radio" name="paymentMethod" value="01"> 무통장계좌 <br>
      <input type="radio" name="paymentMethod" value="02"> 핸드폰 <br>
      <input type="radio" name="paymentMethod" value="03"> 카카오페이 <br>
      <input type="radio" name="paymentMethod" value="04"> 네이버페이 <br>
  </div>

  <input type="button" id="btn-submit" value="주문하기">
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$("#btn-submit").click(function(){
  const data = {
    "productId" : window.location.href.split('/')[4],
    "orderQuantity" : $('#orderQuantity').val(),
    "paymentMethod" : $('#paymentMethod').val(),
    "addressId" : $('#addressId').val()
  };
  $.ajax({
    type: "POST",
    url: "/order/add/proc",
    contentType: 'application/json; charset=utf-8',

    data: JSON.stringify(data),
    success: function(data){
      alert("주문했어" + data);
      window.location.href = "/";
    },
  });
})
</script>
<script>

</script>

<script>
  function updateOrderQuantity(){
    $("#orderQuantity").val() * ${product.productPrice};
    $("#resultPrice").val(${product.productPrice} * $("#orderQuantity").val());
  }
  updateOrderQuantity();
</script>
</html>
