<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <div> 내 배송지 </div>
    <c:forEach var="address" items="${addressList}">
      <input type="radio" id="addressId" value="${address.addressId}" /> ${address.deliveryPlace} <br>
    </c:forEach>
  <div> ----- </div>
  <div> 배송지 <input type="text" name="delivery" id="delivery" value="서울특별시 금천구 호서대벤처타워 4층 403호"/> </div>
  <div> 기본배송지 여부 </div>
  <div>
    네
    <input type="radio" name="default-address" value="Y" checked/>
    아니오
    <input type="radio" name="default-address" value="N"/>
  </div>
  <input type="button" id="btn-submit" value="배송지 등록">

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$("#btn-submit").click(function(){
  const data = {
    "delivery" : $('#delivery').val(),
    "default-address" : $('input[name="default-address"]:checked').val()
  };
  $.ajax({
    type: "POST",
    url: "/user/address/add/proc",
    contentType: 'application/json; charset=utf-8',

    data: JSON.stringify(data),
    success: function(data){
      alert("address add " + data);
      window.location.href = "/";
    },
  });
})
</script>
</html>
