<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <a href="/"><h2> 홈으로 </h2></a>
  <h2> 상품 목록 </h2>
  <c:forEach var="product" items="${productList}">
    <div style="border: 2px solid black; padding:10px 10px 10px 10px; margin-top: 10px">
      상품명 : ${product.productName} <br>
      가격 : ${product.productPrice}  <br>
      재고수량 : ${product.productQuantity}  <br>
      <input type="button" value="수정하기" onclick='editProduct(${product.productId})'>
      <input type="button" value="삭제하기" onclick='deleteProduct(${product.productId})'>
    </div>
  </c:forEach>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
 <script>
 function editProduct(index){
    const data = {
          "productId" : $('#categoryId').val(),
        };
 }
 function deleteProduct(index){
    const data = {"productId" : index};
    $.ajax({
      type: "POST",
      url: "/product/remove/proc",
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data),
      success: function(data){
         alert("hello seller " + data);
         window.location.href="/product/registlist"
      },
    });
 }

 $("#btn-submit").click(function(){
   const data = {
     "categoryId" : $('#categoryId').val(),
   };
   $.ajax({
     type: "POST",
     url: "/product/add/proc",
     contentType: 'application/json; charset=utf-8',

     data: JSON.stringify(data),
     success: function(data){
       alert("hello seller " + data);
       window.location.href = "/";
     },
   });
 })
</script>
</html>
