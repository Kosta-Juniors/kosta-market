<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>상품정보수정페이지</title>
</head>
<body>
<h3>상품 정보 수정</h3>
<form id="form" enctype="multipart/form-data">
    상품식별번호 : <input type="text" name="product_id" id="product_id" value="" readonly><br/>
    상품명: <input type="text" name="product_name" id="product_name" value=""><br/>
    상품가격: <input type="text" name="product_price" id="product_price" value=""><br/>
    상품이미지 : <input type="file" name="imgFile" id="imgFile" onchange="readURL(this);"> <br/>
    <img id ="product_img" width="50" height="50" src=""/><br/>

    상품상세설명 : <input type="text" name="product_description" id="product_description" value=""><br/>
    상품수량 : <input type="text" name="product_quantity" id="product_quantity" value=""><br/>
    카테고리 : <span id="category_name"></span><br/>
    <input type="button" value="상품수정" onclick="ajax_send()">
    <input type="hidden" name="product_img_file_name" id="product_img_file_name" value="">
    <input type="hidden" name="product_img_path" id="product_img_path" value="">
</form>
</body>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script>
    $(function() {
        $("#imgFile").on('change', function(){
            readURL(this);
        });
    });
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#product_img').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    // 상품 정보 가져오기
    var url = window.location.href;
    var urlarray = url.split("/");
    for(i=0;i<urlarray.length;i++){
        if(!isNaN(urlarray[i])) {
            var productId = parseInt(urlarray[i]);
        }
    }
    alert(productId)
    // 수정 전 정보 가져오기
    $('document').ready(function () {

        $.ajax({
            type: "GET",
            url: "/api/product/"+productId,
            datatype: "JSON",
            success: function (data) {
                var result=JSON.parse(data);

                $('#product_id').attr("value",result.data.productId);
                $('#product_name').attr("value",result.data.productName);
                $('#product_price').attr("value",result.data.productPrice);
                $('#product_img').attr("src","${pageContext.request.contextPath }/product/img?product_img_file_name="+
                    result.data.productImgFileName);
                $('#product_description').attr("value",result.data.productDescription);
                $('#product_quantity').attr("value",result.data.productQuantity);
                $('#category_name').html(result.data.categoryName);
                $('#product_img_file_name').attr("value",result.data.productImgFileName);
            }
        })
    });


    // 파일 이미지 + 상품 관련 정보 전송
    function ajax_send() {

        var data = {
            product_id : $('#product_id').val(),
            product_name: $('#product_name').val(),
            product_price: $('#product_price').val(),
            product_img_file_name: $('#product_img_file_name').val(),
            product_img_path : $('#product_img_path').val(),
            product_description: $('#product_description').val(),
            product_quantity: $('#product_quantity').val()
        };

        var form = $('#form')[0];
        var formData = new FormData(form);
        formData.append('imgFile', $('#imgFile'));
        formData.append('productUpdateDto', new Blob([JSON.stringify(data)], {type: "application/json"}));

        $.ajax({
            type: 'PATCH',
            url: "/api/product/"+productId,
            processData: false,
            contentType: false,
            data: formData,
        }).done(function () {
            alert('상품이 수정되었습니다.')
            location.href = '${pageContext.request.contextPath}/product/list?user-type=seller';
        })
        //     .fail(function (error) {
        //     alert(JSON.stringify(error));
        // });
    }
</script>

</html>
