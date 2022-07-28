package kosta.market.domain.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SellerOrderDto {
	private Object orderId; // 주문번호
	private Object productName; // 상품명
	private Object paymentPrice; // 총 구매가격
	private Object orderQuantity; // 구매 수량
	private Object orderDate; // 구매일자
	private Object deliveryPlace; // 배송지
	private Object orderState; // 주문상태
	private Object ordererId; // 구매자 고유번호
	private Object orderer; // 구매자


}
