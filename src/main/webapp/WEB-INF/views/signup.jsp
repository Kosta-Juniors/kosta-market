<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <div>
      <div> 아이디 <input type="text" id="username" value="reguser"/> </div>
      <div> 비밀번호 <input type="password" id="password" value="1111"/> </div>
      <div> 이름 <input type="text" id="name" value="신규회원이름"/></div>
      <div> 전화번호 <input type="text" id="contact" value="000-9999-0000"/></div>
      <div> 전화번호 <input type="text" id="business_no" value="BIZ-001-01-011"/></div>
    </div>
    <input type="button" id="btn-submit" value="가입하기">
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
$("#btn-submit").click(function(){
  const data = {
    "username" : $('#username').val(),
    "password" : $('#password').val(),
    "name" : $('#name').val(),
    "contact" : $('#contact').val(),
  };

  if($('#business_no').val())
    data.business_no = $('#business_no').val()

  $.ajax({
    type: "POST",
    url: "/user/add/proc",
    contentType: 'application/json; charset=utf-8',

    data: JSON.stringify(data),
    success: function(data){
      alert("sign up success " + data);
      window.location.href = "/login";
    },
  });
})
</script>
</html>
