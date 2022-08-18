package kosta.market.domain.product.service.impl;


import kosta.market.domain.product.model.*;
import kosta.market.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    //우선 하드 코딩
    private int sellerId = 1;

    //이미지 파일 처리를 위한 것
    public String basePath = "C:\\kosta\\";

    //**상품 등록 기능
    @Override
    public boolean createProduct(ProductCreateDto productCreateDto, MultipartFile imgFile) {
        // 추후 session이 추가될 경우 추가할 문법 예시?
        //   int seller_id=session.getAttribute("seller_id");

        // 이미지를 입력 안 했을 경우를 대비해서 default.jpg로 잡아줌
        String fileName = "default.jpg";

        // 파일 이미지가 없을 경우
        // 변수 자체가 존재하는지를 체크
        if (Objects.equals(imgFile, null)) {
            // 파일이 있는지 체크
            if (!imgFile.isEmpty()) {
                fileName = saveImg(imgFile);
            }
        }

        // 파일 이미지 저장
        productCreateDto.setProductImgFileName(fileName);
        productCreateDto.setProductImgPath(basePath);

        //입력된 정보 삽입
        //productAddDto에 저장된 상품명이 null 값이 아니라면
        if (productCreateDto.getProductName() != null) {

            // TBL_PRODUCT DB에 상품내용 저장
            productMapper.insertProduct(productCreateDto.getProductName(),
                    productCreateDto.getProductPrice(),
                    productCreateDto.getProductImgFileName(),
                    productCreateDto.getProductImgPath(),
                    productCreateDto.getProductDescription(),
                    productCreateDto.getProductQuantity());


            // TBL_PRODUCT 에 상품 정보 저장 후 Auto increment 된 product_id 가져오기
            int productId = productMapper.selectLastInsertId();

            productMapper.insertProductCategory(productId, productCreateDto.getCategoryId());

            // 추후 session에서 받아온 sellerId를 입력할 메소드 필요함
            productMapper.insertSellerProduct(sellerId, productId);

            return true;
        } else {
            return false;
        }
    }

    //** 상품 리스트 출력
    //usertype이 seller인 경우 판매자용으로
    // user인 경우 등록된 전체 상품 출력
    @Override
    public Map<String, Object> listProduct(String userType, int categoryId, String productName) {
        // 판매자가 등록한 상품 정보가 없으면 null값 반환 or 정보 존재 시 상품 리스트 반환

        //ResponseEntity에 전달해 줄 data
        Map<String, Object> data = new HashMap();
        //data에 담길 ArrayList
        List<Object> productList = new ArrayList();
        //mapper에서 넘어온 데이터를 받은 ArrayList
        ArrayList<ProductListDto> productMapperList = null;
        // mapper에 전달할 query 문
        String subQuery = "";

        // 구매자는 default 값으로 userType을 "user"로 지정했기 때문에 별도의 subQuery를 지정할 필요가 없음

        //판매자일 경우
        if (userType.equals("seller")) {
            // 판매자 초기목록
            subQuery = "AND C.seller_id=" + sellerId;

        }
        // 상품명이 빈 값이 아니면
        if (!productName.isEmpty()) {
            subQuery += " AND A.product_name LIKE '%" + productName + "%'";


        }
        // 카테고리 값이 0이 아니면
        if (categoryId != 0) {
            subQuery += " AND B.category_id=" + categoryId;
        }
        System.out.println(subQuery);

        // Mapper에서 상품리스트 자료를 받아옴
        productMapperList = (ArrayList<ProductListDto>) productMapper.selectProductList(subQuery);

        // 만약 리스트가 존재하지 않는다면 null값 반환
        if (Objects.equals(productMapperList, null)) {
            return null;
        } else {

            for (int i = 0; i < productMapperList.size(); i++) {
                Map<String, Object> product = new HashMap<>();
                product.put("productId", productMapperList.get(i).getProductId());
                product.put("productName", productMapperList.get(i).getProductName());
                product.put("productPrice", productMapperList.get(i).getProductPrice());
                product.put("productImgFileName", productMapperList.get(i).getProductImgFileName());
                product.put("productImgPath", productMapperList.get(i).getProductImgPath());
                product.put("productQuantity", productMapperList.get(i).getProductQuantity());

                productList.add(product);
            }
            data.put("data", productList);
            return data;
        }
    }

    // ** 상품 상세 정보 기능
    @Override
    public Map<String, Object> detailProduct(int productId) {

        Category category = this.detailCategory(productId);
        ProductDto productDetailDto = productMapper.selectProductDetail(productId);

        //ResponseEntity에 넘겨 줄 data
        Map<String, Object> data = new HashMap<>();
        // data 에 담길 Map 방식 data
        Map<String, Object> productDetail = new HashMap<>();
        // 추후에 productImgPath 관련 추가해야 함
        productDetail.put("productId", productDetailDto.getProductId());
        productDetail.put("productName", productDetailDto.getProductName());
        productDetail.put("productPrice", productDetailDto.getProductPrice());
        productDetail.put("productImgFileName", productDetailDto.getProductImgFileName());
        productDetail.put("productDescription", productDetailDto.getProductDescription());
        productDetail.put("productQuantity", productDetailDto.getProductQuantity());
        productDetail.put("categoryName", category.getCategoryName());

        data.put("data", productDetail);

        return data;
    }

