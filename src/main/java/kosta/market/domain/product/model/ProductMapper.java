package kosta.market.domain.product.model;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {


        // ** 상품 추가 기능
        //PRODUCT 테이블에 추가
        @Insert("INSERT INTO TBL_PRODUCT VALUES(null, #{name}, #{price}, #{imgFileName}, #{imgPath}, #{description}, #{quantity}," +
                "now(),'N')")
        int insertProduct(@Param("name") String productName,
                          @Param("price") int productPrice,
                          @Param("imgFileName") String productImgFileName,
                          @Param("imgPath") String productImgPath,
                          @Param("description") String productDescription,
                          @Param("quantity") int productQuantity);

        //SELLER_PRODUCT 테이블에 추가
        @Insert("INSERT INTO TBL_SELLER_PRODUCT VALUES(null, ${sellerId}, ${productId})")
        int insertSellerProduct(@Param("sellerId") int sellerId, @Param("productId") int productId);

        //PRODUCT_CATEGORY 테이블에 추가
        @Insert("INSERT INTO TBL_PRODUCT_CATEGORY VALUES(null, ${productId}, ${categoryId})")
        int insertProductCategory(@Param("productId") int productId, @Param("categoryId") int categoryId);

        // 입력 시 가장 최근에 Auto increment 된 컬럼값 가져오기
        @Select("SELECT LAST_INSERT_ID()")
        int selectLastInsertId();


        // ** 판매자가 등록한 상품 전체 조회 기능
              @Select("SELECT A.* FROM TBL_PRODUCT as A " +
                "JOIN TBL_SELLER_PRODUCT as B ON A.product_id=B.product_id " +
                "WHERE B.seller_id=#{sellerId} " +
                      "AND deleted LIKE 'N' " +
                      "ORDER BY A.product_id")
        List<ProductListDto> selectProductSellerList(@Param("sellerId")int sellerId);

        // ** 등록된 상품 전체 조회
        @Select("SELECT * FROM TBL_PRODUCT ORDER BY product_id")
        List<ProductListDto> selectProductList();

        // ** 상품 상세 보기
        //상품 테이블에 대한 정보 가져오기
        @Select("SELECT * FROM TBL_PRODUCT WHERE product_id=#{productId}")
        ProductDto selectProductDetail(@Param("productId")int productId);
        //? 추가할까 고민인 것 판매자 ID 가져오기?

        // 상품 카테고리 가져오기
        @Select("SELECT * FROM TBL_CATEGORY AS A " +
                "JOIN TBL_PRODUCT_CATEGORY AS B ON A.category_id=B.category_id " +
                "WHERE B.product_id=#{productId}")
        Category selectCategory(@Param("productId")int productId);


        // 업데이트 기능(카테고리는 변경불가)
        @Update("UPDATE TBL_PRODUCT SET product_name=#{name},product_price=#{price}, product_img_file_name=#{imgFileName}," +
                "product_img_path=#{imgPath}, product_description=#{description}, product_quantity=#{quantity} " +
                "WHERE product_id=#{productId}")
        void updateProduct(@Param("productId")int productId,
                           @Param("name")String productName,
                           @Param("price")int productPrice,
                           @Param("imgFileName")String productImageFileName,
                           @Param("imgPath")String productImagePath,
                           @Param("description")String productDescription,
                           @Param("quantity")int productQuantity);


        //** 삭제기능

        // 상품테이블에서 상품 정보 삭제 : deleted 값만 변경
        @Update("UPDATE TBL_PRODUCT SET deleted='Y' WHERE product_id=${productId}")
        int deleteProduct(@Param("productId") int productId);

        //상품식별번호로 값 가져오기 - 수정 필요
        @Select("SELECT deleted FROM TBL_PRODUCT WHERE product_id=#{productId}")
        String selectProductByProductId(@Param("productId")int productId);


        //** 기존 계획에서 추가된 기능
        //카테고리 목록 가져오기
        @Select("SELECT * FROM TBL_CATEGORY ORDER BY category_id")
        List<Category> selectCategoryList();

        //상품명으로 검색
        @Select("SELECT * FROM TBL_PRODUCT " +
                "WHERE product_name LIKE '%${productName}%' " +
                "AND deleted LIKE 'N' " +
                "ORDER BY product_id")
        List<ProductListDto> selectProductListByName(@Param("productName")String productName);

//        // 파일 이름 중복 체크
//        @Select("SELECT count(*) FROM TBL_PRODUCT " +
//                "WHERE product_img_file_name=#{imgFileName}")
//        int selectProductByImgName(@Param("imgFileName")String product_img_file_name);

    }

