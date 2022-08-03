<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>전체상품리스트</title>
<script>


</script>

</head>
<body>
<h3>전체 상품 리스트</h3>


<form action="${pageContext.request.contextPath}/product/productnamelist" method="get">
    <input type="text" name="product_name">
    <input type="submit" value="검색"> <br><br>
</form>
<h4>상품 목록</h4><hr>
<c:forEach var="p" items="${ProductList}">
    <img src="${pageContext.request.contextPath }/product/img?product_img_file_name=${p.product_img_file_name}"
         width="50" height="50"><br/>

    <a href="${pageContext.request.contextPath}/product/${p.product_id}">${p.product_name}</a>
    <br/>
    ${p.product_price}원<br/>
    <c:set var="quantity" value="${p.product_quantity}"/>
    <c:if test="${quantity>10}">구매가능</c:if>
    <c:if test="${quantity>0 && quantity<=10}">품절임박</c:if>
    <c:if test="${quantity==0}">품절</c:if>
    <input type="button" value="구매"><br/><hr/>
</c:forEach>

</body>
</html>
