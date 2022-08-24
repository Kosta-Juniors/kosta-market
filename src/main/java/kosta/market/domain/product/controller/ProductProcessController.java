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
import java.util.HashMap;
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
    public ResponseEntity productUpdate(@RequestPart(value = "productUpdateDto") ProductDto productDto,
                                        @RequestPart(value = "imgFile", required = false) MultipartFile imgFile,
                                        @PathVariable("product_id") int productId)
            throws IOException {
        // 파일 업로드를 하지 않을 경우 기존 파일 제거 X

        productService.updateProduct(productDto, imgFile);
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

        ObjectMapper jsonData = new ObjectMapper();

//        int userId = 1; productId=22
//        cartDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(cartDto, null)) {
            Map<String, Object> data = productService.errorMessage("content");

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            // cartDto 삭제
            productService.deleteCart(cartDto.getUserId(), cartDto.getProductId());
            return new ResponseEntity(HttpStatus.OK);
        }

    }


    // 댓글 관련 기능
    //** 댓글 등록
    @PostMapping(value = "/api/product/{product_id}/comment")
    public ResponseEntity productCommentCreate(@PathVariable("product_id") int productId,
                                               @RequestBody(required = false) CommentDto commentDto) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();

        if (Objects.equals(commentDto, null)) {
            Map<String, Object> data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // 장바구니 추가
            productService.createComment(commentDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //** 댓글 수정에 필요한 정보 가져오기
    @GetMapping(value = "/api/product/{product_id}/comment/{comment_id}")
    public ResponseEntity productCommentDetail(@PathVariable("product_id") int productId,
                                               @PathVariable("comment_id") int commentId) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        if (Objects.equals(data, null)) {
            data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);

        } else {
            data = productService.detailComment(commentId);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);
        }


    }

    //** 댓글 수정
    @PatchMapping(value = "/api/product/{product_id}/comment/{comment_id}")
    public ResponseEntity productCommentUpdate(@PathVariable("product_id") int productId,
                                               @PathVariable("comment_id") int commentId,
                                               @RequestBody(required = false) CommentDto commentDto) throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        int userId = 1;
        // 추후에 세션에서 가져올 거임
        // userId =1 , orderId=41-45;
        //commentDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(commentDto, null)) {
            Map<String, Object> data = productService.errorMessage("content");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            // 댓글 수정
            productService.updateComment(userId, commentId, commentDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //** 댓글 삭제
    @DeleteMapping(value = "/api/product/{product_id}/comment/{comment_id}")
    public ResponseEntity productCartDelete(@PathVariable("product_id") int productId,
                                            @PathVariable("comment_id") Integer commentId) throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        int userId = 1; // 하드 코딩 추후에 세션에서 가져올 거임
//         // userId =1 , orderId=41-45;
//        orderId 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(commentId, 0)) {
            Map<String, Object> data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // commentDto 삭제
            productService.deleteComment(userId, commentId);
            return new ResponseEntity(HttpStatus.OK);
        }

    }

    //** 댓글 리스트 출력
    @GetMapping(value = "/api/product/{product_id}/comment")
    public ResponseEntity productCommentList(@PathVariable("product_id") int productId,
                                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                             @RequestParam(value = "user_id", required = false) Integer userId)
            throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        userId = 1; // 하드 코딩

        // 댓글 목록이 반환되지 않는다면
        if (Objects.equals(productService.listComment(productId, page, size, userId), null)) {
            data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
            data = productService.listComment(productId, page, size, userId);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }


    // 추가해야 할 기능
    //** Top rated 상품 3가지 가져오기
    @GetMapping(value = "/api/product/top")
    public ResponseEntity productListTopRated() throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();


        // 최고 평점 상품리스트가 존재하지 않으면
        if (Objects.equals(productService.topRatedProductList(), null)) {
            data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
            data = productService.topRatedProductList();
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }

    // 카테고리 관련 상품 5가지 - random, categoryId
    @GetMapping(value = "/api/product/{product_id}/category/{category_id}")
    public ResponseEntity productListCategory(@PathVariable("product_id") int productId, @PathVariable("category_id") int categoryId)
            throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        // 카테고리 관련 추천 상품이 존재하지 않는다면
        if (Objects.equals(productService.categoryProductList(categoryId), null)) {
            data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            data = productService.categoryProductList(categoryId);
            ;
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }

    // 상품 리뷰 개수 가져오기 count

    @GetMapping(value = "/api/product/{product_id}/comment/count")
    public ResponseEntity productCommentCount(@PathVariable("product_id") int productId) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        // 댓글 목록이 반환되지 않는다면
        if (Objects.equals(productService.countProductComment(productId), null)) {
            data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
            data = productService.countProductComment(productId);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }

    //** 상품 개수 가져오기

    @GetMapping(value = "/api/product/count")
    public ResponseEntity productCommentList(@RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId,
                                             @RequestParam(value = "productName", required = false, defaultValue = "") String productName)
            throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        // 댓글 목록이 반환되지 않는다면
        if (Objects.equals(productService.countProduct(productName, categoryId), null)) {
            data = productService.errorMessage("content");
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
            data = productService.countProduct(productName, categoryId);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }

}
