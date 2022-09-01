package kosta.market.domain.order.model;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto { //cartLIST주문의 공통사항

	// TBL_ORDER
	private int orderId;
	private Date orderDate;

	// TBL_PAYMENT
	private int paymentPrice;
	private String paymentMethod;

	// TBL_ADDRESS
	private String deliveryPlace;
	private String contact;
	private String recipient;

}
