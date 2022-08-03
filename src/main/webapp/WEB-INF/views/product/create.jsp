<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품등록페이지</title>
</head>
<body>
<h3>상품 등록</h3>
<form action="${pageContext.request.contextPath}/api/product" method="post"
      enctype="multipart/form-data">
    상품명: <input type="text" name="product_name"><br>
    상품가격: <input type="text" name="product_price"><br>
    상품이미지 : <input type="file" name="product_img_file"><br>
    상품상세설명 :<br/> <textarea name="product_description" width="30" cols="30"></textarea><br>
    상품수량 : <input type="text" name="product_quantity"><br>
    카테고리 : <select id="category1" name="category_id">
    <c:forEach var="c" items="${categoryList}">
    <option value="${c.category_id}">${c.category_name}</option>
    </c:forEach>
    </select>
    <br>
    <input type="reset" value="초기화"><input type="submit" value="상품등록">
</form>

</body>
</html>
