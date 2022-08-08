package kosta.market.domain.order.controller;

import kosta.market.domain.order.model.OrderDetailDto;
import kosta.market.domain.order.model.OrderListDto;
import kosta.market.domain.order.model.OrderRequestDto;
import kosta.market.domain.order.service.OrderServiceImpl;
import kosta.market.domain.product.model.Product;
import kosta.market.domain.user.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceImpl service;

    @GetMapping(value = "/order/orderProduct") //페이지 던져줌
    public String addForm(HttpServletRequest req, Model model) {
//        HttpSession session = req.getSession(true);
        // 회원번호, session으로 받아온다.
//        int user_id = (int)session.getAttribute("id");
        // session은 임시로 하드코딩으로 넘길것
        int user_id = 1;

        if (Objects.equals(user_id, null)){ //int의 null에 해당되는값이 0맞나?
            return "/user/login";
        } else {
            // (Address table)주소지선택 - User단의 mapper
            ArrayList<Address> addressList = (ArrayList<Address>)service.getAddress(user_id);
            model.addAttribute("addressList",addressList);

            return "order/orderProduct";
        }

        // return 보다 위에 적어야 하나?
        // 출력할 정보 - (Product table)상품상세정보 - Product단의 mapper
        // 나중에 정렬이형 support



    }


    @PostMapping(value = "/order/orderProduct") // 입력값 받아서 add실행
    public String add(HttpServletRequest req, OrderRequestDto ord){
        //입력값 - 주문수량, 결제수단, 기본주소여부, 배송지
//        int order_quantity = Integer.parseInt(req.getParameter("order_quantity"));
//        String payment_method = req.getParameter("payment_method");
//        String address_id = req.getParameter("address_id");

//        OrderRequestDto ord = new OrderRequestDto(order_quantity, payment_method, is_defualt_address, delivery_place);


        int product_id = 9; // 수정필요 - 나중에 받아올 것!!


        boolean flag = service.addOrder(ord, product_id);
        //order_id를 전달받아야 한다
        //insert완료
        System.out.println(flag);
        if (flag){
            int order_id = service.getLastOrderId();
//            req.setAttribute("order_id",order_id);
//            return "redirect:/order/orderDetail"; // 주문성공시 상세주문보기page로
            return "redirect:/order/sheet/"+order_id;
        } else {
            // 주문실패 메세지를 어떻게 띄울까?
            return "redirect:/order/orderProduct"; // 실패시 다시 주문page로
        }

    }

    // 주문상세정보를 보여줘야한다.
    // getmapping(orderdetail)을 따로 만들자 - 기능분리

    @GetMapping(value = "/order/sheet/{orderId}")
    public String orderdetailForm(HttpServletRequest req, Model model, @PathVariable("orderId") int orderId){



        //1. 주문page에서 (주문이후) 넘어올때  order_id 받아옴 >>  int order_id = service.getLastOrderId();

        //2. 주문list에서 조회를 위해 넘어올 때 order_id를 받아옴
//        int order_id = Integer.parseInt(req.getParameter("order_id"));


        OrderDetailDto orderDetail = service.detailOrder(orderId);
        model.addAttribute("orderDetail",orderDetail);

        return "order/orderDetail";
    }


    @GetMapping(value = "/order/orderListByUser")
    public String orderListUser(HttpServletRequest req, Model model){
//        HttpSession session = req.getSession(true);
//        회원번호, session으로 받아온다.
//        int user_id = (int)session.getAttribute("id");
//        session은 임시로 하드코딩으로 넘길것
        int user_id = 1;
        ArrayList<OrderListDto> orderlist = service.listByUserIdOrder(user_id);
        model.addAttribute("orderlist", orderlist);


        //user_id를 통해 (구매자)주문내역리스트를 가져온다. 주문번호, 상품명, 구매개수, 주문금액, 주문상태

        return "order/orderListByUser";
    }


    @GetMapping(value = "/order/orderListBySeller") // 현재 jsp페이지가 독립적으로 구성됨, 판매자(product or user)에서 어떻게 연결시킬지 check!
    public String orderListSeller(HttpServletRequest req, Model model){
//        HttpSession session = req.getSession(true);
//        판매자번호, session으로 받아온다.
//        session은 임시로 하드코딩으로 넘길것
        int seller_id = 1;

        ArrayList<OrderListDto> orderlist = (ArrayList<OrderListDto>) service.listBySellerIdOrder(seller_id);
        model.addAttribute("orderlist", orderlist);

        return "order/orderListBySeller";
    }

    //orderExchange(상품교환신청)은 jsp페이지 없이 기능만 구현
    @GetMapping(value = "/order/sheet/{order_id}/exchange")
    public String orderExchange(@PathVariable("order_id") int order_id){
        int orderState = 3;
        service.exchangeOrder(orderState, order_id);
        return "redirect:/order/sheet/"+order_id;
    }

    @GetMapping(value = "/order/sheet/{order_id}/cancle")
    public String orderCancel(@PathVariable("order_id") int order_id){
        int orderState = 4;
        service.exchangeOrder(orderState, order_id);

        //product의 주문수량도 바꿔주자
        OrderDetailDto orderDetail = service.detailOrder(order_id);
//        product_id, order_quantity
        service.updateProduct(orderDetail.getProduct_id(), orderDetail.getOrder_quantity());
        return "redirect:/order/sheet/"+order_id;
    }

    @GetMapping(value = "/order/sheet/{order_id}/confirm")
    public String orderConfirm(@PathVariable("order_id") int order_id){
        int orderState = 5;
        service.exchangeOrder(orderState, order_id);
        return "redirect:/order/sheet/"+order_id;
    }








}
