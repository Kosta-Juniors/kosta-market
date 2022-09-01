package kosta.market.domain.order.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosta.market.domain.order.model.*;
import kosta.market.domain.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class OrderProcessController {

    private final OrderServiceImpl service;

    //상품 주문form 페이지 호출
//    @GetMapping("/api/order/{product_id}")
//    public ResponseEntity addForm(@PathVariable("product_id") int productId) throws JsonProcessingException {
//        ObjectMapper jsonData = new ObjectMapper();
//        Map<String, Object> data = new HashMap<>();
//        Map<String, Object> data2 = new HashMap<>();
//
//        // user_id 하드코딩으로 넘김 >> 추후 service단에서 SecurityUtil을 이용해서 받아옴
//        int userId =1;
//
//        //주소
//        ArrayList<Address> addressList = (ArrayList<Address>)service.getAddress(userId);
//        data2.put("addressList",addressList); // ArrayList도 잘 들어가는지 Test ~ing
//
//        // int productId = 9; // 주소값에서 받아옴 @PathVariable
//        Product p = service.getProduct(productId);
//        data2.put("p",p);
//        data.put("data",data2);
//
//        return ResponseEntity.ok(jsonData.writeValueAsString(data));
//        }



//        // 상품주문 원본
//        @PostMapping(value = "/api/order/{product_id}")
//        public ResponseEntity add(@PathVariable("product_id") int productId, @RequestBody OrderRequestDto ord) throws JsonProcessingException{
//
//            boolean exceptionflag = service.handlingIdException(productId);
//
//            if(exceptionflag){ // 정상작동
//                boolean functionflag = service.addOrder(ord, productId);
//
//                if (functionflag){ // 주문성공 - insertOrder, insertPayment, insertOrderPayment, insertUserOrder 모두성공
//                    return ResponseEntity.ok().build();
//                } else { // 주문실패 - 메세지 뭐라고 출력?
//                    return ResponseEntity.notFound().build();
//                }
//            }
//
//            ObjectMapper jsonData = new ObjectMapper();
//            Map<String, Object> data = new HashMap<>();
//            Map<String, Object> errormessage = new HashMap<>();
//
//            // 예외처리
//            String message = "잘못된 productId입니다.";
//            errormessage.put("message", message);
//            data.put("error", errormessage);
//            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
//        }




    @PostMapping(value = "/api/order")
    public ResponseEntity add(@RequestParam String productList, @RequestBody HashMap<String,Object> common) throws JsonProcessingException{
        // common에 paymentMethod, addressId 가 들어있다.
        String paymentMethod = common.get("paymentMethod").toString();
        int addressId = (int) common.get("addressId");

        // [productId, orderQuantity] 받아오기
        // productList를 URL에 1:10,5:3,7:2 으로 받아온다 >> RequestParam >> Split으로 쪼개어 사용

//        //완규ver
//        List<Map<String, Object>> pList = new ArrayList<>();
//
//        for (String el : productList.split(",")) {
//            String[] dict = el.split(":");
//            Map<String, Object> data = new HashMap<>();
//
//            data.put("productId", dict[0]);
//            data.put("orderQuantity", dict[1]);
//
//            pList.add(data);
//        }

        // 완규ver에서 OrderRequestDto를 사용하는 수정 ver
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();

        for (String el : productList.split(",")) {
            String[] dict = el.split(":");
            OrderRequestDto order = new OrderRequestDto();

            order.setProductId(Integer.parseInt(dict[0]));
            order.setOrderQuantity(Integer.parseInt(dict[1]));

            orderRequestDtoList.add(order);
        }



        // paymentMethod, addressId 받아오기 - 수정필요

        // 추후 addressDTO(deliveryPlace, title, recipient, contact) 추가하여 addressId선택하지 않았을때, 주소지 등록기능 추가
        // 생략

        // 하드코딩
        int userId = 1;

        boolean Orderflag = service.addOrder(orderRequestDtoList, paymentMethod, addressId, userId);
        if(Orderflag){
            return ResponseEntity.ok(""); // 주문성공
        }
        return ResponseEntity.notFound().build(); //실패
    }


        // 구매자,판매자 주문 리스트
        @GetMapping(value = "/api/order/sheet/list")
        public ResponseEntity orderALLListByUser(@RequestParam("user-type") int userType) throws JsonProcessingException{
            ObjectMapper jsonData = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();

            if (Objects.equals(userType, 1)){ // 구매자
                int userId = 1; //하드코딩
                ArrayList<OrderListDto> orderAllList = service.listByUserIdOrder(userId);
                data.put("data", orderAllList);

            } else if (Objects.equals(userType, 2)) { // 판매자
                int sellerId = 1; //하드코딩
                ArrayList<OrderDetailForSellerDto> orderAllList = service.listBySellerIdOrder(sellerId);
                data.put("data", orderAllList);

            } else {
                return ResponseEntity.notFound().build(); // user-type이 1또는2가 아닐때는?
            }


            return ResponseEntity.ok(jsonData.writeValueAsString(data));
        }



    // 주문 상세 정보
    @GetMapping(value = "/api/order/sheet/{order_id}")
    public ResponseEntity orderDetailForm(@PathVariable("order_id") Object orderId) throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();


        // orderId가 null이 아니고 int인지 검사
        boolean exceptionflag = service.handlingIdException(orderId);

        if (exceptionflag) { // 정상작동

            // OrderDto(주문공통정보)
            OrderDto Order = service.selectOrder(Integer.parseInt(orderId.toString()));

            // OrderDetail(주문-상품개별정보)
            ArrayList<OrderDetailDto> OrderDetailList = service.selectOrderDetailDto(Integer.parseInt(orderId.toString()));

//            data.put("order",Order); //공통사항
            data.put("orderId",Order.getOrderId());
            data.put("orderDate",Order.getOrderDate());
            data.put("paymentPrice",Order.getPaymentPrice());
            data.put("paymentMethod",Order.getPaymentMethod());
            data.put("deliveryPlace",Order.getDeliveryPlace());
            data.put("contact",Order.getContact());
            data.put("recipient",Order.getRecipient());


            data.put("orderList",OrderDetailList);

            return ResponseEntity.ok(jsonData.writeValueAsString(data));
            }



        // 예외처리 - data2 변수명을 의미에 맞게 변경요망
        String message = "잘못된 orderId입니다.";
        data2.put("message", message);
        data.put("error", data2);
        return ResponseEntity.ok(jsonData.writeValueAsString(data));
//        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }







        // 교환 신청
        @PatchMapping(value = "/api/order/sheet/{order_id}/exchange")
        public ResponseEntity orderExchange(@PathVariable("order_id") int orderId, @RequestParam("product_id") int productId) throws JsonProcessingException{

            boolean exceptionflag1 = service.handlingIdException(orderId);
            boolean exceptionflag2 = service.handlingIdException(productId);

            if(exceptionflag1 & exceptionflag2){ // 정상작동
                boolean functionflag = service.exchangeOrder(orderId, productId);

                if(functionflag){
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.notFound().build();
            }

            ObjectMapper jsonData = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> errormessage = new HashMap<>();

            // 예외처리
            String message = "잘못된 orderId입니다.";
            errormessage.put("message", message);
            data.put("error", errormessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);

        }

        // 환불 신청 - 구매 취소로 통합

        // 구매 확정
        @PatchMapping(value = "/api/order/sheet/{order_id}/confirm")
        public ResponseEntity orderConfirm(@PathVariable("order_id") int orderId, @RequestParam("product_id") int productId) throws JsonProcessingException{

            boolean exceptionflag1 = service.handlingIdException(orderId);
            boolean exceptionflag2 = service.handlingIdException(productId);

            if(exceptionflag1 & exceptionflag2){ // 정상작동
                boolean functionflag = service.confirmOrder(orderId,productId);

                if(functionflag){
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.notFound().build();
            }

            ObjectMapper jsonData = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> errormessage = new HashMap<>();

            // 예외처리
            String message = "잘못된 orderId입니다.";
            errormessage.put("message", message);
            data.put("error", errormessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);

        }

        // 주문 취소
        @PatchMapping(value = "/api/order/sheet/{order_id}/cancel")
        public ResponseEntity orderCancel(@PathVariable("order_id") int orderId, @RequestParam("product_id") int productId) throws JsonProcessingException{

            boolean exceptionflag1 = service.handlingIdException(orderId);
            boolean exceptionflag2 = service.handlingIdException(productId);

            if(exceptionflag1 & exceptionflag2){ //정상작동
                boolean flag = service.cancelOrder(orderId, productId);

                if(flag){
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.notFound().build();
            }

            ObjectMapper jsonData = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> errormessage = new HashMap<>();

            // 예외처리
            String message = "잘못된 orderId입니다.";
            errormessage.put("message", message);
            data.put("error", errormessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }












    }







