package kosta.market.domain.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kosta.market.domain.product.model.*;
import kosta.market.domain.product.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;


    //메뉴로 이동
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    //** 상품 등록
    // 카테고리 리스트 가져오기
    @GetMapping(value = "/product/create")
    public String createProduct(Model model) {
        return "product/create";
    }

    @PostMapping(value = "/api/product")
    public ResponseEntity productCreate(@RequestPart(value = "product") ProductCreateDto productCreateDto,
                                        @RequestPart(value = "imgFile", required = false) MultipartFile imgFile)
            throws IOException {
        productService.createProduct(productCreateDto, imgFile);
        return new ResponseEntity(HttpStatus.OK);

    }

    //** 판매자가 등록한 상품리스트 출력 + 등록된 상품리스트 출력

    @GetMapping(value = "/product/list")
    public String listProduct(Model model, @RequestParam("user-type") String usertype) {

        ArrayList<ProductListDto> productList = (ArrayList<ProductListDto>) productService.ListProduct(usertype);
        model.addAttribute("ProductList", productList);
        if (usertype.equals("seller")) {
            return "product/seller-list";
        } else if (usertype.equals("user")) {
            return "product/user-list";
        }
        return "index";
    }

    // 아직 처리할 정보가 없음

    @GetMapping(value = "/api/product/list")
    public void productList(@RequestParam("user-type") String usertype) {


    }

    //** 등록된 상품 정보 수정
    @GetMapping(value = "/product/{product_id}/update")
    public String updateProduct(Model model, @PathVariable("product_id") int product_id) {

//        Category category = productService.detailCategory(product_id);
//        ProductDto productModifyDto = productService.detailProduct(product_id);
//        model.addAttribute("Category", category);
//        model.addAttribute("Product", productModifyDto);
        return "product/update";
    }

    @PatchMapping(value = "/api/product/{product_id}")
    public String productUpdate(ProductDto productUpdateDto, @PathVariable("product_id") int product_id) {
        productService.deleteImg(productUpdateDto.getProductImgFileName());
        productService.updateProduct(productUpdateDto);
        return "redirect:/product/list?user-type=seller";
    }
    //**상품 삭제

    //
    @GetMapping(value = "/product/{product_id}/delete")
    public String deleteProduct(@PathVariable("product_id") int product_id) {
        return "product/seller-list";
    }

    @DeleteMapping(value = "/api/product/{product_id}")
    public String productDelete(@PathVariable("product_id") int product_id) {
        productService.deleteProduct(product_id);
        return "redirect:/product/" + product_id + "/delete";
    }

    //** 상품 상세 정보 조회
    @GetMapping(value = "/product/{product_id}")
    public String detailProduct(@PathVariable("product_id") int product_id) {
      return "product/detail";
    }

    // 아직 처리해야 될 부분 없음
    @GetMapping(value = "/api/product/{product_id}")
    public ResponseEntity productDetail(@PathVariable("product_id") int productId) throws JsonProcessingException{

        ObjectMapper jsonData = new ObjectMapper();
        Map<String,Object> data = productService.detailProduct(productId);

        return new ResponseEntity(jsonData.writeValueAsString(data),HttpStatus.OK);
    }

    // 상품 이름으로 검색
    @GetMapping(value = "/product/productnamelist")
    public String listProductName(Model model, @RequestParam("product_name") String product_name) {
        ArrayList<ProductListDto> productNameListDto = (ArrayList<ProductListDto>) productService.listProductName(product_name);
        model.addAttribute("ProductList", productNameListDto);
        return "product/user-list";
    }

    // 상품 이미지 출력
    @GetMapping(value = "/product/img")
    public ResponseEntity<byte[]> productImg(@RequestParam("product_img_file_name") String product_img_file_name) {
        return productService.imgProduct(product_img_file_name);
    }

    // 카테고리 목록 출력
    @GetMapping(value = "/api/category")
    public ResponseEntity category() throws JsonProcessingException {
        ObjectMapper jsonData = new ObjectMapper();
        Map<String,Object> data = productService.listCategory();

        // Map 형식 데이터인 data를 JSON 방식으로 바꿔주는 것 : writeValuesAsString
        return new ResponseEntity(jsonData.writeValueAsString(data), HttpStatus.OK);

    }

}
