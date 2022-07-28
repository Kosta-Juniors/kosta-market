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
    <a href="/product/${product.productId}">
      <h2> ${product.productName} </h2> <h5> 클릭 시 상품 상세 페이지 이동 </h5><br>
      </a>
      가격 : ${product.productPrice}  <br>
      재고수량 : ${product.productQuantity}  <br>
    </div>

  </c:forEach>

</body>

</html>
