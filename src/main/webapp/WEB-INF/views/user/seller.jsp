<%@ page import="kosta.market.domain.user.model.UserCreateDto" %>
<%@ page import="kosta.market.domain.user.model.SellerCreateDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<h3>판매자 등록</h3>

<form action="/user/seller" method="post">
    유저아이디: <input type="text" name="user_id" value="${user_id}" readonly><br>
    사업자등록번호: <input type="text" name="business_reg_no" id="business_reg_no"><br>
    <input type="submit" value="등록">
</form>

</body>
<%--<script>--%>
<%--  $("submit").click(function () {--%>
<%--    const data = {--%>
<%--      "user_id" : $('#userId').val(),--%>
<%--      "businessNo" : $('#businessNo').val()--%>
<%--    };--%>
<%--    $.ajax({--%>
<%--      type: "POST",--%>
<%--      url:"/seller",--%>
<%--      contentType: 'application/json; charset=utf-8',--%>

<%--      data: JSON.stringify(data),--%>
<%--      success:function (data) {--%>
<%--      },--%>
<%--    });--%>
<%--  })--%>
<%--</script>--%>
</html>