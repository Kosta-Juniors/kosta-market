package kosta.market.domain.order.model;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

	@Insert("INSERT INTO TBL_ORDER VALUES (null, #{productId}, #{orderQuantity}, now(), #{orderState}")
	int insertOrder(int productId, int orderQuantity, int orderState);

	@Insert("INSERT INTO TBL_PAYMENT VALUES (null, #{paymentPrice}, #{paymentMethod}, now())")
	int insertPayment(@Param("paymentPrice") int paymentPrice, @Param("paymentMethod") String paymentMethod);

	@Insert("INSERT INTO TBL_ORDER_PAYMENT VALUES (null, #{orderId}, #{paymentId})")
	int insertOrderPayment(@Param("orderId") int orderId, @Param("paymentId") int paymentId);

	@Select("SELECT * FROM TBL_ORDER AS A JOIN"
		+ " TBL_USER_ORDER AS B ON A.order_id = B.order_id"
		+ " WHERE B.user_id = #{userId}")
	List<Order> selectOrderListByUserId(@Param("userId") int userId);

	@Select("SELECT * FROM TBL_ORDER AS A JOIN"
		+ " TBL_SELLER_PRODUCT AS B ON A.product_id = B.product_id"
		+ " WHERE B.seller_id = #{sellerId}")
	List<Order> selectOrderListBySellerId(@Param("sellerId") int sellerId);

	@Select("SELECT * FROM TBL_ORDER WHERE order_id = #{orderId}")
	Order selectOrder(@Param("orderId") int orderId);

	@Select("SELECT * FROM TBL_PAYMENT WHERE payment_id = #{paymentId}")
	Payment selectPayment(@Param("paymentId") int paymentId);

	@Update("UPDATE TBL_ORDER SET order_state = #{orderState}")
	int updateOrder(@Param("orderState") int orderState);
}












