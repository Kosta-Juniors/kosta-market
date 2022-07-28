package kosta.market.domain.order.model;

import java.util.List;
import kosta.market.domain.order.model.dto.SellerOrderDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
	@Select("SELECT LAST_INSERT_ID()")
	int lastInsertId();

	@Insert("INSERT INTO TBL_ORDER VALUES (null, #{productId}, #{orderQuantity}, now(), #{orderState})")
	int insertOrder(Object productId, Object orderQuantity, Object orderState);

	@Insert("INSERT INTO TBL_PAYMENT VALUES (null, #{paymentPrice}, #{paymentMethod}, now())")
	int insertPayment(@Param("paymentPrice") Object paymentPrice, @Param("paymentMethod") String paymentMethod);

	@Insert("INSERT INTO TBL_ORDER_PAYMENT VALUES (null, #{orderId}, #{paymentId})")
	int insertOrderPayment(@Param("orderId") Object orderId, @Param("paymentId") Object paymentId);

	@Insert("INSERT INTO TBL_USER_ORDER VALUES (null, #{userId}, #{addressId}, #{orderId})")
	int insertUserOrder(@Param("userId") Object userId, @Param("addressId") Object addressId, @Param("orderId") Object orderId);

	@Select("SELECT * FROM TBL_ORDER AS A JOIN"
		+ " TBL_USER_ORDER AS B ON A.order_id = B.order_id"
		+ " WHERE B.user_id = #{userId}")
	List<Order> selectOrderListByUserId(@Param("userId") Object userId);

	@Select("SELECT ORD.order_id, PRD.product_name, PAY.payment_price, ORD.order_quantity, ORD.order_date, ADR.delivery_place, ORD.order_state, USR.user_id, USR.username"
		+ " FROM TBL_ORDER AS ORD"
		+ " JOIN TBL_PRODUCT AS PRD ON ORD.product_id = PRD.product_id"
		+ " JOIN TBL_SELLER_PRODUCT AS SPD ON PRD.product_id = SPD.product_id"
		+ " JOIN TBL_ORDER_PAYMENT AS OPY ON OPY.order_id = ORD.order_id"
		+ " JOIN TBL_PAYMENT AS PAY ON PAY.payment_id = OPY.payment_id"
		+ " JOIN TBL_USER_ORDER AS UOD ON UOD.order_id = ORD.order_id"
		+ " JOIN TBL_USER AS USR ON USR.user_id = UOD.user_id"
		+ " JOIN TBL_ADDRESS AS ADR ON ADR.user_id = USR.user_id"
		+ " WHERE SPD.seller_id = #{sellerId}")
	List<SellerOrderDto> selectOrderListBySellerId(@Param("sellerId") Object sellerId);

	@Select("SELECT * FROM TBL_ORDER WHERE order_id = #{orderId}")
	Order selectOrder(@Param("orderId") Object orderId);

	@Select("SELECT * FROM TBL_PAYMENT WHERE payment_id = #{paymentId}")
	Payment selectPayment(@Param("paymentId") Object paymentId);

	@Update("UPDATE TBL_ORDER SET order_state = #{orderState}")
	int updateOrder(@Param("orderState") Object orderState);
}












