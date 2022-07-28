<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <div> 삭제할 상품번호 <input type="text" id="productId" value="" /> </div>
  <input type="button" id="btn-submit" value="삭제하기">

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$("#btn-submit").click(function(){
  const data = {
    "productId" : $('#productId').val(),
  };
  $.ajax({
    type: "POST",
    url: "/product/remove/proc",
    contentType: 'application/json; charset=utf-8',

    data: JSON.stringify(data),
    success: function(data){
      alert("delete product/ seller " + data);
      window.location.href = "/";
    },
  });
})
</script>
</html>
