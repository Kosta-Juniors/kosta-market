package kosta.market.domain.product.service;

import kosta.market.domain.product.model.*;
import kosta.market.domain.product.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 기능 : 상품 등록 <br>
     * 설명 : <br>
     * 상품명, 상품가격, 이미지파일명, 이미지파일경로, 상품상세설명, 재고수량, <br>
     * 카테고리가 담긴 <b>상품등록요청 객체</b>를 받아 상품을 등록한다.<br>
     * 단, 상품 등록 날짜는 자동으로 추가되야 한다.
     *
     * @param productCreateDto 등록할 상품 정보가 담긴 DTO
     * @param imgFile          상품이미지파일
     * @return 상품을 성공적으로 등록했다면 true, 아니면 false
     */
    boolean createProduct(ProductCreateDto productCreateDto, MultipartFile imgFile);

    /**
     * 기능 : 상품 리스트 <br>
     * 설명 : <br>
     * 1. usertype을 기준으로 판매자인지 구매자인지를 구분한다. <br>
     * 2. categoryId를 기준으로 카테고리별 상품리스트를 출력한다. <br>
     * 3. 상품명을 기준으로 상품명을 포함한 상품리스트를 출력한다. <br>
     * 4. 등록한 상품식별 번호, 상품명, 가격, 재고수량, 이미지파일이름,이미지파일경로를 DB에서 가져온다. <br>
     * <p>
     * 1-1). 판매자인 경우 <br>
     * session에서 가져온 seller_id 를 기준으로 모든 product_id 를 조회한다. <br>
     * * <b>접속한 유저의 권한이 판매자</b>인 경우여야함 <br>
     * <p>
     * 1-2). 구매자인 경우 <br>
     * 등록된 상품의 전체 리스트를 출력해준다. <br>
     *
     * @Param userType 판매자인지 유저인지 구분하는 변수
     * @Param categoryId 카테고리 번호
     * @Param productName 검색할 상품명
     * @return 등록한 상품이 있다면 List;, 아니면 null

     **/
    List<ProductDto> listProduct(String userType, Integer categoryId, String productName);

    /**
     * 기능 : 상품 상세정보 <br>
     * 설명 : 상품번호를 받아 해당 상품의 모든 정보를 조회한다. <br>
     *
     * @param productId 상품 테이블의 상품번호
     * @return 상품 정보가 있다면 ProductDto, 아니면 null
     */
    ProductDto detailProduct(Integer productId);

    /**
     * 기능 : 상품 수정 <br>
     * 설명 : 상품번호, 상품명, 가격, 이미지파일명, 이미지파일경로, 설명, 재고수량, <br>
     * 카테고리가 담긴 <b>상품수정요청 객체</b>를 받아 상품을 수정한다.<br>
     *
     * @param productUpdateDto 변경할 상품 정보가 담겨있는 DTO
     * @param imgFile          변경할 상품 이미지
     * @return 상품이 성공적으로 변경되면 true, 아니면 false
     */
    boolean updateProduct(ProductDto productUpdateDto, MultipartFile imgFile);

    /**
     * 기능 : 상품 삭제 <br>
     * 설명 : 상품번호를 받아 해당 상품을 삭제한다. <br>
     *
     * @param productId 상품번호
     * @return 상품이 삭제되었다면 true, 아니면 false
     */
    boolean deleteProduct(Integer productId);

    /**
     * 기능 : 카테고리 리스트 가져오기 <br>
     * 설명 : 상품 등록 시 필요한 카테고리 목록을 미리 받아온다. <br>
     *
     * @return 카테고리 리스트가 존재하면 List;, 아니면 null
     */
   List<Category> listCategory();

    /**
     * 기능 : 등록상품 카테고리 목록 가져오기 <br>
     * 설명 : 상품 수정 및 상세 보기 시 필요한 카테고리 목록을 받아온다. <br>
     *
     * @return 카테고리 값이 존재하면 Object&gt;, 아니면 null
     * @Param productId 상품번호
     */
    Category detailCategory(Integer productId);


    /**
     * 기능 : 파일 이미지 저장 <br>
     * 설명 : <br>
     * 1. 각 상품별 이미지 파일을 저장한다.<br>
     *
     * @return UUID가 적용된 이미지파일명 반환
     * @Param productImgFile 상품관련이미지파일
     **/
    String saveImg(MultipartFile productImgFile);

    /**
     * 기능 : 파일 이미지 호출 <br>
     * 설명 : <br>
     * 1. 각 상품별 저정된 이미지 파일을 호출한다.<br>
     *
     * @return 상품이미지 검색 성공 시 이미지 파일 반환
     * @Param productImgName 이미지 파일 아룸
     */
    ResponseEntity<byte[]> imgProduct(String productImgName);

    /**
     * 기능 : 파일 이미지 삭제 <br>
     * 설명 : <br>
     * 1. 해당하는 상품에 적용된 이미지 파일을 삭제한다.<br>
     *
     * @Param productImgName 삭제할 이미지 파일 이름
     */
    void deleteImg(String productImgName);


    // 장바구니 관련 기능

    /**
     * 기능 : 장바구니 추가 <br>
     * 설명 : <br>
     * 사용자식별번호(userId), 상품식별번호, 장바구니수량을 입력받아 장바구니 목록에 추가한다. <br>
     *
     * @param cartDto 등록할 장바구니 정보가 담긴 DTO
     * @return 장바구니를 성공적으로 등록했다면 true, 아니면 false
     */
    boolean createCart(CartDto cartDto);

    /**
     * 기능 : 장바구니 수정 <br>
     * 설명 : <br>
     * 사용자식별번호(userId), 상품식별번호, 수정된 장바구니 수량을 입력받아 장바구니 내용을 수정한다. <br>
     *
     * @param cartDto 수정할 장바구니 정보가 담긴 DTO
     * @return 장바구니를 성공적으로 수정했다면 true, 아니면 false
     */
    boolean updateCart(CartDto cartDto);

    /**
     * 기능 : 장바구니 삭제 <br>
     * 설명 : <br>
     * 사용자식별번호(userId), 상품식별번호를 입력받아 장바구니 목록을 삭제한다. <br>
     *
     * @param userId    사용자 식별 번호
     * @param productId 상품식별번호
     * @return 장바구니를 성공적으로 삭제했다면 true, 아니면 false
     */
    boolean deleteCart(Integer userId, Integer productId);

    /**
     * 기능 : 장바구니 리스트 출력 <br>
     * 설명 : <br>
     * 사용자식별번호(userId)를 입력받아 사용자의 장바구니 목록을 가지고 온다. <br>
     *
     * @param userId 장바구니 리스트를 불러오기 위한 사용자 식별 번호
     * @return 장바구니 리스트 값이 존재하면 List&gt;, 아니면 null
     */
    List<CartListDto> listCart(Integer userId);


    // 댓글 관련 기능

    /**
     * 기능 : 댓글 작성 <br>
     * 설명 : <br>
     * 주문식별번호, 상품식별번호, 평점, 댓글내용을 받아와 댓글을 작성한다. <br>
     *
     * @param commentDto 댓글 관련 정보가 담긴 DTO
     * @return 댓글을 성공적으로 등록했다면 true, 아니면 false
     */
    boolean createComment(CommentDto commentDto);

    /**
     * 기능 : 댓글 수정에 필요한 정보 가져오기 <br>
     * 설명 : <br>
     * 주문식별번호(orderId)를 입력받아 댓글 정보를 반환한다. <br>
     *
     * @return 댓글 내용이 존재하면 Object&gt;, 아니면 null
     * @Param commentId 댓글 작성자 본인인지 확인하기 위해 필요한 데이터
     */
    CommentDto detailComment(Integer commentId);

    /**
     * 기능 : 댓글 수정 <br>
     * 설명 : <br>
     * 사용자식별번호(userId), 댓글 식별 번호 (commentId), 주문식별번호(orderId), 상품식별번호, 수정된 평점, 수정된 댓글냇용을 입력받아 댓글을 수정한다. <br>
     *
     * @param commentDto 수정할 댓글 관련 정보가 담긴 DTO
     * @return 댓글을 성공적으로 수정했다면 true, 아니면 false
     * @Param userId 댓글 작성자 본인인지 확인하기 위해 필요한 데이터
     * @Param commentId 댓글 식별 번호
     */
    boolean updateComment(Integer userId, Integer commentId, CommentDto commentDto);


    /**
     * 기능 : 댓글 삭제 <br>
     * 설명 : <br>
     * 사용자식별번호(userId), 주문식별번호를 입력받아 댓글을 삭제한다. <br>
     *
     * @param userId    사용자 식별 번호
     * @param commentId 댓글 식별 번호
     * @return 댓글을 성공적으로 삭제했다면 true, 아니면 false
     */
    boolean deleteComment(Integer userId, Integer commentId);

    /**
     * 기능 : 상품 댓글 리스트 <br>
     * 설명 : <br>
     * 상품식별번호를 입력받아 해당 상품과 관련된 댓글 목록을 가지고 온다. <br>
     *
     * @param productId 장바구니 리스트를 불러오기 위한 상품 식별 번호
     * @param page      장바구니 리스트 페이지
     * @param size      장바구니 리스트에서 한 번에 볼 댓글의 개수
     * @param userId    장바구니 리스트를 불러오기 위한 사용자 식별 번호
     * @return 상품 관련 댓글 목록이 존재하면 Object&gt;, 아니면 null
     */
    List<CommentListDto> listComment(Integer productId, Integer page, Integer size, Integer userId);

    /**
     * 기능 : 상품별 댓글 개수 <br>
     * 설명 : <br>
     * 상품식별번호(productId)를 입력받아 상품별 댓글 갯수를 반환한다. <br>
     *
     * @param productId 상품식별번호
     * @return 상품 관련 댓글이 존재하면 댓글 개수를 반환(Integer);, 아니면 null
     */
    Integer countProductComment(Integer productId);

    /**
     * 기능 : 카테고리별 추천 상품 <br>
     * 설명 : <br>
     * 카테고리식별번호(categoryId)를 입력받아 카테고리 관련 추천 상품 5개 정보를 반환한다. <br>
     *
     * @param categoryId 상품식별번호
     * @return 해당 카테고리에 관련 있는 상품이 존재하면 상품 리스트가 담긴 List&gt;, 아니면 null
     */
    List<ProductDto> categoryProductList(Integer categoryId);

    /**
     * 기능 : 평균평전별 추천 상품 <br>
     * 설명 : <br>
     * 평균펼점이 가장 높은 상품 3개 정보를 반환한다. <br>
     * *
     *
     * @return 평균 평점이 가장 높은 상품 3개가 존재하면 상품 리스트가 담긴 List&gt;, 아니면 null
     */
    List<ProductTopRatedDto> topRatedProductList();

    /**
     * 기능 : 상품 개수 조회 <br>
     * 설명 : <br>
     * 상품명(productName), 카테고리식별번호(categoryId)를 입력받아 관련 상품 개수를 반환한다. <br>
     *
     * @param productName 검색할 상품명
     * @param categoryId 상품식별번호
     * @return 해당 상품명 혹은 카테고리에 관련 있는 상품이 존재하면 상품 개수가 담긴 Object&gt;, 아니면 null
     */
    Integer countProduct(String productName, Integer categoryId);


    /**
     * 기능 : 에러 메세지 <br>
     * 설명 : <br>
     * 에러 메세지를 반환한다. <br>
     *
     * @param error 에러메시지 종류
     * @return 에러 발생 시 String;, 아니면 null
     */
    String errorMessage(String error);


}