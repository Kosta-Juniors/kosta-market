package kosta.market.domain.order.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500")

public class OrderPageController {


    // 상품주문
    @GetMapping(value = "/order/{product_id}")
    public String addForm(@PathVariable("product_id") int productId){

        // 하드코딩으로 넘김
        int userId = 1;


//        if(Objects.equals(userId,null)){
//            return "/user/login";
//        }else{
//            return "order/orderProduct";
//        }
        return "order/creat";
    }

    //구매자,판매자 주문 리스트
    @GetMapping(value = "/order/sheet/list?{user-type}")
    public String orderListByUser(@PathVariable("user-type") int userType){

        return "order/user-list";
    }




    // 주문 상세정보
    @GetMapping(value = "/order/sheet/{order_id}")
    public String orderDetail(@PathVariable("order_id") int orderId ){

        return "order/detail";
    }

    // 교환 신청
    @GetMapping(value = "/order/sheet/{order_id}/exchange")
    public String orderExchange(@PathVariable("order_id") int orderId ){

        return "order/exchange";
    }


    // 환불 신청 - 주문취소로 통합

    // 구매 확정
    @GetMapping(value = "/order/sheet/{order_id}/confirm")
    public String orderConfirm(@PathVariable("order_id") int orderId ){

        return "order/confirm";
    }

    // 주문 취소
    @GetMapping(value = "/order/sheet/{order_id}/cancel")
    public String orderCancel(@PathVariable("order_id") int orderId ){

        return "order/cancel";
    }



















}
