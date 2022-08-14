<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품상세정보페이지</title>
</head>
<body>
<h3>상품 상세 정보</h3>

상품식별번호 : <span id="product_id"></span> <br/>
상품명 : <span id="product_name"></span> <br/>
상품가격 : <span id="product_price"></span> <br/>
상품이미지 : <img id="product_img" src="" width="50" height="50"> <br/>
상품상세설명 : <span id="product_description"></span> <br/>
상품수량 : <span id="product_quantity"></span> <br/>
카테고리 : <span id="category_name"></span> <br/>
<a href="${pageContext.request.contextPath}/product/list?user-type=user">상품리스트로 이동</a>
</body>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

    var url = window.location.href;
    var urlarray = url.split("/");
    for(i=0;i<urlarray.length;i++){
        if(!isNaN(urlarray[i])) {
            var productId = parseInt(urlarray[i]);
        }
    }

    $('document').ready(function () {

        $.ajax({
            type: "GET",
            url: "/api/product/"+productId,
            datatype: "JSON",
            success: function (data) {
             var result=JSON.parse(data);
             $('#product_id').html(result.data.productId);
             $('#product_name').html(result.data.productName);
             $('#product_price').html(result.data.productPrice);
             $('#product_img').attr("src","${pageContext.request.contextPath }/product/img?product_img_file_name="+
             result.data.productImgFileName);
             $('#product_description').html(result.data.productDescription);
             $('#product_quantity').html(result.data.productQuantity);
             $('#category_name').html(result.data.categoryName);
            }
        })
    });

</script>


</html>
