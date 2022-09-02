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
        if (!Objects.equals(imgFile, null)) {
            // 파일이 있는지 체크
            if (!imgFile.isEmpty()) {
                fileName = saveImg(imgFile);
            }
        }

        // 파일 이미지 저장
        productCreateDto.setProductImgFileName(fileName);
        productCreateDto.setProductImgPath(basePath);

        if(!Objects.equals(productCreateDto.getCategoryId(),null)) {
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
                Integer productId = productMapper.selectLastInsertId();


                productMapper.insertProductCategory(productId, (int)productCreateDto.getCategoryId());

                // 추후 session에서 받아온 sellerId를 입력할 메소드 필요함
                productMapper.insertSellerProduct(sellerId, productId);

                return true;
            }
        }
        return false;
    }

    //** 상품 리스트 출력
    //usertype이 seller인 경우 판매자용으로
    // user인 경우 등록된 전체 상품 출력
    @Override
    public List<ProductListDto> listProduct(String userType, Integer categoryId, String sortBy, String productName,Integer size, Integer page) {
        // 판매자가 등록한 상품 정보가 없으면 null값 반환 or 정보 존재 시 상품 리스트 반환

        // mapper에 전달할 query 문
        String subQuery = "";


        List<ProductListDto> productList = null;
        // 구매자는 default 값으로 userType을 "user"로 지정했기 때문에 별도의 subQuery를 지정할 필요가 없음

        //판매자일 경우
        if (userType.equals("SELLER")) {
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
        subQuery += " ORDER BY ";

        if(sortBy.equals("default")){
            subQuery += "A.product_id ";
        } else if(sortBy.equals("top")){
            subQuery += "D.score DESC ";
        } else if(sortBy.equals("related")){
            subQuery += "RAND() ";
        }

        if(userType.equals("SELLER")){}
        else{ subQuery += " LIMIT "+(page-1)*size+","+size;}

        // Mapper에서 상품리스트 자료를 받아옴
        productList = productMapper.selectProductList(subQuery);

        // 만약 리스트가 존재하지 않는다면 null값 반환
        if (Objects.equals(productList, null)) {
            return null;
        } else {
            return productList;
        }
    }

    // ** 상품 상세 정보 기능
    @Override
    public ProductDto detailProduct(Integer productId) {

        //productId에 해당하는 상품상세정보 조회
        ProductDto productDetailDto = productMapper.selectProductDetail(productId);

        //productDetailDto가 존재하지 않을 경우
        if (Objects.equals(productDetailDto, null)) {
            return null;
        }

        return productDetailDto;
    }

//**등록한 상품 정보 수정
// product_id값에 해당하는 TBL_Category 값이 잇으면 category 반환
// 아니면 null 반환

    //productId에 해당하는 category 값 가져오기
    @Override
    public Category detailCategory(Integer productId) {

        Category category = productMapper.selectCategory(productId);

        if (Objects.equals(category, null)) {
            return null;
        } else {
            return category;
        }
    }

    //** 상품 정보 수정

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
            }
        } else {
            productUpdateDto.setProductImgFileName(productUpdateDto.getProductImgFileName());
            productUpdateDto.setProductImgPath(basePath);
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
    public boolean deleteProduct(Integer productId) {
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
    public List<Category> listCategory() {

        List<Category> categoryMapperList = productMapper.selectCategoryList();
        // 카테고리 목록이 존재하지 않으면
        if (Objects.equals(categoryMapperList, null)) {
            return null;
        } else {
            return categoryMapperList;
        }


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
    public boolean deleteCart(Integer userId, Integer productId) {
        int flag = productMapper.deleteCart(userId, productId);
        if (flag == 1) return true;
        else return false;
    }

    //** 장바구니 리스트 출력
    @Override
    public List<CartListDto> listCart(Integer userId) {

        List<CartListDto> cartMapperList = productMapper.selectCart(userId);

        if (Objects.equals(cartMapperList, null)) {
            return null;
        }

        return cartMapperList;
    }

    //* 댓글 관련 기능

    //** 댓글 생성
    @Override
    public boolean createComment(CommentDto commentDto) {

        // 평점이 1~10 범위를 벗어난 경우 댓글 생성 불가
        if (commentDto.getScore() < 1 || commentDto.getScore() > 10) {
            return false;
        }
        // 주문완료 후 이미 작성한 댓글이 있는지 유무를 판별하는 기능
        if (productMapper.selectDuplicateComment(commentDto.getOrderId()) == 1) {
            return false;
        } else {
            // 댓글을 생성한다.
            productMapper.insertComment(commentDto.getOrderId(), commentDto.getProductId(), commentDto.getScore(),
                    commentDto.getContent());
            return true;
        }

    }
    //** 댓글 수정 전 자료 가져오기

    @Override
    public CommentDto detailComment(Integer commentId) {

        // commentId가 잘못된 값일 경우 null을 반환
        if (productMapper.selectCountById(commentId) == 0) {
            return null;
        }
        CommentDto commentDto = productMapper.selectComment(commentId);

        // commentDto가 존재하지 않을 경우
        if (Objects.equals(commentDto, null)) {
            return null;
        } else {
            return commentDto;
        }

    }


    //** 댓글 수정 기능

    @Override
    public boolean updateComment(Integer userId, Integer commentId, CommentDto commentDto) {


        // 삭제된 댓글이라 수정할 수 없을 경우
        if (Objects.equals(productMapper.selectComment(commentId), null)) {
            return false;
        }

        // 평점이 1~10 범위를 벗어난 경우 업데이트 불가
        if (commentDto.getScore() < 1 || commentDto.getScore() > 10) {
            return false;
        }

        // orderId : 41~45
        // 평점만 혹은 댓글만 수정하는 경우를 구분하기 위해 subquery로 조건을 나눈다.
        String subquery = "";


        // 평점 수정 시 commentDto의 score가 null이 아니면
        if (!Objects.equals(commentDto.getScore(), null)) {
            // 기존 평점과 다를 경우
            if (!Objects.equals(commentDto.getScore(), productMapper.selectComment(commentId).getScore())) {
                // 판매자 초기목록
                subquery += " ,A.score=" + commentDto.getScore();
            }
        }
        // 댓글 수정 시 commentDto의 score가 null이 아니면
        if (!Objects.equals(commentDto.getContent(), null)) {
            //기존 코멘트 내용과 다른 경우
            if (!commentDto.getContent().equals(productMapper.selectComment(commentId).getContent())) {
                subquery += " ,A.content='" + commentDto.getContent() + "'";
            }
        }
        System.out.println(subquery);
        System.out.println(commentDto.getOrderId());
        if (productMapper.updateComment(userId, commentDto.getOrderId(), subquery) == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 댓글 삭제
    @Override
    public boolean deleteComment(Integer userId, Integer commentId) {

        //제대로 삭제가 되어있지 않으면 false 반환
        if (productMapper.deleteComment(userId, commentId) == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 사용자가 작성한 댓글 목록 or 상품에 담긴 댓글 리스트 출력
    @Override
    public List<CommentListDto> listComment(Integer productId, Integer page, Integer size, Integer userId) {


        List<CommentListDto> commentList = null;


        String subQuery = "";

        //userId가 null 값이 아니면
        if (!Objects.equals(userId, null)) {
            subQuery += " AND D.user_id=" + userId;
        }

        // mapper 쪽에서 데이터 받아오기
        commentList = productMapper.selectCommentList(productId, (page - 1) * size, size, subQuery);

        // 댓글 목록이 존재하지 않으면
        if (Objects.equals(commentList, null)) {
            return null;
        } else {
            return commentList;
        }
    }

    //** 상품에 달린 댓글 개수 반환
    @Override
    public Integer countProductComment(Integer productId) {

        Integer countProductComment = productMapper.selectCommentCountByproductId(productId);

        // 상품에 등록된 댓글이 없을 경우
        if (Objects.equals(countProductComment, null)) {
            return null;
        } else {
            return countProductComment;
        }
    }

    //** 상품 개수 반환
    @Override
    public Integer countProduct(String userType, Integer categoryId, String productName) {

        Integer productCount = 0;
        String subQuery = "";

        //판매자일 경우
        if (userType.equals("SELLER")) {
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
        productCount = productMapper.selectProductCount(subQuery);

        //값이 존재하지 않으면
        if (Objects.equals(productCount, null)) {
            return null;
        } else {

            return productCount;
        }
    }

    // error 메서지 처리
    //** 에러 메시지 종류애 따라 처리
    @Override
    public String errorMessage(String error) {

        String errorMessage = "";

        if (error.equals("file")) {
            errorMessage = "FileNotFound";
        } else if (error.equals("content")) {
            errorMessage = "ContentNotFound";
        }

        return errorMessage;
    }
}

