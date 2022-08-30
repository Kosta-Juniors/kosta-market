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
import java.util.*;

//@CrossOrigin(allowCredentials = "true", originPatterns = "http://127.0.0.1:80")
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
        if(productService.createProduct(productCreateDto, imgFile)){
            return new ResponseEntity(HttpStatus.OK);
        } else{
            //json 변환작업에 사용되는 ObjectMapper
            ObjectMapper jsonData = new ObjectMapper();
            //ResponseEntity에 전달해 줄 data
            Map<String, Object> data = new HashMap();
            //errorMessage를 담아줄 map 객체
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }


    }

    //** 판매자가 등록한 상품리스트 출력 + 등록된 상품리스트 출력

    //** 상품리스트 처리 + 카테고리별 검색 + 상품명으로 검색
    // GetMapping으로 url 2개로 분리해서 하려고 했더니 매개변수로는
    // url 구분이 불가능함
    // 그래서 여러 개의 RequestParam을 받아올 수 있게 함


    // ** ser-type, 상품명+카테고리+user-type, 카테고리+user-type, 상품명+user-type
    // ** Top rated 상품 3가지 가져오기
    // ** 카테고리 관련 상품 목록 5가지 가져오기
    @GetMapping(value = "/api/product")
    public ResponseEntity productList(@RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId,
                                      @RequestParam(value = "user-type", required = false, defaultValue = "user") String userType,
                                      @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                      @RequestParam(value = "sortby", required = false, defaultValue = "") String sortBy,
                                      @RequestParam(value = "size", required = false, defaultValue = "0") Integer size) throws JsonProcessingException {

        //json 변환작업에 사용되는 ObjectMapper
        ObjectMapper jsonData = new ObjectMapper();
        //ResponseEntity에 전달해 줄 data
        Map<String, Object> data = new HashMap();
        //errorMessage를 담아줄 map 객체
        Map<String, Object> errorMessage = new HashMap<>();

        //data에 담길 ArrayList
        List<Object> productList = new ArrayList();
        //Service에서 넘어온 데이터를 받은 ArrayList
        ArrayList<ProductDto> productServiceList = null;

        // 평점이 가장 높은 상품 3가지 목록 출력
        if (sortBy.equals("top")) {
            if (size == 3) {
                ArrayList<ProductTopRatedDto> productTopList =
                        (ArrayList<ProductTopRatedDto>) productService.topRatedProductList();
                // productTopList가 null값이면
                if (Objects.equals(productTopList, null)) {

                    errorMessage.put("errorMessage", productService.errorMessage("content"));
                    data.put("data", errorMessage);
                    return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
                }
                // productTopList 가 존재하면

                for (int i = 0; i < productTopList.size(); i++) {
                    Map<String, Object> product = new HashMap<>();
                    product.put("score", productTopList.get(i).getScore());
                    product.put("productId", productTopList.get(i).getProductId());
                    product.put("productName", productTopList.get(i).getProductName());
                    product.put("productPrice", productTopList.get(i).getProductPrice());
                    product.put("productImgFileName", productTopList.get(i).getProductImgFileName());
                    product.put("productImgPath", productTopList.get(i).getProductImgPath());
                    productList.add(product);
                }

                data.put("data", productList);
                return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);
            }
        }
        // category 관련 상품 5가지 목록 출력

        if (size == 5) {
            productServiceList = (ArrayList<ProductDto>) productService.categoryProductList(categoryId);
        } else {
            productServiceList = (ArrayList<ProductDto>) productService.listProduct(userType, categoryId, search);
        }

        // productServiceList가 null값이면
        if (Objects.equals(productServiceList, null)) {

            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }

        // 상품 List를 Map 객체->ArrayList 객체에 담는 과정

        for (int i = 0; i < productServiceList.size(); i++) {
            Map<String, Object> product = new HashMap<>();
            product.put("productId", productServiceList.get(i).getProductId());
            product.put("productName", productServiceList.get(i).getProductName());
            product.put("productPrice", productServiceList.get(i).getProductPrice());
            product.put("productDescription", productServiceList.get(i).getProductDescription());
            product.put("productImgFileName", productServiceList.get(i).getProductImgFileName());
            product.put("productImgPath", productServiceList.get(i).getProductImgPath());
            product.put("productQuantity", productServiceList.get(i).getProductQuantity());

            productList.add(product);
        }
        data.put("data", productList);

        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);
    }

    //** 등록된 상품 정보 수정
    @PatchMapping(value = "/api/product/{product_id}")
    public ResponseEntity productUpdate(@RequestPart(value = "productUpdateDto") ProductDto productDto,
                                        @RequestPart(value = "imgFile", required = false) MultipartFile imgFile,
                                        @PathVariable("product_id") Integer productId)
            throws IOException {

        //json 변환작업에 사용되는 ObjectMapper
        ObjectMapper jsonData = new ObjectMapper();
        //ResponseEntity에 전달해 줄 data
        Map<String, Object> data = new HashMap();
        //errorMessage를 담아줄 map 객체
        Map<String, Object> errorMessage = new HashMap<>();

        // 파일 업로드를 하지 않을 경우 기존 파일 제거 X
        if (Objects.equals(productDto, null)) {

            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productDto, imgFile);
        return new ResponseEntity(HttpStatus.OK);
    }

    //**상품 삭제
    @DeleteMapping(value = "/api/product/{product_id}")
    public ResponseEntity productDelete(@PathVariable("product_id") Integer productId) throws JsonProcessingException{
        if(productService.deleteProduct(productId)) {
            return new ResponseEntity(HttpStatus.OK);
        }else{
            //json 변환작업에 사용되는 ObjectMapper
            ObjectMapper jsonData = new ObjectMapper();
            //ResponseEntity에 전달해 줄 data
            Map<String, Object> data = new HashMap();
            //errorMessage를 담아줄 map 객체
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }

    }

    //** 상품 상세 정보 조회
    @GetMapping(value = "/api/product/{product_id}")
    public ResponseEntity productDetail(@PathVariable("product_id") Integer productId) throws JsonProcessingException {

        //json 형식으로 바꿔줄 ObjectMapper 객체
        ObjectMapper jsonData = new ObjectMapper();

        //ResponseEntity에 넘겨 줄 data 객체
        Map<String, Object> data = new HashMap<>();
        // errorMessage를 담을 Map 객체
        Map<String, Object> errorMessage = new HashMap<>();


        // productId에 해당되는 categoryId, categoryName 받아오기
        Category category = productService.detailCategory(productId);
        // productId에 해당하는 상품 정보 받아오기
        ProductDto productDetailDto = productService.detailProduct(productId);


        if (Objects.equals(category, null)) {

            // productId 값을 갖은 category 존재 x
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);

            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }

        if (Objects.equals(productDetailDto, null)) {

            // productId 값을 갖은 상품 정보가 존재하지 않을 시
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }

        // data 에 담길 Map 형식 객체
        Map<String, Object> productDetail = new HashMap<>();
        // 추후에 productImgPath 관련 추가해야 함
        productDetail.put("productId", productDetailDto.getProductId());
        productDetail.put("productName", productDetailDto.getProductName());
        productDetail.put("productPrice", productDetailDto.getProductPrice());
        productDetail.put("productImgFileName", productDetailDto.getProductImgFileName());
        productDetail.put("productDescription", productDetailDto.getProductDescription());
        productDetail.put("productQuantity", productDetailDto.getProductQuantity());
        productDetail.put("categoryName", category.getCategoryName());
        productDetail.put("categoryId", category.getCategoryId());
        data.put("data", productDetail);

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
        Map<String, Object> data = new HashMap();

        List<Object> categoryList = new ArrayList();
        ArrayList<Category> categoryServiceList = (ArrayList<Category>) productService.listCategory();

        //카테고리 목록이 존재하지 않으면
        if (Objects.equals(categoryServiceList, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }

        for (int i = 0; i < categoryServiceList.size(); i++) {
            Map<String, Object> category = new HashMap<>();
            category.put("categoryId", categoryServiceList.get(i).getCategoryId());
            category.put("categoryName", categoryServiceList.get(i).getCategoryName());
            categoryList.add(category);
        }

        data.put("data", categoryList);

        // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

    }


    // 장바구니 관련 - 추후에 분리할 수 있음

    //** 장바구니 등록
    @PostMapping(value = "/api/product/cart")
    public ResponseEntity productCartCreate(@RequestBody(required = false) Map<String,CartDto> cart) throws JsonProcessingException {

        // cart int seller_id=1
        ObjectMapper jsonData = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        CartDto cartDto = cart.get("data");

        if (Objects.equals(cartDto, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // 장바구니 추가
            productService.createCart(cartDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //** 장바구니 불러오기
    @GetMapping(value = "/api/product/cart")
    public ResponseEntity productCartList() throws JsonProcessingException {

        Integer userId = 1; // 하드 코딩
        ObjectMapper jsonData = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        List<Object> cartList = new ArrayList();

        ArrayList<CartListDto> cartServiceList = (ArrayList<CartListDto>) productService.listCart(userId);

        if (Objects.equals(cartServiceList, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        }

        for (int i = 0; i < cartServiceList.size(); i++) {
            Map<String, Object> cart = new HashMap<>();

            cart.put("productId", cartServiceList.get(i).getProductId());
            cart.put("quantity", cartServiceList.get(i).getQuantity());
            cart.put("productName", cartServiceList.get(i).getProductName());
            cart.put("productPrice", cartServiceList.get(i).getProductPrice());
            cart.put("productImgFileName", cartServiceList.get(i).getProductImgFileName());
            cart.put("productImgPath", cartServiceList.get(i).getProductImgPath());
            cart.put("productQuantity", cartServiceList.get(i).getProductQuantity());


            cartList.add(cart);
        }

        data.put("data", cartList);

        // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

    }

    // 장바구니 수정
    @PatchMapping(value = "/api/product/cart")
    public ResponseEntity productCartUpdate(@RequestBody(required = false) Map<String,CartDto> cart) throws JsonProcessingException {


        ObjectMapper jsonData = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        CartDto cartDto = cart.get("data");

        //cartDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(cartDto, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // cartDto 수정
            productService.updateCart(cartDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    // 장바구니 삭제
    @DeleteMapping(value = "/api/product/cart")
    public ResponseEntity productCartDelete(@RequestBody(required = false) Map<String,CartDto> cart) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        CartDto cartDto = cart.get("data");
//        int userId = 1; productId=22
//        cartDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(cartDto, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // cartDto 삭제
            productService.deleteCart(cartDto.getUserId(), cartDto.getProductId());
            return new ResponseEntity(HttpStatus.OK);
        }

    }


    // 댓글 관련 기능
    //** 댓글 등록
    @PostMapping(value = "/api/product/{product_id}/comment")
    public ResponseEntity productCommentCreate(@PathVariable("product_id") Integer productId,
                                               @RequestBody(required = false) Map<String,CommentDto> comment) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        CommentDto commentDto= comment.get("data");

        if (Objects.equals(commentDto, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // 장바구니 추가
            productService.createComment(commentDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //** 댓글 수정에 필요한 정보 가져오기
    @GetMapping(value = "/api/product/{product_id}/comment/{comment_id}")
    public ResponseEntity productCommentDetail(@PathVariable("product_id") Integer productId,
                                               @PathVariable("comment_id") Integer commentId) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        CommentDto commentDto = productService.detailComment(commentId);

        if (Objects.equals(commentDto, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);

        } else {

            Map<String, Object> comment = new HashMap<>();

            comment.put("commentId", commentDto.getCommentId());
            comment.put("orderId", commentDto.getOrderId());
            comment.put("productId", commentDto.getProductId());
            comment.put("score", commentDto.getScore());
            comment.put("content", commentDto.getContent());
            data.put("data", comment);

            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);
        }


    }

    //** 댓글 수정
    @PatchMapping(value = "/api/product/{product_id}/comment/{comment_id}")
    public ResponseEntity productCommentUpdate(@PathVariable("product_id") Integer productId,
                                               @PathVariable("comment_id") Integer commentId,
                                               @RequestBody(required = false) Map<String,CommentDto> comment) throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        CommentDto commentDto = comment.get("data");
        Integer userId = 1;
        // 추후에 세션에서 가져올 거임
        // userId =1 , orderId=41-45;
        //commentDto 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(commentDto, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // 댓글 수정
            productService.updateComment(userId, commentId, commentDto);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    //** 댓글 삭제
    @DeleteMapping(value = "/api/product/{product_id}/comment/{comment_id}")
    public ResponseEntity productCartDelete(@PathVariable("product_id") Integer productId,
                                            @PathVariable("comment_id") Integer commentId) throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        Integer userId = 1; // 하드 코딩 추후에 세션에서 가져올 거임
//         // userId =1 , orderId=41-45;
//        orderId 값이 존재하지 않을 경우 HTTP STATUS NOT_FOUND로 설정
        if (Objects.equals(commentId, 0)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // commentDto 삭제
            productService.deleteComment(userId, commentId);
            return new ResponseEntity(HttpStatus.OK);
        }

    }

    //** 댓글 리스트 출력
    @GetMapping(value = "/api/product/{product_id}/comment")
    public ResponseEntity productCommentList(@PathVariable("product_id") Integer productId,
                                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                             @RequestParam(value = "user_id", required = false) Integer userId)
            throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        List<Object> commentList = new ArrayList();

        ArrayList<CommentListDto> commentServiceList = (ArrayList<CommentListDto>) productService.listComment(productId, page, size, userId);
        userId = 1; // 하드 코딩

        // 댓글 목록이 반환되지 않는다면
        if (Objects.equals(commentServiceList, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString

            for (int i = 0; i < commentServiceList.size(); i++) {
                Map<String, Object> comment = new HashMap<>();
                comment.put("commentId", commentServiceList.get(i).getCommentId());
                comment.put("orderId", commentServiceList.get(i).getOrderId());
                comment.put("productId", commentServiceList.get(i).getProductId());
                comment.put("name", commentServiceList.get(i).getName());
                comment.put("score", commentServiceList.get(i).getScore());
                comment.put("content", commentServiceList.get(i).getContent());
                comment.put("createdAt", commentServiceList.get(i).getCreatedAt());
                commentList.add(comment);
            }
            data.put("data", commentList);

            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }



    // 추가해야 할 기능

    // 상품 리뷰 개수 가져오기 count

    @GetMapping(value = "/api/product/{product_id}/comment/count")
    public ResponseEntity productCommentCount(@PathVariable("product_id") int productId) throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> commentCount = new HashMap<>();

        Integer productCommentCount = productService.countProductComment(productId);
        // 댓글 목록이 반환되지 않는다면
        if (Objects.equals(productCommentCount, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);
        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString

            commentCount.put("commentCount",productCommentCount);
            data.put("data",commentCount);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }

    //** 상품 개수 가져오기

    @GetMapping(value = "/api/product/count")
    public ResponseEntity productCount(@RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId,
                                       @RequestParam(value = "productName", required = false, defaultValue = "") String productName)
            throws JsonProcessingException {

        ObjectMapper jsonData = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> productCount = new HashMap<>();

        Integer productServiceCount = productService.countProduct(productName, categoryId);

        // 댓글 목록이 반환되지 않는다면
        if (Objects.equals(productServiceCount, null)) {
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("errorMessage", productService.errorMessage("content"));
            data.put("data", errorMessage);
            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.NOT_FOUND);

        } else {
            // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
            productCount.put("productCount", productServiceCount);
            data.put("data", productCount);

            return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

        }
    }
}
