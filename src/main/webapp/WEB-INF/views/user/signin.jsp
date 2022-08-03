<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h3>로그인</h3>

<form action="/user/signin" method="post">
<span>아이디: <input type="text" name="username" id="username"></span>
<span>비밀번호: <input type="password" name="password" id="password"></span>
<input type="submit" id="submit" value="로그인">
</form>
${message}
<br><a href="/user/signup">회원가입</a>
</body>
<%--<script>--%>
<%--    $("submit").click(function () {--%>
<%--      const data = {--%>
<%--        "username" : $('#username').val(),--%>
<%--        "password" : $('#password').val()--%>
<%--      };--%>
<%--      $.ajax({--%>
<%--        type: "POST",--%>
<%--        url:"/login",--%>
<%--        contentType: 'application/json; charset=utf-8',--%>

<%--        data: JSON.stringify(data),--%>
<%--        success:function (data) {--%>
<%--        },--%>
<%--      });--%>
<%--    })--%>
<%--</script>--%>
</html>