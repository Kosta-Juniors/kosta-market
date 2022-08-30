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
                      @Param("price") Integer productPrice,
                      @Param("imgFileName") String productImgFileName,
                      @Param("imgPath") String productImgPath,
                      @Param("description") String productDescription,
                      @Param("quantity") Integer productQuantity);

    //SELLER_PRODUCT 테이블에 추가
    ///SELLER_PROUDCT 와 RPRODUCT_CATEGORY에서 PK값 생략될 예정 null 값제거 하시오
    @Insert("INSERT INTO TBL_SELLER_PRODUCT VALUES( #{sellerId}, #{productId})")
    int insertSellerProduct(@Param("sellerId") Integer sellerId, @Param("productId") Integer productId);

    //PRODUCT_CATEGORY 테이블에 추가
    @Insert("INSERT INTO TBL_PRODUCT_CATEGORY VALUES(#{productId}, #{categoryId})")
    int insertProductCategory(@Param("productId") Integer productId, @Param("categoryId") int categoryId);

    // 입력 시 가장 최근에 Auto increment 된 컬럼값 가져오기
    @Select("SELECT LAST_INSERT_ID()")
    int selectLastInsertId();


    // 상품 리스트 불러오기
    @Select("SELECT A.product_id, A.product_name, A.product_price, A.product_description, " +
            "A.product_img_file_name, A.product_img_path, A.product_quantity " +
            "FROM TBL_PRODUCT AS A " +
            "LEFT JOIN TBL_PRODUCT_CATEGORY AS B ON A.product_id=B.product_id " +
            "LEFT JOIN TBL_SELLER_PRODUCT AS C on A.product_id= C.product_id " +
            "WHERE deleted LIKE 'N' " + "${subQuery}" + " ORDER BY A.product_id")
    List<ProductDto> selectProductList(@Param("subQuery") String subQuery);


    // ** 상품 상세 보기
    //상품 테이블에 대한 정보 가져오기
    @Select("SELECT product_id, product_name,product_price, product_img_file_name, " +
            "product_img_path, product_description, product_quantity " +
            "FROM TBL_PRODUCT WHERE product_id=#{productId}")
    ProductDto selectProductDetail(@Param("productId") Integer productId);
    //? 추가할까 고민인 것 판매자 ID 가져오기?

    // 상품 카테고리 가져오기
    @Select("SELECT A.category_id, A.category_name FROM TBL_CATEGORY AS A " +
            "JOIN TBL_PRODUCT_CATEGORY AS B ON A.category_id=B.category_id " +
            "WHERE B.product_id=#{productId}")
    Category selectCategory(@Param("productId") Integer productId);


    // 업데이트 기능(카테고리는 변경불가)
    @Update("UPDATE TBL_PRODUCT SET product_name=#{name},product_price=#{price}, product_img_file_name=#{imgFileName}, " +
            "product_img_path=#{imgPath}, product_description=#{description}, product_quantity=#{quantity} " +
            "WHERE product_id=#{productId}")
    int updateProduct(@Param("productId") Integer productId,
                      @Param("name") String productName,
                      @Param("price") Integer productPrice,
                      @Param("imgFileName") String productImageFileName,
                      @Param("imgPath") String productImagePath,
                      @Param("description") String productDescription,
                      @Param("quantity") Integer productQuantity);


    //** 삭제기능

    // 상품테이블에서 상품 정보 삭제 : deleted 값만 변경
    @Update("UPDATE TBL_PRODUCT SET deleted='Y' WHERE product_id=#{productId}")
    int deleteProduct(@Param("productId") Integer productId);

    //상품식별번호로 값 가져오기 - 수정 필요
    @Select("SELECT deleted FROM TBL_PRODUCT WHERE product_id=#{productId}")
    String selectDeletedByProductId(@Param("productId") Integer productId);


    //** 기존 계획에서 추가된 기능
    //카테고리 목록 가져오기
    @Select("SELECT category_id, category_name " +
            "FROM TBL_CATEGORY ORDER BY category_id")
    List<Category> selectCategoryList();


    // * 장바구니 관련 기능 추후에 분리 가능

    //** 장바구니 추가
    @Insert("INSERT INTO TBL_CART VALUES(null, #{userId}, #{productId}, #{quantity})")
    int insertCart(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("quantity") Integer quantity);

    //** 장바구니 수정
    @Update("UPDATE TBL_CART SET quantity=#{quantity} " +
            "WHERE product_id=#{productId} " +
            "AND user_id=#{userId}")
    int updateCart(@Param("userId") Integer userId, @Param("productId") int productId, @Param("quantity") Integer quantity);

    //** 장바구니 불러오기
    @Select("SELECT A.product_id,A.quantity, B.product_name, B.product_img_file_name, " +
            "B.product_img_path, B.product_price, B.product_quantity " +
            "FROM TBL_CART AS A " +
            "LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id " +
            "WHERE user_id=#{userId} " +
            "ORDER BY A.cart_id")
    List<CartListDto> selectCart(@Param("userId") Integer userId);

    //** 장바구니 삭제
    @Delete("DELETE FROM TBL_CART " +
            "WHERE user_id=#{userId} " +
            "AND product_id=#{productId}")
    int deleteCart(@Param("userId") int userId, @Param("productId") Integer productId);

    //** 동일한 상품에 대한 추가 테이블 생성을 방지하기 위해 기존 DB를 조회
    @Select("SELECT cart_id FROM TBL_CART " +
            "WHERE user_id=#{userId} " +
            "AND product_id=#{productId}")
    Integer selectDuplicateCart(@Param("userId") Integer userId, @Param("productId") Integer productId);

    // ** 동일한 상품
    @Update("UPDATE TBL_CART SET quantity=quantity+#{quantity} WHERE cart_id=#{cartId}")
    int updateDuplicateCart(@Param("quantity") Integer quantity, @Param("cartId") Integer cartId);


    //* 댓글 관련 기능


    //** 댓글 생성
    @Insert("INSERT INTO TBL_COMMENT VALUES(null, #{orderId}, #{productId}, #{score},#{content},'N',now(),null,null)")
    int insertComment(@Param("orderId") Integer orderId, @Param("productId") Integer productId, @Param("score") Integer score,
                      @Param("content") String content);


    //** 댓글 삭제
    @Update("UPDATE TBL_COMMENT AS A " +
            "LEFT JOIN TBL_ORDER AS B ON A.order_id=B.order_id " +
            "LEFT JOIN TBL_USER_ORDER AS C ON B.order_id=C.order_id " +
            "SET A.deleted='Y', A.deleted_at=now() " +
            "WHERE A.comment_id=#{commentId} " +
            "AND C.user_id=#{userId}")
    int deleteComment(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    //** 수정할 댓글 관련 정보 가져오기
    @Select("SELECT * FROM TBL_COMMENT " +
            "WHERE deleted LIKE 'N' " +
            "AND comment_id=#{commentId}")
    CommentDto selectComment(@Param("commentId") Integer commentId);

    // commentId가 유효한지 검사
    @Select("SELECT COUNT(*) FROM TBL_COMMENT WHERE comment_Id=#{commentId}")
    int selectCountById(@Param("commentId")Integer commentId);

    //** 댓글 수정
    @Update("UPDATE TBL_COMMENT AS A " +
            "LEFT JOIN TBL_ORDER AS B ON A.order_id=B.order_id " +
            "LEFT JOIN TBL_USER_ORDER AS C ON B.order_id=C.order_id " +
            "SET A.modified_at=now() " +
            "${subQuery} " +
            "WHERE A.deleted LIKE 'N' " +
            "AND A.order_id=#{orderId} " +
            "AND C.user_id=#{userId}")
    int updateComment(@Param("userId") Integer userId, @Param("orderId") Integer orderId, @Param("subQuery") String subQuery);


    //** 댓글 목록 출력
    @Select("SELECT A.comment_id, A.order_id, A.product_id, D.name,A.score,A.content,A.created_at " +
            "FROM TBL_COMMENT AS A " +
            "LEFT JOIN TBL_ORDER AS B ON A.order_id=B.order_id " +
            "LEFT JOIN TBL_USER_ORDER AS C ON B.order_id=C.order_id " +
            "LEFT JOIN TBL_USER AS D ON C.user_id=D.user_id " +
            "WHERE A.deleted Like 'N' " +
            "AND A.product_id=#{productId} " +
            "${subQuery} " +
            "ORDER BY A.created_at DESC " +
            "LIMIT #{offset},#{size}")
    List<CommentListDto> selectCommentList(@Param("productId") Integer productId, @Param("offset") Integer offset,
                                           @Param("size") Integer size, @Param("subQuery") String subQuery);

    //** 하나의 주문에 하나의 상품만 담길 수 있도록 확인하는 것
    @Select("SELECT COUNT(*) FROM TBL_COMMENT " +
            "WHERE order_id=#{orderId}")
    int selectDuplicateComment(@Param("orderId") Integer orderId);


    //** 상품 댓글 관련 개수
    @Select("SELECT COUNT(*) FROM TBL_COMMENT WHERE deleted LIKE 'N' AND product_id=#{productId}")
    Integer selectCommentCountByproductId(@Param("productId") Integer productId);

    //** 관렴 상품 5가지 랜덤하게 출력
    @Select("SELECT A.product_id, A.product_name, A.product_price, A.product_Description, " +
            "A.product_img_file_name, A.product_img_path " +
            "FROM TBL_PRODUCT AS A " +
            "LEFT JOIN TBL_PRODUCT_CATEGORY AS B ON A.product_id=B.product_id " +
            "WHERE B.category_id=#{categoryId} " +
            "ORDER BY rand() " +
            "LIMIT 5")
    List<ProductDto> selectProductByCategoryId(@Param("categoryId") Integer categoryId);

    // 최고의 평점 3개 상품만 가져오기
    @Select("SELECT A.score,B.* " +
            " FROM(SELECT product_id, AVG(score) AS score " +
            " FROM TBL_COMMENT WHERE deleted LIKE 'N' GROUP BY product_id) AS A " +
            " LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id " +
            " WHERE B.deleted LIKE 'N' " +
            " ORDER BY A.score DESC, B.product_id ASC " +
            " LIMIT 3")
     List<ProductTopRatedDto> selectProductByTopScore();

    // 상품 개수 반환
    @Select("SELECT COUNT(*) FROM TBL_PRODUCT AS A " +
            "LEFT JOIN TBL_PRODUCT_CATEGORY AS B ON A.product_id=B.product_id " +
            "WHERE A.deleted LIKE 'N' " +
            "${subQuery} ")
    Integer selectProductCount(@Param("subQuery") String subQuery);

}

