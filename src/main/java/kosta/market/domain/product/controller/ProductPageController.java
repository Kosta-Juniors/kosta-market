//package kosta.market.domain.product.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
////@CrossOrigin(allowCredentials = "true", originPatterns = "http://127.0.0.1:80")
//@Controller
//public class ProductPageController {
//
//    //메뉴로 이동
//    @RequestMapping(value = "/index")
//    public String index() {
//        return "index";
//    }
//
//    //** 상품 등록
//
//    @GetMapping(value = "/product/create")
//    public String productCreate() {
//        return "product/create";
//    }
//
//    //** 판매자가 등록한 상품리스트 출력 + 등록된 상품리스트 출력
//
//    @GetMapping(value = "/product/list")
//    public String listProduct(@RequestParam("user-type") String usertype) {
//
//        if (usertype.equals("seller")) {
//            return "product/seller-list";
//        } else if (usertype.equals("user")) {
//            return "product/user-list";
//        }
//        return "index";
//    }
//
//    //** 등록된 상품 정보 수정
//    @GetMapping(value = "/product/{product_id}/update")
//    public String updateProduct(@PathVariable("product_id") int productId) {
//        return "product/update";
//    }
//
//    //**상품 삭제
//
//    @GetMapping(value = "/product/{product_id}/delete")
//    public String deleteProduct(@PathVariable("product_id") int productId) {
//        return "product/seller-list";
//    }
//
//    //** 상품 상세 정보 조회
//    @GetMapping(value = "/product/{product_id}")
//    public String detailProduct(@PathVariable("product_id") int productId) {
//        return "product/detail";
//    }
//
//    //** 카트 목록 불러오기
//    @GetMapping(value = "/product/cart")
//    public String cartList() {
//        return "product/cart";
//    }
//}