//**등록한 상품 정보 수정
// product_id값에 해당하는 TBL_Category 값이 잇으면 category 반환
// 아니면 null 반환

    @Override
    public Category detailCategory(int productId) {
        Category category = productMapper.selectCategory(productId);
        if (category != null) {
            return category;
        } else {
            return null;
        }
    }

    @Override
    public boolean updateProduct(ProductDto productUpdateDto, MultipartFile imgFile) {

        // 변수 자체가 존재하는지를 체크
        if (!Objects.equals(imgFile, null)) {

            // 파일 업로드를 하지 않을 경우 기존 파일 제거 X
            if (!imgFile.isEmpty()) {
                deleteImg(productUpdateDto.getProductImgFileName());
                String fileName = saveImg(imgFile);
                productUpdateDto.setProductImgFileName(fileName);
                productUpdateDto.setProductImgPath(basePath);
            } else {
                productUpdateDto.setProductImgFileName(productUpdateDto.getProductImgFileName());
                productUpdateDto.setProductImgPath(basePath);
            }
        }

        if (productUpdateDto != null) {
            productMapper.updateProduct(productUpdateDto.getProductId(),
                    productUpdateDto.getProductName(),
                    productUpdateDto.getProductPrice(),
                    productUpdateDto.getProductImgFileName(),
                    productUpdateDto.getProductImgPath(),
                    productUpdateDto.getProductDescription(),
                    productUpdateDto.getProductQuantity());
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
        if (productMapper.selectDeletedByProductId(productId).equals('N')) {
            flag = false;
        }
        return flag;
    }

//상품 카테고리 목록 가져오기 - 상품 등록

    @Override
    public Map<String, Object> listCategory() {

        Map<String, Object> data = new HashMap();

        List<Object> categoryList = new ArrayList();
        ArrayList<Category> categoryMapperList = (ArrayList<Category>) productMapper.selectCategoryList();

        for (int i = 0; i < categoryMapperList.size(); i++) {
            Map<String, Object> category = new HashMap<>();
            category.put("categoryId", categoryMapperList.get(i).getCategoryId());
            category.put("categoryName", categoryMapperList.get(i).getCategoryName());
            categoryList.add(category);
        }

        data.put("data", categoryList);

        return data;
    }

    //상품 파일이미지 추가 - 상품 이미지 파일 명 반환
    public String saveImg(MultipartFile productImgFile) {

        // 이미지 파일 타입(jpeg,png,...)을 가져오기 위한 변수
        // . 은 특수문자이므로 이를 위해 \\ 두 개를 추가 \은 특수문자를 일반문자처럼 인식하게 해 줌
        String[] fileNameArray = productImgFile.getOriginalFilename().split("\\.");

        // 무작위 일련번호 추가
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "." + fileNameArray[1];

        if (fileName != null && !fileName.equals("")) {
            File dir = new File(basePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(basePath + "\\" + fileName);
            try {
                productImgFile.transferTo(file);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return fileName;
    }

    //파일 이미지 삭제
    public void deleteImg(String productImgName) {

        String path = basePath + "\\";
        File imgDir = new File(path);
        if (imgDir.exists()) {
            File f = new File(path + productImgName);
            f.delete();
        }
        imgDir.delete();
    }

    // ** 파일 이미지 보여주기 (임시)
    public ResponseEntity<byte[]> imgProduct(String productImgName) {
        String path = basePath + "\\" + productImgName;
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


// * 장바구니 기능 관련

    //** 장바구니 추가
    @Override
    public boolean createCart(CartDto cartDto) {

        Integer check = productMapper.selectDuplicateCart(cartDto.getUserId(), cartDto.getProductId());

        System.out.println(check);
        // 동일한 상품에 대한 장바구니 데이터가 존재할 경우
        if (check != null) {
            productMapper.updateDuplicateCart(cartDto.getQuantity(), check);
            return true;
        } else {
            check = productMapper.insertCart(cartDto.getUserId(), cartDto.getProductId(), cartDto.getQuantity());
            //상품이 제대로 입력되었으면 flag=1 아니면 flag=0
        }
        if (check == 1) return true;
        else return false;
    }

    //** 장바구니 수정
    @Override
    public boolean updateCart(CartDto cartDto) {
        int flag = productMapper.updateCart(cartDto.getUserId(), cartDto.getProductId(), cartDto.getQuantity());
        //상품이 제대로 입력되었으면 flag=1 아니면 flag=0
        if (flag == 1) return true;
        else return false;
    }

    //** 장바구니 삭제
    @Override
    public boolean deleteCart(int userId, int productId) {
        int flag = productMapper.deleteCart(userId, productId);
        if (flag == 1) return true;
        else return false;
    }

    //** 장바구니 리스트 출력
    @Override
    public Map<String, Object> listCart(int userId) {

        Map<String, Object> data = new HashMap();

        List<Object> cartList = new ArrayList();
        ArrayList<CartListDto> cartMapperList = (ArrayList<CartListDto>) productMapper.selectCart(userId);

        for (int i = 0; i < cartMapperList.size(); i++) {
            Map<String, Object> cart = new HashMap<>();

            cart.put("productId", cartMapperList.get(i).getProductId());
            cart.put("quantity", cartMapperList.get(i).getQuantity());
            cart.put("productName", cartMapperList.get(i).getProductName());
            cart.put("productPrice", cartMapperList.get(i).getProductPrice());
            cart.put("productImgFileName", cartMapperList.get(i).getProductImgFileName());
            cart.put("productImgPath", cartMapperList.get(i).getProductImgPath());
            cart.put("productQuantity", cartMapperList.get(i).getProductQuantity());


            cartList.add(cart);
        }

        data.put("data", cartList);

        return data;
    }
}
