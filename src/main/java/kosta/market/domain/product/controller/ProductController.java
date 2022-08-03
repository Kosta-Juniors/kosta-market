package kosta.market.domain.product.controller;

import kosta.market.domain.product.model.*;
import kosta.market.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;


    //메뉴로 이동
    @RequestMapping(value="/index")
    public String index(){
        return "index";
    }

    //** 상품 등록
    // 카테고리 리스트 가져오기
    @GetMapping(value="/product/create")
    public String createProduct(Model model){
            ArrayList<Category> categoryList = null;
            categoryList=(ArrayList<Category>) productService.listCategory();
            model.addAttribute("categoryList",categoryList);
            return "product/create";
    }

    @PostMapping(value="/api/product")
    public String productCreate(ProductCreateDto productCreateDto){
        productService.createProduct(productCreateDto);
        return "redirect:/index";

    }

    //** 판매자가 등록한 상품리스트 출력 + 등록된 상품리스트 출력

    @GetMapping (value="/product/list")
    public String listProduct(Model model,@RequestParam("user-type") String usertype){
            System.out.println("hi");
            ArrayList<ProductListDto> productList = (ArrayList<ProductListDto>) productService.ListProduct(usertype);
            model.addAttribute("ProductList", productList);
            if(usertype.equals("seller")){
            return "product/seller-list";
        } else if(usertype.equals("user")){
                return "product/user-list";
        }
            return"index";
    }

    @PostMapping(value="/api/product/list")
    public void productList(@RequestParam("user-type") String usertype){
    }

    //** 등록된 상품 정보 수정
    @GetMapping(value="/product/{product_id}/update")
    public String updateProduct(Model model, @PathVariable("product_id") int product_id){
        System.out.println(product_id);
        Category category=productService.detailCategory(product_id);
        ProductDto productModifyDto=productService.detailProduct(product_id);
        model.addAttribute("Category",category);
        model.addAttribute("Product",productModifyDto);
        return "product/update";
    }
    @PostMapping(value="/api/product/{product_id}/update")
    public String productUpdate(ProductDto productUpdateDto,@PathVariable("product_id") int product_id){
        productService.updateProduct(productUpdateDto);
        return "redirect:/product/list?user-type=seller";
    }
    //**상품 삭제
    @PostMapping(value="/product/{product_id}/delete")
    public void deleteProduct(@PathVariable("product_id") int product_id){
    }

    @PostMapping(value="/api/product/{product_id}/delete")
    public String productDelete(@PathVariable("product_id") int product_id){
        productService.deleteProduct(product_id);
        return "redirect:/product/list?user-type=seller";
    }

    //** 상품 상세 정보 조회
    @GetMapping(value="/product/{product_id}")
    public String detailProduct(Model model,@PathVariable("product_id")int product_id){
        Category category=productService.detailCategory(product_id);
        ProductDto productDetailDto=productService.detailProduct(product_id);
        model.addAttribute("Category",category);
        model.addAttribute("Product",productDetailDto);
        return "product/detail";
    }

    @PostMapping(value="/api/product/{product_id}")
    public void productDetail(@PathVariable("product_id")int product_id){
    }

     // 상품 이름으로 검색
    @GetMapping(value="/product/productnamelist")
    public String listProductName(Model model,@RequestParam("product_name")String product_name){
        ArrayList<ProductListDto> productNameListDto = (ArrayList<ProductListDto>)productService.listProductName(product_name);
        model.addAttribute("ProductList",productNameListDto);
        return "product/user-list";
    }

    @PostMapping(value="/product/productnamelist")
    public void productNameList(){
    }

    // 상품 이미지 출력
    @RequestMapping(value="/product/img")
    public ResponseEntity<byte[]> productImg(@RequestParam("product_img_file_name") String product_img_file_name) {
        return productService.imgProduct(product_img_file_name);
    }
 }
