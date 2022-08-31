<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>등록한 제품 리스트</title>
<script type="text/javascript">
function update(product_id){
    location.href="${pageContext.request.contextPath}/product/"+product_id+"/update";

}
</script>
</head>
<body>

<Form action="${pageContext.request.contextPath}/api/product/${s.product_id}/delete" method="post">
<input type="submit" value="삭제">
<table border="1">
    <c:forEach var="s" items="${ProductList}">
    <tr><th>   </th><th>상품식별번호</th><th>상품명</th><th>판매가격</th><th>재고수량</th><th>판매상태</th><th>수정</th></tr>

    <tr>

        <td><input type="radio" name="product_id" value="${s.product_id}">${s.product_id}</td>
        <td><a href="${pageContext.request.contextPath}/product/${s.product_id}/update">${s.product_id}</a></td>
        <td>${s.product_name}</td>
        <td>${s.product_price}</td>
        <td>${s.product_quantity}</td>
        <c:set var="quantity" value="${s.product_quantity}"/>
        <c:if test="${quantity>10}">
            <td>판매중</td>
        </c:if>
        <c:if test="${quantity>0 && quantity<=10}">
            <td>품절임박</td>
        </c:if>
        <c:if test="${quantity==0}">
            <td>품절</td>
        </c:if>
        <td><input type="button" value="수정" onclick="update(${s.product_id})"></td>
    </tr>

</table>
</Form>
</c:forEach>
</body>
</html>
