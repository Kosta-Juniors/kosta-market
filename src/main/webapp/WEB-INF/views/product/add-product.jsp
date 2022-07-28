<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

  <div> 카테고리 : <select id="categoryId">
                 <option value="1"> Cate-A </option>
                 <option value="2"> Cate-B </option>
                 <option value="3" selected="selected"> Cate-C </option>
               </select>
  </div>

  <div> 상품명 <input type="text" name="" id="productName" value="우주선 무드등 수유등 MK-001" /> </div>
  <div> 가격 <input type="text" name="" id="productPrice" value="15000"/> </div>
  <div> 수량 <input type="text" name="" id="productQuantity" value="1000"/> </div>
  <div> 설명 <textarea id="productDescription" name="productDescription" rows="5" cols="33">
              "잠잘때 좋아 이거" </textarea>
  <input type="button" id="btn-submit" value="상품등록">

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$("#btn-submit").click(function(){
  const data = {
    "categoryId" : $('#categoryId').val(),
    "productName" : $('#productName').val(),
    "productPrice" : $('#productPrice').val(),
    "productQuantity" : $('#productQuantity').val(),
    "productDescription" : $('#productDescription').val()
  };
  $.ajax({
    type: "POST",
    url: "/product/add/proc",
    contentType: 'application/json; charset=utf-8',

    data: JSON.stringify(data),
    success: function(data){
      alert("hello seller " + data);
      window.location.href = "/";
    },
  });
})
</script>
</html>
