package kosta.market.domain.product.service.impl;


import kosta.market.domain.product.model.*;
import kosta.market.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    //우선 하드 코딩
    private int sellerId=1;

    //이미지 파일 처리를 위한 것
    public String basePath = "C:\\kosta\\";

    //**상품 등록 기능
    @Override
    public boolean createProduct(ProductCreateDto productCreateDto) {
        // 추후 session이 추가될 경우 추가할 문법 예시?
        //   int seller_id=session.getAttribute("seller_id");
        productCreateDto.setProduct_img_file_name(productCreateDto.getProduct_img_file().getOriginalFilename());
        productCreateDto.setProduct_img_path(basePath);
        //입력된 정보 삽입
        //productAddDto에 저장된 상품명이 null 값이 아니라면
        if(productCreateDto.getProduct_name()!=null){
            
            // TBL_PRODUCT DB에 상품내용 저장
            productMapper.insertProduct(productCreateDto.getProduct_name(),
                    productCreateDto.getProduct_price(),
                    productCreateDto.getProduct_img_file_name(),
                    productCreateDto.getProduct_img_path(),
                    productCreateDto.getProduct_description(),
                    productCreateDto.getProduct_quantity());
            
            // 파일 이미지 저장
            saveImg(productCreateDto);

            // TBL_PRODUCT 에 상품 정보 저장 후 Auto increment 된 product_id 가져오기
            int productId=productMapper.selectLastInsertId();

            productMapper.insertProductCategory(productId, productCreateDto.getCategory_id());

            // 추후 session에서 받아온 sellerId를 입력할 메소드 필요함
            productMapper.insertSellerProduct(sellerId,productId);

            return true;
        }
        else {
            return false;
        }
    }
    //** 상품 리스트 출력
    //usertype이 seller인 경우 판매자용으로
    // user인 경우 등록된 전체 상품 출력
    @Override
    public List<ProductListDto> ListProduct(String usertype) {
        // 판매자가 등록한 상품 정보가 없으면 null값 반환 or 정보 존재 시 상품 리스트 반환

        if(usertype.equals("seller")) {
            if (productMapper.selectProductSellerList(sellerId) == null) {
                return null;
            } else {
                System.out.println("hi");
                return productMapper.selectProductSellerList(sellerId);
            }
        }
        else if(usertype.equals("user")){
            if (productMapper.selectProductList() == null) {
                return null;
            } else {
                return productMapper.selectProductList();
            }
        } else{
            return null;
        }
    }
    // ** 상품 상세 정보 기능
    @Override
    public ProductDto detailProduct(int productId) {

        return productMapper.selectProductDetail(productId);
    }

    //**등록한 상품 정보 수정
    // product_id값에 해당하는 TBL_Category 값이 잇으면 category 반환
    // 아니면 null 반환
    
    @Override
    public Category detailCategory(int productId) {
        Category category=productMapper.selectCategory(productId);
        if (category!= null) {
            return category;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateProduct(ProductDto productModifyDto) {
        productModifyDto.setProduct_img_file_name(productModifyDto.getProduct_img_file().getOriginalFilename());
        productModifyDto.setProduct_img_path(basePath);
        updateImg(productModifyDto);
        if(productModifyDto!=null){
            productMapper.updateProduct(productModifyDto.getProduct_id(),
                                        productModifyDto.getProduct_name(),
                                        productModifyDto.getProduct_price(),
                                        productModifyDto.getProduct_img_file_name(),
                                        productModifyDto.getProduct_img_path(),
                                        productModifyDto.getProduct_description(),
                                        productModifyDto.getProduct_quantity());
            return true;
        } else {
            return false;
        }
    }

    //** 상품 삭제
    @Override
    public boolean deleteProduct(int productId) {
        boolean flag = true;

          productMapper.deleteProduct(productId);

          //삭제가 제대로 수행되지 않았을 경우
          if(productMapper.selectProductByProductId(productId)!=null){
              flag=false;
          }
              return flag;
    }
    
    //상품 카테고리 목록 가져오기 - 상품 등록
    
    @Override
    public List<Category> listCategory() {

        return productMapper.selectCategoryList();
    }

    //상품명으로 검색
    @Override
    public List<ProductListDto> listProductName(String productName) {
        if (productMapper.selectProductListByName(productName) == null) {
            return null;
        } else {
            return productMapper.selectProductListByName(productName);
        }
    }
    //상품 파일이미지 추가
    public void saveImg(ProductCreateDto productCreateDto) {
        String fileName = productCreateDto.getProduct_img_file().getOriginalFilename();
        if (fileName != null && !fileName.equals("")) {
            File dir = new File(basePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(basePath+ "\\" + fileName);
            try {
                productCreateDto.getProduct_img_file().transferTo(file);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    public void updateImg(ProductDto productDto) {
        String fileName = productDto.getProduct_img_file().getOriginalFilename();
        if (fileName != null && !fileName.equals("")) {
            File dir = new File(basePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(basePath+ "\\" + fileName);
            try {
                productDto.getProduct_img_file().transferTo(file);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    // ** 파일 이미지 보여주기 (임시)
    public ResponseEntity<byte[]> imgProduct(String product_img_name) {
        String path = basePath + "\\" + product_img_name;
        File file = new File(path);
        HttpHeaders httpHeaders = new HttpHeaders(); // 응답에 대한 설정
        ResponseEntity<byte[]> imgResult = null;
        try {
            httpHeaders.add("Content-Type", Files.probeContentType(file.toPath()));

            // file에 대한 부분을 byte 단위로 전송
            imgResult = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), httpHeaders, HttpStatus.OK);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return imgResult;
    }


}
