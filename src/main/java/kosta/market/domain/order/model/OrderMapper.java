package kosta.market.domain.order.model;

import java.util.ArrayList;
import java.util.List;

import kosta.market.domain.product.model.Product;
import kosta.market.domain.user.model.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

	//	orderproduct.jsp 페이지에서 주소정보 출력
	@Select("SELECT * FROM TBL_ADDRESS WHERE user_id = #{userId}")
	List<Address> selectAddressByUserId(@Param("userId") int userId);

	@Insert("INSERT INTO TBL_ORDER VALUES (null, #{productId}, #{orderQuantity}, now(), 0)")
	int insertOrder(@Param("productId") int productId,
					@Param("orderQuantity") int orderQuantity);
	// int orderState 를 0(주문완료)로 입력

	// 가장 최근에 생성된 PK를  foreign 키로 받아오기
	@Select("SELECT LAST_INSERT_ID()")
	int lastInsertId();

	@Select("SELECT * FROM TBL_PRODUCT WHERE product_id=#{productId}")
	Product selectProduct(@Param("productId")int productId);

	@Insert("INSERT INTO TBL_PAYMENT VALUES (null, #{paymentPrice}, #{paymentMethod}, now())")
	int insertPayment(@Param("paymentPrice") int paymentPrice,
					  @Param("paymentMethod") String paymentMethod);

	@Insert("INSERT INTO TBL_ORDER_PAYMENT VALUES (null, #{orderId}, #{paymentId})")
	int insertOrderPayment(@Param("orderId") int orderId,
						   @Param("paymentId") int paymentId);

	@Insert("INSERT INTO TBL_USER_ORDER VALUES (null, #{userId}, #{orderId}, #{addressId})")
	int insertUserOrder(@Param("userId") int userId,
						@Param("orderId") int orderId,
						@Param("addressId") int addressId);

	@Update("UPDATE TBL_PRODUCT SET product_quantity=product_quantity-#{orderQuantity} WHERE product_id=#{productId}")
	int updateProduct(@Param("productId") int productId,
					  @Param("orderQuantity") int orderQuantity);

	// (구매자)주문리스트
	@Select("SELECT A.order_id, B.product_name, A.order_quantity, D.payment_price, A.order_state" +
			" FROM TBL_ORDER AS A" +
			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_USER_ORDER AS E ON A.order_id=E.order_id" +
			" WHERE E.user_id = #{userId}")
	ArrayList<OrderListDto> selectOrderListByUserId(@Param("userId") int userId);

	// (판매자)주문리스트
	@Select("SELECT A.order_id, B.product_name, A.order_quantity, D.payment_price, A.order_state" +
			" FROM TBL_ORDER AS A" +
			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_SELLER_PRODUCT AS H ON B.product_id=H.product_id" +
			" WHERE H.seller_id = #{sellerId}")
	List<OrderListDto> selectOrderListBySellerId(@Param("sellerId") int sellerId);

	@Select("SELECT A.*, B.product_name, D.payment_price, D.payment_method, F.delivery_place, G.name, G.contact" +
			" FROM TBL_ORDER as A" +
			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_USER_ORDER AS E ON A.order_id=E.order_id" +
			" LEFT JOIN TBL_ADDRESS AS F ON E.address_id=F.address_id" +
			" LEFT JOIN TBL_USER AS G ON E.user_id=G.user_id" +
			" WHERE A.order_id=#{orderId}")
	OrderDetailDto selectOrderDetail(@Param("orderId") int orderId);


	// 주문상태수정 - 교환신청
	@Update("UPDATE TBL_ORDER SET order_state = 3 WHERE order_id = #{orderId}")
	int updateOrderStateExchange(@Param("orderId") int orderId);

	// 주문상태수정 - 구매취소
	@Update("UPDATE TBL_ORDER SET order_state = 4 WHERE order_id = #{orderId}")
	int updateOrderStateCancel(@Param("orderId") int orderId);

	@Select("SELECT * FROM TBL_ORDER WHERE order_id = #{orderId}")
	Order selectOrder(@Param("orderId") int orderId);

	@Update("update TBL_PRODUCT set product_quantity = product_quantity + #{orderQuantity} where product_id = #{productId}")
	int updateProductQuantity(@Param("productId") int productId, @Param("orderQuantity") int orderQuantity);



	// 주문상태수정 - 구매확정
	@Update("UPDATE TBL_ORDER SET order_state = 5 WHERE order_id = #{orderId}")
	int updateOrderStateConfirm(@Param("orderId") int orderId);






}












