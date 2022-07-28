package kosta.market.domain.order.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
	private int orderId;
	private int productId;
	private int orderQuantity;
	private Date orderDate;
	private char orderState;
}
