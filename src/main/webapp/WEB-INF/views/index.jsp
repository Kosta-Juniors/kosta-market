<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

  <div>현재 인증된 유저아이디 : ${username} </div>
  <div>현재 인증된 유저명 : ${name} </div>
  <div>판매자 고유 아이디 : ${seller}-${businessNo} </div>

  <div>
    <h3> 회원 도메인 기능 </h3>
    <a href="/user/add"> 회원가입 </a> <br>
    <a href="/login/"> 로그인 </a> <br>
    <a href="/user/address/add"> 배송지 추가 </a> <br>
    회원정보 변경 <br>
    <a href="/logout/proc"> 로그아웃 </a> <br>
    <a href="/user/delete"> 회원탈퇴 </a>
  </div>

  <div>
    <h3> 상품 도메인 기능 </h3>
    <a href="/product/add"> 상품 등록 </a> <br>
    상품 수정 (첫번째 상품) <br>
    <a href="/product/remove"> 상품 삭제 </a> <br>
    <a href="/product/list"> 전체 상품 목록 </a> <br>
    <a href="/product/registlist"> 내가 등록한 상품 목록 </a> <br>
    <a href="/product/1"> 상품 상세 정보 (첫번째 상품) </a> <br>
    카테고리 생성 <br>
  </div>

  <div>
    <h3> 결제 도메인 기능 </h3>
    <a href="/order/1/add"> 주문하기 (첫번째 상품) </a> <br>
    <a href="/order/list"> 나의 구매내역 </a> <br>
    <a href="/order/sellerlist"> 나의 판매내역 </a><br>
    <a href="/order/detail/1"> 주문 상세 조회 (첫번째 주문) </a> <br>
     >> 상품 교환 신청 <br>
     >> 구매 취소하기 (첫번째 주문) <br>
     >> 구매 확정하기 (첫번째 주문) <br>
  </div>

</body>
</html>
