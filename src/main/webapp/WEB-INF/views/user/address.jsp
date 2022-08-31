<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h3>배송지 추가</h3>

<div></div>
<form id="address" action="/user/address" method="post">
    유저아이디 : <input type="text" name="user_id" value="${user_id}" readonly><br>
    배송지 추가 : <input type="text" name="delivery_place"><br>
    <input type="submit" id="add" value="배송지추가">

</form>

</body>
<%--<script>--%>
<%--    $(document).ready(function () {--%>
<%--      $('#add').on('click', function() {--%>
<%--        const data = {--%>
<%--          "address1" : $('#delivery_place').val()--%>
<%--        };--%>
<%--        $.ajax({--%>
<%--          url:"/address",--%>
<%--          type:"post",--%>
<%--          contentType: 'application/json; charset=utf-8',--%>
<%--          data: JSON.stringify(data),--%>
<%--          suscess: susccessCall,--%>
<%--          error: errorCall--%>
<%--        });--%>
<%--        function successCall() {--%>
<%--          alert("배송지 추가성공");--%>
<%--        }--%>
<%--        function errorCall() {--%>
<%--          alert("배송지 추가실패");--%>
<%--        }--%>
<%--      })--%>
<%--        }--%>
<%--    )--%>
<%--</script>--%>
</html>