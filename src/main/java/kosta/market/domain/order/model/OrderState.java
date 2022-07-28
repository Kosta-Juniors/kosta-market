package kosta.market.domain.order.model;

public enum OrderState {

	/** 주문확인중 <br> : 판매자가 아직 구매자의 주문요청을 수락하지 않은 경우 */
	CHECKING_ORDER("00"),
	/** 배송준비중 <br> : 판매자가 주문을 확인하고, 상품 배송을 준비중인 경우 */
	PREPARING_DELIVERY("01"),
	/** 배송중 <br> : 택배사가 상품을 수령한 경우 */
	START_DELIVERY("02"),
	/** 배송완료 <br> : 판매자에게 상품을 받았으나 구매확정을 하지 않은 경우 */
	ARRIVED_DELIVERY("03"),

	/** 주문취소 <br> : 구매자가 단순변심으로 주문을 취소한 경우 */
	CANCLE_ORDER("04"),

	/** 교환신청 <br> : 구매자가 상품에 문제가 있어 다른 제품으로 교환을 신청한 경우 */
	REQUEST_EXCHANGE("05"),
	/** 주문취소,환불 신청 거부 (판매자용) <br> : 구매자의 환불신청을 거부하는 경우 */
	REJECT_REQUEST_EXCHANGE("06"),
	/** 주문취소,환불 신청 승인 (판매자용) <br> : 구매자의 환불신청을 거부하는 경우 */
	CONFIRM_REQUEST_EXCHANGE("07"),

	/** 환불신청 <br> : 구매자가 주문을 취소한 경우 */
	REQUEST_REFUND("08"),
	/** 주문취소,환불 신청 거부 (판매자용) <br> : 구매자의 환불신청을 거부하는 경우 */
	REJECT_REQUEST_REFUND("09"),
	/** 주문취소, 환불 승인 (판매자용) <br> : 구매자에게 환불이 완료된 경우 */
	CONFIRM_REQUEST_REFUND("10"),

	/** 주문확정 <br> : 결제일로부터 N일이 경과하거나, 주문자가 주문확인 버튼을 누른 경우 */
	CONFIRM_ORDER("11");

	private final String statusCode;
	OrderState(String statusCode) { this.statusCode = statusCode; }
	public String getStatusCode() { return statusCode; }
}
