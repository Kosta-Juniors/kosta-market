<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

  <div> 아이디 <input type="text" name="username" id="username" value="seller1" /> </div>
  <div> 비밀번호 <input type="password" name="password" id="password" value="1111"/> </div>
  <input type="button" id="btn-submit" value="로그인">

</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$("#btn-submit").click(function(){
  const data = {
    "username" : $('#username').val(),
    "password" : $('#password').val()
  };
  $.ajax({
    type: "POST",
    url: "/login/proc",
    contentType: 'application/json; charset=utf-8',

    data: JSON.stringify(data),
    success: function(data){
      alert("hello user " + data);
      window.location.href = "/";
    },
  });
})
</script>
</html>
