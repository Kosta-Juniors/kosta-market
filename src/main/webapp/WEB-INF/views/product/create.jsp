<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>상품등록페이지</title>

</head>
<body>
<h3>상품 등록</h3>
<form id ="form" enctype="multipart/form-data">

상품명 : <input type="text" name="product_name" id="product_name"><br/>
    상품가격 : <input type="text" name="product_price" id="product_price"><br/>
    상품이미지 : <input type="file" name="imgFile" id="imgFile"><br/>
    <img id ="preview" width="50" height="50" src="" alt="미리보기"/><br/>
    상품상세설명 :<br/> <textarea name="product_description" width="30" cols="30" id="product_description"></textarea><br/>
    상품수량 : <input type="text" name="product_quantity" id="product_quantity"><br/>
    카테고리 : <select name="category_id" id="category_id">
    <c:forEach var="c" items="${categoryList}">
    <option value="${c.category_id}">${c.category_name}</option>
    </c:forEach>
    </select>
    <br>
<input type="hidden" name="product_img_file_name" id="product_img_file_name" value="">
<input type="hidden" name="product_img_path" id="product_img_path" value="">
<input type="reset" value="취소"> <input type="button" value="상품등록" onclick="ajax_send()">

</form>
</body>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script>

    // 이미지 미리보기
   $(function() {
        $("#imgFile").on('change', function(){
            readURL(this);
        });
    });
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#preview').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
    function ajax_send() {
        var sel = document.getElementById("category_id");
        var category = sel.options[sel.selectedIndex].value;

        var data = {
            product_name: $('#product_name').val(),
            product_price: $('#product_price').val(),
            product_img_file_name : $('#product_img_file_name').val(),
            product_img_path : $('#product_img_path').val(),
            product_description : $('#product_description').val(),
            product_quantity : $('#product_quantity').val(),
            category_id : category
        };
        var form =$('#form')[0];
        var formData = new FormData(form);
        formData.append('imgFile', $('#imgFile'));
        formData.append('product', new Blob([JSON.stringify(data)] , {type: "application/json"}));

        $.ajax({
            type: 'POST',
            url: '/api/product',
            processData: false,
            contentType:false,
            data: formData,
        }).done(function() {
            alert('상품이 등록되었습니다.');
            location.href = '${pageContext.request.contextPath}/index';
        })
        //     .fail(function (error) {
        //     alert(JSON.stringify(error));
        // });
    }


</script>
</html>
