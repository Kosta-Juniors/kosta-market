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

	@Update("UPDATE TBL_PRODUCT SET product_quantity=product_quantity-#{orderQuantity} WHERE product_id=#{productId}")
	int updateProduct(@Param("productId") int productId,
					  @Param("orderQuantity") int orderQuantity);


	@Insert("INSERT INTO TBL_ORDER VALUES (null, #{productId}, #{orderQuantity}, now(), 0)")
	int insertOrder(@Param("productId") int productId,
					@Param("orderQuantity") int orderQuantity);
	// int orderState 를 0(주문완료)로 입력

	@Insert("INSERT INTO TBL_PAYMENT VALUES (null, #{paymentPrice}, #{paymentMethod}, now())")
	int insertPayment(@Param("paymentPrice") int paymentPrice,
					  @Param("paymentMethod") String paymentMethod);

	// 가장 최근에 생성된 PK를  foreign 키로 받아오기
	@Select("SELECT LAST_INSERT_ID()")
	int lastInsertId();

	@Insert("INSERT INTO TBL_ORDER_PAYMENT VALUES (null, #{orderId}, #{paymentId})")
	int insertOrderPayment(@Param("orderId") int orderId,
						   @Param("paymentId") int paymentId);


	@Insert("INSERT INTO TBL_USER_ORDER VALUES (null, #{user_id}, #{order_id}, #{address_id})")
	int insertUserOrder(@Param("user_id") int user_id,
						@Param("order_id") int order_id,
						@Param("address_id") int address_id);

	// 여기까지가 Service에서 addOrder()에 사용하는 mapper

	//(Product table)상품상세정보 - Product단의 mapper

	//(Address table)주소지선택 - User단의 mapper


	@Select("select * from TBL_ORDER order by order_id desc  limit 1")
	int selectOrderId();



	@Select("SELECT A.*, B.product_name, D.payment_price, D.payment_method, F.delivery_place, G.name, G.contact" +
			" FROM TBL_ORDER as A" +
			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_USER_ORDER AS E ON A.order_id=E.order_id" +
			" LEFT JOIN TBL_ADDRESS AS F ON E.address_id=F.address_id" +
			" LEFT JOIN TBL_USER AS G ON E.user_id=G.user_id" +
			" WHERE A.order_id=#{order_id}")
	OrderDetailDto selectOrder(@Param("order_id") int order_id);



	// (구매자)주문리스트
	@Select("SELECT A.order_id, B.product_name, A.order_quantity, D.payment_price, A.order_state" +
			" FROM TBL_ORDER AS A" +
			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_USER_ORDER AS E ON A.order_id=E.order_id" +
			" WHERE E.user_id = #{user_id}")
	ArrayList<OrderListDto> selectOrderListByUserId(@Param("user_id") int user_id);




	// (판매자)주문리스트
	@Select("SELECT A.order_id, B.product_name, A.order_quantity, D.payment_price, A.order_state" +
			" FROM TBL_ORDER AS A" +
			" LEFT JOIN TBL_PRODUCT AS B ON A.product_id=B.product_id" +
			" LEFT JOIN TBL_ORDER_PAYMENT AS C ON A.order_id=C.order_id" +
			" LEFT JOIN TBL_PAYMENT AS D ON C.payment_id=D.payment_id" +
			" LEFT JOIN TBL_SELLER_PRODUCT AS H ON B.product_id=H.product_id" +
			" WHERE H.seller_id = #{seller_id}")
	List<OrderListDto> selectOrderListBySellerId(@Param("seller_id") int seller_id);



	@Select("SELECT * FROM TBL_PAYMENT WHERE payment_id = #{paymentId}")
	Payment selectPayment(@Param("paymentId") int paymentId);

	@Update("UPDATE TBL_ORDER SET order_state = #{orderState}")
	int updateOrder(@Param("orderState") int orderState);

	@Select("SELECT * FROM TBL_PRODUCT WHERE product_id=#{productId}")
	Product selectProduct(@Param("productId")int productId);

}












