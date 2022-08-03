<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>상품상세정보페이지</title>
</head>
<body>
<h3>상품 상세 정보</h3>

    상품식별번호 :${Product.product_id}<br/>
    상품명: ${Product.product_name}<br/>
    상품가격: ${Product.product_price}<br/>
    상품이미지 : <img src="${pageContext.request.contextPath }/product/img?product_img_file_name=${Product.product_img_file_name}"
                 width="50" height="50"> <br/>
    상품상세설명 : ${Product.product_description}<br/>
    상품수량 : ${Product.product_quantity}<br/>
    카테고리 : ${Category.category_name}     <br>
<a href="${pageContext.request.contextPath}/product/list?user-type=user">상품리스트로 이동</a>
</body>
</html>
