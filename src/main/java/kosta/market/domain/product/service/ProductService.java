package kosta.market.domain.product.service;

import kosta.market.domain.product.model.*;
import kosta.market.domain.product.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

	/**
	 * 기능 : 상품 등록 <br>
	 * 설명 : <br>
	 * 상품명, 상품가격, 이미지파일명, 이미지파일경로, 상품상세설명, 재고수량, <br>
	 * 카테고리가 담긴 <b>상품등록요청 객체</b>를 받아 상품을 등록한다.<br>
	 * 단, 상품 등록 날짜는 자동으로 추가되야 한다.
	 *
	 * @param productCreateDto 등록할 상품 정보가 담긴 DTO
	 * @return 상품을 성공적으로 등록했다면 true, 아니면 false
	 */
	boolean createProduct(ProductCreateDto productCreateDto);

	/**
	 * 기능 : 상품 리스트 <br>
	 * 설명 : <br>
	 * 1. usertype을 기준으로 판매자인지 구맨자인지를 구분한다. <br>
	 * 2. 판매자인 경우
	 * session에서 가져온 seller_id 를 기준으로 모든 product_id 를 조회한다. <br>
	 * 등록한 상품식별 번호, 상품명, 가격, 재고수량 순으로 물품리스트를 출력한다. <br>
	 * <b>접속한 유저의 권한이 판매자</b>인 경우여야함 <br>
	 * 3. 구매자인 경우
	 * 등록된 상품의 전체 리스트를 출력해준다. <br>
	 * 
	 * @Param usertype 판매자인지 유저인지 구분하는 변수
	 * @return 등록한 상품이 있다면 ArrayList&lt;Object&gt;, 아니면 null
	 */
	List<ProductListDto> ListProduct(String usertype);


	/**
	 * 기능 : 상품 상세정보 <br>
	 * 설명 : 상품번호를 받아 해당 상품의 모든 정보를 조회한다. <br>
	 *
	 * @param product_id 상품 테이블의 상품번호
	 * @return 상품 정보가 있다면 Object, 아니면 null
	 */
	ProductDto detailProduct(int product_id);

	/**
	 * 기능 : 상품 수정 <br>
	 * 설명 : 상품번호, 상품명, 가격, 이미지파일명, 이미지파일경로, 설명, 재고수량, <br>
	 * 카테고리가 담긴 <b>상품수정요청 객체</b>를 받아 상품을 수정한다.<br>
	 *
	 * @param productDto 변경할 상품 정보가 담겨있는 DTO
	 * @return 상품이 성공적으로 변경되면 true, 아니면 false
	 */
	boolean updateProduct(ProductDto productDto);

	/**
	 * 기능 : 상품 삭제 <br>
	 * 설명 : 상품번호를 받아 해당 상품을 삭제한다. <br>
	 *
	 * @param product_id 상품번호
	 * @return 상품이 삭제되었다면 true, 아니면 false
	 */
	boolean deleteProduct(int product_id);

	/**
	 * 기능 : 카테고리 리스트 가져오기 <br>
	 * 설명 : 상품 등록 시 필요한 카테고리 목록을 미리 받아온다. <br>
	 *
	 * @return 카테고리 리스트가 존재하면 ArrayList&lt;Object&gt;, 아니면 null
	 */
	List<Category> listCategory();

	/**
	 * 기능 : 등록상품 카테고리 목록 가져오기 <br>
	 * 설명 : 상품 수정 및 상세 보기 시 필요한 카테고리 목록을 받아온다. <br>
	 * @Param productId 상품번호
	 * @return 카테고리 값이 존재하면 Object&gt;, 아니면 null
	 */
	Category detailCategory(int productId);

	/**
	 * 기능 : 검색 상품 리스트 <br>
	 * 설명 : <br>
	 * 1. 비로그인/구매자 회원에게 보여주는 리스트다.<br>
	 * 2. 상품명으로 검색된 모든 상품을 출력한다.<br>
	 *
	 * @Param product_name 상품명
	 * @return 등록된 상품이 있다면 ArrayList&lt;Object&gt;, 아니면 null
	 */
	List<ProductListDto> listProductName(String product_name);

	/**
	 * 기능 : 파일 이미지 저장 <br>
	 * 설명 : <br>
	 * 1. 각 상품별 이미지 파일을 저장한다.<br>
	 *
	 *@Param productAddDto 이미지 파일이 저장된 Dto
	 */
	public void saveImg(ProductCreateDto productCreateDto);
	/**
	 * 기능 : 파일 이미지 호출 <br>
	 * 설명 : <br>
	 * 1. 각 상품별 저정된 이미지 파일을 호출한다.<br>
	 *
	 *@Param product_img_name 이미지 파일 아룸
	 * @return 상품이미지 검색 성공 시 이미지 파일 반환
	 */
	public ResponseEntity<byte[]> imgProduct(String product_img_name);


	/**
	 * 기능 : 파일 이미지 업데이트 <br>
	 * 설명 : <br>
	 * 1. 각 상품별 이미지 파일을 저장한다.<br>
	 *
	 *@Param productDto 이미지 파일이 저장된 Dto
	 */
	public void updateImg(ProductDto productDto);
}