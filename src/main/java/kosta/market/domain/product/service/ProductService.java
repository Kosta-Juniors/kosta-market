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
     * 
     * 1-1). 판매자인 경우 <br>
     * session에서 가져온 seller_id 를 기준으로 모든 product_id 를 조회한다. <br>
     * * <b>접속한 유저의 권한이 판매자</b>인 경우여야함 <br>
     * 
     * 1-2). 구매자인 경우 <br>
     * 등록된 상품의 전체 리스트를 출력해준다. <br>
     *
     * 
     * @Param userType 판매자인지 유저인지 구분하는 변수
     * @Param categoryId 카테고리 번호
     * @Param productName 검색할 상품명
     * 
     * @return 등록한 상품이 있다면 Map<String,Object>;, 아니면 null

     **/
    Map<String, Object> listProduct(String userType, int categoryId,String productName);

    /**
     * 기능 : 상품 상세정보 <br>
     * 설명 : 상품번호를 받아 해당 상품의 모든 정보를 조회한다. <br>
     *
     * @param productId 상품 테이블의 상품번호
     * @return 상품 정보가 있다면 Object, 아니면 null
     */
    Map<String, Object> detailProduct(int productId);

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
    boolean deleteProduct(int productId);

    /**
     * 기능 : 카테고리 리스트 가져오기 <br>
     * 설명 : 상품 등록 시 필요한 카테고리 목록을 미리 받아온다. <br>
     *
     * @return 카테고리 리스트가 존재하면 Map<String,Object>;, 아니면 null
     */
    Map<String, Object> listCategory();

    /**
     * 기능 : 등록상품 카테고리 목록 가져오기 <br>
     * 설명 : 상품 수정 및 상세 보기 시 필요한 카테고리 목록을 받아온다. <br>
     *
     * @return 카테고리 값이 존재하면 Object&gt;, 아니면 null
     * @Param productId 상품번호
     */
    Category detailCategory(int productId);


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
     * 유저 ID, 상품식별번호, 장바구니수량을 입력받아 장바구니 목록에 추가한다. <br>
     *
     * @param cartDto 등록할 장바구니 정보가 담긴 DTO
     * @return 장바구니를 성공적으로 등록했다면 true, 아니면 false
     */
    boolean createCart(CartDto cartDto);

    /**
     * 기능 : 장바구니 수정 <br>
     * 설명 : <br>
     * 유저 ID, 상품식별번호, 수정된 장바구니 수량을 입력받아 장바구니 내용을 수정한다. <br>
     *
     * @param cartDto 등록할 장바구니 정보가 담긴 DTO
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
    boolean deleteCart(int userId, int productId);

    /**
     * 기능 : 장바구니 리스트 <br>
     * 설명 : <br>
     * 유저 ID를 입력받아 장바구니 목록에 저장된 목록을 가지고 온다. <br>
     *
     * @param userId 장바구니 리스트를 불러오기 위한 사용자 식별 번호
     * @return 장바구니 리스트 값이 존재하면 Object&gt;, 아니면 null
     */
    Map<String, Object> listCart(int userId);
}