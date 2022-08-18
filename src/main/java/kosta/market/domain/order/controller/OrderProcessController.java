package kosta.market.domain.order.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosta.market.domain.order.model.OrderDetailDto;
import kosta.market.domain.order.model.OrderListDto;
import kosta.market.domain.order.model.OrderRequestDto;
import kosta.market.domain.order.service.OrderServiceImpl;
import kosta.market.domain.product.model.Product;
import kosta.market.domain.user.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
public class OrderProcessController {

    private final OrderServiceImpl service;

    //상품 주문form 페이지 호출
//    @GetMapping("/api/order/{product_id}")
//    public ResponseEntity addForm(@PathVariable("product_id") int product_id) throws JsonProcessingException {
//        ObjectMapper jsonData = new ObjectMapper();
//        Map<String, Object> data = new HashMap<>();
//        Map<String, Object> data2 = new HashMap<>();
//
//        // user_id 하드코딩으로 넘김 >> 추후 service단에서 SecurityUtil을 이용해서 받아옴
//        int user_id =1;
//
//        //주소
//        ArrayList<Address> addressList = (ArrayList<Address>)service.getAddress(user_id);
//        data2.put("addressList",addressList); // ArrayList도 잘 들어가는지 Test ~ing
//
//        // int product_id = 9; // 주소값에서 받아옴 @PathVariable
//        Product p = service.getProduct(product_id);
//        data2.put("p",p);
//        data.put("data",data2);
//
//        return ResponseEntity.ok(jsonData.writeValueAsString(data));
//        }

        // 상품주문
        @PostMapping(value = "/api/order/{product_id}")
        public ResponseEntity add(@PathVariable("product_id") int product_id, @RequestBody OrderRequestDto ord) throws JsonProcessingException{

            boolean flag = service.addOrder(ord, product_id);

            if (flag){
                int order_id = service.getLastOrderId();
                return ResponseEntity.ok().build();
            } else {
                // 주문실패 메세지를 어떻게 띄울까?
                return ResponseEntity.notFound().build();
            }

        }


        // 구매자,판매자 주문 리스트
        @GetMapping(value = "/api/order/sheet/list")
        public ResponseEntity orderListByUser(@RequestParam("user-type") int user_type) throws JsonProcessingException{
            ObjectMapper jsonData = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();

            ArrayList<OrderListDto> orderlist = null;

            if (Objects.equals(user_type, 1)){ // 구매자
                //하드코딩
                int user_id = 1;
                orderlist = service.listByUserIdOrder(user_id);
            } else if (Objects.equals(user_type, 2)) { // 판매자
                //하드코딩
                int seller_id = 1;
                orderlist = (ArrayList<OrderListDto>) service.listBySellerIdOrder(seller_id);
            } else {
                return ResponseEntity.notFound().build();
            }

            data.put("data", orderlist);

            return ResponseEntity.ok(jsonData.writeValueAsString(data));
        }







        // 주문 상세 정보
        @GetMapping(value = "/api/order/sheet/{order_id}")
        public ResponseEntity orderDetailForm(@PathVariable("order_id") int order_id) throws JsonProcessingException{
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();

        OrderDetailDto orderDetail = service.detailOrder(order_id);
        data2.put("orderDetail", orderDetail);
        data.put("data",data2);

        return ResponseEntity.ok(jsonData.writeValueAsString(data));
        }


        // 교환 신청
        @PostMapping(value = "/api/order/sheet/{order_id}/exchange")
        public ResponseEntity orderExchange(@PathVariable("order_id") int order_id, @RequestBody Map<String, Integer> body) throws JsonProcessingException{
            int order_state = body.get("order_state");
            boolean flag = service.exchangeOrder(order_id, order_state);

            if(flag){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }

        // 환불 신청 - 구매 취소로 통합

        // 구매 확정
        @PostMapping(value = "/api/order/sheet/{order_id}/confirm")
        public ResponseEntity orderConfirm(@PathVariable("order_id") int order_id, @RequestBody Map<String, Integer> body) throws JsonProcessingException{
            int order_state = body.get("order_state");
            boolean flag = service.confirmOrder(order_id,order_state);

            if(flag){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }

        // 주문 취소
        @PostMapping(value = "/api/order/sheet/{order_id}/cancel")
        public ResponseEntity orderCancel(@PathVariable("order_id") int order_id, @RequestBody Map<String, Integer> body) throws JsonProcessingException{
            int order_state = body.get("order_state");
            boolean flag = service.cancelOrder(order_id, order_state);

            if(flag){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }












    }







