<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>상품정보수정페이트</title>
</head>
<body>
<h3>상품 정보 수정</h3>
<form action="${pageContext.request.contextPath}/api/product/${Product.product_id}/update" method="post">
    상품식별번호 : <input type="text" name="product_id" value="${Product.product_id}" readonly><br/>
    상품명: <input type="text" name="product_name" value="${Product.product_name}"><br/>
    상품가격: <input type="text" name="product_price" value="${Product.product_price}"><br/>
    상품이미지 : <img src="${pageContext.request.contextPath }/product/img?product_img_file_name=${Product.product_img_file_name}"
    width="50" height="50">
    <input type="file" name="product_img_file"> <br/>
    상품상세설명 : <input type="text" name="product_description" value="${Product.product_description}"><br/>
    상품수량 : <input type="text" name="product_quantity" value="${Product.product_quantity}"><br/>
    카테고리 : <select name="category_id">
            <option value="${Category.category_id}">${Category.category_name}</option>
            </select>
    <br>
    <input type="submit" value="상품수정">
    <input type="hidden" name="user-type" value="seller">
</form>

</body>
</html>
