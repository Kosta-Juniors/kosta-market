package kosta.market.domain.order.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	private int orderId;
	private int productId;
	private int orderQuantity;
	private Date orderDate;
	private char orderState;
}
