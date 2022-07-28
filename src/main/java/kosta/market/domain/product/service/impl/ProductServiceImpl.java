package kosta.market.domain.product.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import kosta.market.domain.product.model.Product;
import kosta.market.domain.product.model.ProductMapper;
import kosta.market.domain.product.service.ProductService;
import kosta.market.domain.user.model.Seller;
import kosta.market.global.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 기능 : 상품 등록 <br> 설명 : <br> 상품명, 상품가격, 이미지파일명, 이미지파일경로, 상품상세설명, 재고수량, <br> 카테고리가 담긴 <b>상품등록요청 객체</b>를 받아 상품을
	 * 등록한다.<br> 단, 상품 등록 날짜는 자동으로 추가되야 한다.
	 *
	 * @return 상품을 성공적으로 등록했다면 true, 아니면 false
	 */
	@Override
	@Transactional
	public boolean addProduct(Object categoryId, Object productName, Object productPrice, Object productQuantity, Object productImgFileName, Object productImgPath, Object productDescription) {

		if (SecurityUtil.hasAuthorization()) {
		  productMapper.insertProduct(
			  productName,
			  productPrice,
			  productQuantity,
			  productImgFileName,
			  productImgPath,
			  productDescription);

		  Object productId = productMapper.lastInsertId();
		  productMapper.insertSellerProduct(SecurityUtil.getAuthorization(), productId);
		  productMapper.insertProductCategory(productId, categoryId);
		  return true;
		}

		return false;
	}

	/**
	 * 기능 : 등록한 상품 리스트 <br> 설명 : <br> 1. Session 의 user_id 를 기준으로 seller_id 를 조회한다. <br> 2. 가져온 seller_id 를 기준으로 모든
	 * product_id 를 조회한다. <br> 단, <b>접속한 유저의 권한이 판매자</b>인 경우여야함
	 *
	 * @return 등록한 상품이 있다면 ArrayList&lt;Object&gt;, 아니면 null
	 */
	@Override
	public List<Object> registListProduct() {
		return Collections.singletonList(productMapper.selectSellerProductList(SecurityUtil.getAuthorization()));
	}

	/**
	 * 기능 : 등록한 상품 리스트 <br> 설명 : <br> 1. Session 의 user_id 를 기준으로 seller_id 를 조회한다. <br> 2. 가져온 seller_id 를 기준으로 모든
	 * product_id 를 조회한다. <br> 단, <b>접속한 유저의 권한이 판매자</b>인 경우여야함
	 *
	 * @return 등록한 상품이 있다면 ArrayList&lt;Object&gt;, 아니면 null
	 */
	@Override
	public List<Object> listProduct() {
		return Collections.singletonList(productMapper.selectProductList());
	}

	/**
	 * 기능 : 상품 상세정보 <br> 설명 : 상품번호를 받아 해당 상품의 모든 정보를 조회한다. <br>
	 *
	 * @return 상품 정보가 있다면 Object, 아니면 null
	 */
	@Override
	public Object detailProduct(Object productId) {
		return productMapper.selectProduct(productId);
	}

	/**
	 * 기능 : 상품 수정 <br> 설명 : 상품번호, 상품명, 가격, 이미지파일명, 이미지파일경로, 설명, 재고수량, <br> 카테고리가 담긴 <b>상품수정요청 객체</b>를 받아 상품을 수정한다.<br>
	 *
	 * @param productModifyDto 변경할 상품 정보가 담겨있는 DTO
	 * @return 상품이 성공적으로 변경되면 true, 아니면 false
	 */
	@Override
	public boolean modifyProduct(Object productModifyDto) {
		return false;
	}

	/**
	 * 기능 : 상품 삭제 <br> 설명 : 상품번호를 받아 해당 상품을 삭제한다. <br>
	 *
	 * @return 상품이 삭제되었다면 true, 아니면 false
	 */
	@Override
	@Transactional
	public boolean removeProduct(Object productId) {
		if(SecurityUtil.hasAuthorization()){
			Seller owner = productMapper.selectProductOwner(productId);
			if (!Objects.equals(owner, null) &&
				owner.getSellerId() == (Integer) SecurityUtil.getAuthorization()) {
				productMapper.deleteProduct(productId);
				productMapper.deleteProduct(productId);
				return true;
			}
		}
		return false;
	}

	public Object ownerProduct(Object productId){
		return productMapper.selectProductOwner(productId);
	}
}
