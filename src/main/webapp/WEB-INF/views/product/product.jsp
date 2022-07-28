<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <a href="/"><h2> 홈으로 </h2></a>
  <h2> 상품 상세 정보 </h2>
    <div style="border: 2px solid black; padding:50px 50px 50px 50px; margin-top: 10px">
      상품명 : ${product.productName} <br>
      가격 : ${product.productPrice}  <br>
      재고수량 : ${product.productQuantity}  <br>
      설명 : <pre> ${product.productDescription} </pre> <br>
      <a href="http://127.0.0.1/order/${product.productId}/add"> <input type="button" value="구매하기"> </a>
    </div>
</body>
</html>
