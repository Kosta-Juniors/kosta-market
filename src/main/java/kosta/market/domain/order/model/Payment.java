package kosta.market.domain.order.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
	private int paymentId;
	private int paymentPrice;
	private String paymentMethod;
	private Date paymentDate;
}
