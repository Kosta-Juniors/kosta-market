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
    @Insert("INSERT INTO TBL_SELLER_PRODUCT VALUES(null, #{sellerId}, #{productId})")
    int insertSellerProduct(@Param("sellerId") int sellerId, @Param("productId") int productId);

    //PRODUCT_CATEGORY 테이블에 추가
    @Insert("INSERT INTO TBL_PRODUCT_CATEGORY VALUES(null, #{productId}, #{categoryId})")
    int insertProductCategory(@Param("productId") int productId, @Param("categoryId") int categoryId);

    // 입력 시 가장 최근에 Auto increment 된 컬럼값 가져오기
    @Select("SELECT LAST_INSERT_ID()")
    int selectLastInsertId();




    // 상품 리스트 불러오기
    @Select("SELECT A.product_id AS productId, A.product_name AS productName, A.product_price AS productPrice, " +
            "A.product_img_file_name AS productImgFileName, A.product_img_path AS productImgPath, " +
            "A.product_quantity AS productQuantity FROM TBL_PRODUCT AS A " +
            "LEFT JOIN TBL_PRODUCT_CATEGORY AS B ON A.product_id=B.product_id " +
            "LEFT JOIN TBL_SELLER_PRODUCT AS C on A.product_id= C.product_id " +
            "WHERE deleted LIKE 'N' " + "${subQuery}" + " ORDER BY A.product_id")

    List<ProductListDto> selectProductList(@Param("subQuery")String subQuery);
    
    
    // ** 상품 상세 보기
    //상품 테이블에 대한 정보 가져오기
    @Select("SELECT product_id AS productId, product_name AS productName, product_price AS productPrice, " +
            "product_img_file_name AS productImgFileName, product_img_path AS productImgPath, " +
            "product_description AS productDescription, product_quantity AS productQuantity " +
            "FROM TBL_PRODUCT WHERE product_id=#{productId}")
    ProductDto selectProductDetail(@Param("productId") int productId);
    //? 추가할까 고민인 것 판매자 ID 가져오기?

    // 상품 카테고리 가져오기
    @Select("SELECT A.category_id AS categoryId, A.category_name AS categoryName FROM TBL_CATEGORY AS A " +
            "JOIN TBL_PRODUCT_CATEGORY AS B ON A.category_id=B.category_id " +
            "WHERE B.product_id=#{productId}")
    Category selectCategory(@Param("productId") int productId);


    // 업데이트 기능(카테고리는 변경불가)
    @Update("UPDATE TBL_PRODUCT SET product_name=#{name},product_price=#{price}, product_img_file_name=#{imgFileName}," +
            "product_img_path=#{imgPath}, product_description=#{description}, product_quantity=#{quantity} " +
            "WHERE product_id=#{productId}")
    int updateProduct(@Param("productId") int productId,
                      @Param("name") String productName,
                      @Param("price") int productPrice,
                      @Param("imgFileName") String productImageFileName,
                      @Param("imgPath") String productImagePath,
                      @Param("description") String productDescription,
                      @Param("quantity") int productQuantity);


    //** 삭제기능

    // 상품테이블에서 상품 정보 삭제 : deleted 값만 변경
    @Update("UPDATE TBL_PRODUCT SET deleted='Y' WHERE product_id=#{productId}")
    int deleteProduct(@Param("productId") int productId);

    //상품식별번호로 값 가져오기 - 수정 필요
    @Select("SELECT deleted FROM TBL_PRODUCT WHERE product_id=#{productId}")
    String selectDeletedByProductId(@Param("productId") int productId);


    //** 기존 계획에서 추가된 기능
    //카테고리 목록 가져오기
    @Select("SELECT category_id as categoryId, " +
            "category_name as categoryName " +
            "FROM TBL_CATEGORY ORDER BY category_id")
    List<Category> selectCategoryList();


    // * 장바구니 관련 기능 추후에 분리 가능

    //** 장바구니 추가
    @Insert("INSERT INTO TBL_CART VALUES(null, #{userId}, #{productId}, #{quantity})")
    int insertCart(@Param("userId") int userId, @Param("productId") int productId, @Param("quantity") int quantity);

    //** 장바구니 수정
    @Update("UPDATE TBL_CART SET quantity=#{quantity} " +
            "WHERE product_id=#{productId} " +
            "AND user_id=#{userId}")
    int updateCart(@Param("userId") int userId, @Param("productId") int productId, @Param("quantity") int quantity);

    //** 장바구니 불러오기
    @Select("SELECT A.product_id AS productId,A.quantity AS quantity, " +
            "B.product_name AS productName,B.product_img_file_name AS productImgFileName, " +
            "B.product_img_path AS productImgPath, B.product_price AS productPrice, B.product_quantity AS productQuantity " +
            "FROM TBL_CART AS A " +
            "LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id " +
            "WHERE user_id=#{userId} " +
            "ORDER BY A.cart_id")
    List<CartListDto> selectCart(@Param("userId") int userId);

    //** 장바구니 삭제
    @Delete("DELETE FROM TBL_CART " +
            "WHERE user_id=#{userId} " +
            "AND product_id=#{productId}")
    int deleteCart(@Param("userId") int userId, @Param("productId") int productId);

    //** 동일한 상품에 대한 추가 테이블 생성을 방지하기 위해 기존 DB를 조회
    @Select("SELECT cart_id FROM TBL_CART " +
            "WHERE user_id=#{userId} " +
            "AND product_id=#{productId}")
    Integer selectDuplicateCart(@Param("userId") int userId, @Param("productId") int productId);

    // ** 동일한 상품
    @Update("UPDATE TBL_CART SET quantity=quantity+#{quantity} WHERE cart_id=#{cartId}")
    int updateDuplicateCart(@Param("quantity")int quantity, @Param("cartId") int cartId);
}

