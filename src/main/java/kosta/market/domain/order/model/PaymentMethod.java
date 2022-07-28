package kosta.market.domain.order.model;

public enum PaymentMethod {

	/** 카드결제 <br> */
	CARD("00"),
	/** 핸드폰결제 <br> */
	PHONE("01"),
	/** 무통장결제 <br> */
	FREE_BANKBOOK("02"),
	/** 카카오페이 <br> */
	KAKAO_PAY("03"),
	/** 네이버페이 <br> */
	NAVER_PAY("04");
	private final String code;
	PaymentMethod(String code) { this.code = code; }
	public String getCode() { return code; }
}
