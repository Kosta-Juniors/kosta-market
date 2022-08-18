package kosta.market.domain.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosta.market.domain.product.model.*;
import kosta.market.domain.product.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
public class ProductProcessController {
    private final ProductServiceImpl productService;

    //** 상품 등록
    @PostMapping(value = "/api/product")
    public ResponseEntity productCreate(@RequestPart(value = "product") ProductCreateDto productCreateDto,
                                        @RequestPart(value = "imgFile", required = false) MultipartFile imgFile)
            throws IOException {

        // 추후 boolean 반환 타입을 이용한 예외처리 추가해야 함
        productService.createProduct(productCreateDto, imgFile);
        return new ResponseEntity(HttpStatus.OK);

    }

    //** 판매자가 등록한 상품리스트 출력 + 등록된 상품리스트 출력

    //** 상품리스트 처리 + 카테고리별 검색 + 상품명으로 검색
    // GetMapping으로 url 2개로 분리해서 하려고 했더니 매개변수로는
    // url 구분이 불가능함
    // 그래서 여러 개의 RequestParam을 받아올 수 있게 함


    // user-type, 상품명+카테고리+user-type, 카테고리+user-type, 상품명+user-type
    //
    @GetMapping(value = "/api/product")
    public ResponseEntity productList(@RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId,
                                      @RequestParam(value = "user-type", required = false, defaultValue = "user") String userType,
                                      @RequestParam(value = "search", required = false, defaultValue = "") String search) throws JsonProcessingException {

        // session애 저장된 seller_id가 존재할 경우~
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = productService.listProduct(userType, categoryId, search);


        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);
    }

    //** 등록된 상품 정보 수정
    @PatchMapping(value = "/api/product/{product_id}")
    public ResponseEntity productUpdate(@RequestPart(value = "productUpdateDto") ProductDto productUpdateDto,
                                        @RequestPart(value = "imgFile", required = false) MultipartFile imgFile,
                                        @PathVariable("product_id") int productId)
            throws IOException {
        // 파일 업로드를 하지 않을 경우 기존 파일 제거 X

        productService.updateProduct(productUpdateDto, imgFile);
        return new ResponseEntity(HttpStatus.OK);
    }

    //**상품 삭제
    @DeleteMapping(value = "/api/product/{product_id}")
    public ResponseEntity productDelete(@PathVariable("product_id") int productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity(HttpStatus.OK);
    }

    //** 상품 상세 정보 조회
    @GetMapping(value = "/api/product/{product_id}")
    public ResponseEntity productDetail(@PathVariable("product_id") int productId) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = productService.detailProduct(productId);

        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);
    }

    // 상품 이미지 출력
    @GetMapping(value = "/api/product/img")
    public ResponseEntity<byte[]> productImg(@RequestParam("product_img_file_name") String productImgFileName) {
        return productService.imgProduct(productImgFileName);
    }

    // 카테고리 목록 출력
    @GetMapping(value = "/api/category")
    public ResponseEntity category() throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = productService.listCategory();

        // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

    }


    // 장바구니 관련 - 추후에 분리할 수 있음

    //** 장바구니 등록
    @PostMapping(value = "/api/product/cart")
    public ResponseEntity productCartCreate(@RequestBody(required = false) CartDto cartDto) {

        // cart int seller_id=1

        if (Objects.equals(cartDto, null)) {
         return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            // 장바구니 추가
            productService.createCart(cartDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //** 장바구니 불러오기
    @GetMapping(value = "/api/product/cart")
    public ResponseEntity productCartList() throws JsonProcessingException {

        int userId = 1; // 하드 코딩
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = productService.listCart(userId);

        // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

    }

    // 장바구니 수정
    @PatchMapping(value = "/api/product/cart")
    public ResponseEntity productCartUpdate(@RequestBody(required = false) CartDto cartDto) {

        //cartDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(cartDto, null)) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            // cartDto 수정
            productService.updateCart(cartDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    // 장바구니 삭제
    @DeleteMapping(value = "/api/product/cart")
    public ResponseEntity productCartDelete(@RequestBody(required = false) CartDto cartDto) {

//        int userId = 1; productId=22
//        cartDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(cartDto, null)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            // cartDto 수정
            System.out.println(cartDto.getProductId());
            productService.deleteCart(cartDto.getUserId(), cartDto.getProductId());
            return new ResponseEntity(HttpStatus.OK);
        }

    }


}
