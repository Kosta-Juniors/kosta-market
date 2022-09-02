package kosta.market.domain.order.model;

import java.util.ArrayList;
import java.util.List;

import kosta.market.domain.product.model.Product;
import kosta.market.domain.user.model.AddressDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

	//	orderproduct.jsp 페이지에서 주소정보 출력
	@Select("SELECT * FROM TBL_ADDRESS WHERE user_id = #{userId}")
	List<AddressDto> selectAddressByUserId(@Param("userId") int userId);


	@Insert("INSERT INTO TBL_ORDER VALUES (null, now())")
	int insertOrder();
	// int orderState 를 0(주문완료)로 입력

	@Insert("INSERT INTO TBL_ORDER_DETAIL VALUES (#{orderId}, #{productId}, #{orderQuantity}, '주문완료', #{orderPrice})")
	int insertOrderDetail(@Param("orderId")int orderId, @Param("productId")int productId, @Param("orderQuantity")int orderQuantity, @Param("orderPrice")int orderPrice);

	// 가장 최근에 생성된 PK를  foreign 키로 받아오기
	@Select("SELECT LAST_INSERT_ID()")
	int lastInsertId();

	@Select("SELECT * FROM TBL_PRODUCT WHERE product_id=#{productId}")
	Product selectProduct(@Param("productId")int productId);

	@Insert("INSERT INTO TBL_PAYMENT VALUES (null, #{paymentPrice}, #{paymentMethod}, now())")
	int insertPayment(@Param("paymentPrice") int paymentPrice,
					  @Param("paymentMethod") String paymentMethod);

	@Insert("INSERT INTO TBL_ORDER_PAYMENT VALUES (#{orderId}, #{paymentId})")
	int insertOrderPayment(@Param("orderId") int orderId,
						   @Param("paymentId") int paymentId);

	@Insert("INSERT INTO TBL_USER_ORDER VALUES (#{userId}, #{orderId}, #{addressId})")
	int insertUserOrder(@Param("userId") int userId,
						@Param("orderId") int orderId,
						@Param("addressId") int addressId);

	@Update("UPDATE TBL_PRODUCT SET product_quantity=product_quantity-#{orderQuantity} WHERE product_id=#{productId}")
	int updateProduct(@Param("productId") int productId,
					  @Param("orderQuantity") int orderQuantity);

	// (구매자)주문리스트
//	@Select("SELECT A.order_id, B.product_name, A.order_quantity, D.payment_price, A.order_state" +
//			" FROM TBL_ORDER AS A" +
//			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
//			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
//			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
//			" LEFT JOIN TBL_USER_ORDER AS E ON A.order_id=E.order_id" +
//			" WHERE E.user_id = #{userId}")
//	ArrayList<OrderListDto> selectOrderListByUserId(@Param("userId") int userId);

	// (판매자)주문리스트
//	@Select("SELECT A.order_id, B.product_name, A.order_quantity, D.payment_price, A.order_state" +
//			" FROM TBL_ORDER AS A" +
//			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
//			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
//			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
//			" LEFT JOIN TBL_SELLER_PRODUCT AS H ON B.product_id=H.product_id" +
//			" WHERE H.seller_id = #{sellerId}")
//	List<OrderListDto> selectOrderListBySellerId(@Param("sellerId") int sellerId);

	// (구매자) userId에 해당하는 LIST<orderId> 가져오기
	@Select("SELECT E.order_id" +
			" FROM TBL_USER AS G" +
			" LEFT JOIN TBL_USER_ORDER AS E ON G.user_id=E.user_id" +
			" WHERE G.user_id = #{userId}")
	List<Integer> getOrderIdListByUserId(@Param("userId") int userId);

	// (판매자) sellerId에 해당하는 LIST<productId> 가져오기
	@Select("SELECT product_id" +
			" From TBL_SELLER_PRODUCT" +
			" WHERE seller_id=#{sellerId}")
	List<Integer> getProductIdListBySellerId(@Param("sellerId") int sellerId);




	@Select("SELECT A.order_id, A.order_date, D.payment_price, D.payment_method, F.delivery_place, F.contact, F.recipient" +
			" FROM TBL_ORDER AS A" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_USER_ORDER AS E ON A.order_id=E.order_id" +
			" LEFT JOIN TBL_ADDRESS AS F ON E.address_id=F.address_id" +
			" WHERE A.order_id = #{orderId}")
	OrderDto selectOrder(@Param("orderId") int orderId);


	@Select("SELECT K.product_id, K.order_quantity, K.order_state, K.order_price, B.product_name" +
			" FROM TBL_ORDER_DETAIL AS K" +
			" LEFT JOIN TBL_PRODUCT AS B ON B.product_id = K.product_id" +
			" LEFT JOIN TBL_ORDER AS A ON A.order_id = K.order_id" +
			" WHERE A.order_id = #{orderId}")
	ArrayList<OrderDetailDto> selectOrderDetailByOrderId(@Param("orderId") int orderId);

	@Select("SELECT K.*, A.order_date, B.product_name" +
			" FROM TBL_ORDER_DETAIL AS K" +
			" LEFT JOIN TBL_ORDER AS A ON A.order_id=K.order_id" +
			" LEFT JOIN TBL_PRODUCT AS B ON B.product_id=K.product_id" +
			" WHERE K.product_id=#{productId}")
	ArrayList<OrderDetailForSellerDto> selectOrderDetailForSellerByProductId(@Param("productId") int productId);


	// TBL_ORDER_DETAIL에서 orderState가져오기
	@Select("SELECT order_state" +
			" FROM TBL_ORDER_DETAIL" +
			" WHERE order_id=#{orderId} AND product_id=#{productId}")
	String  getOrderState(@Param("orderId") int orderId, @Param("productId") int productId);

	// 주문상태수정 - 교환신청
	@Update("UPDATE TBL_ORDER_DETAIL SET order_state = '교환신청'" +
			" WHERE order_id = #{orderId} AND product_id = #{productId}")
	int updateOrderStateExchange(@Param("orderId") int orderId,
								 @Param("productId") int productId);

	// 주문상태수정 - 취소/환불
	@Update("UPDATE TBL_ORDER_DETAIL SET order_state = '취소환불'" +
			" WHERE order_id = #{orderId} AND product_id=#{productId}")
	int updateOrderStateCancel(@Param("orderId") int orderId,
							   @Param("productId") int productId);

	@Select("SELECT product_id, order_quantity, order_price FROM TBL_ORDER_DETAIL" +
			" WHERE order_id = #{orderId} AND product_id = #{productId}")
	OrderRequestDto selectOrderRequestDto(@Param("orderId") int orderId, @Param("productId") int productId);

	@Update("UPDATE TBL_PRODUCT SET product_quantity = product_quantity + #{orderQuantity}" +
			" WHERE product_id = #{productId}")
	int updateProductQuantity(@Param("productId") int productId, @Param("orderQuantity") int orderQuantity);



	// 주문상태수정 - 구매확정
	@Update("UPDATE TBL_ORDER_DETAIL SET order_state = '구매확정'" +
			" WHERE order_id = #{orderId} AND product_id=#{productId}")
	int updateOrderStateConfirm(@Param("orderId") int orderId,
								@Param("productId") int productId);






}












