package kosta.market.domain.order.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    // cart(장바구니)안에 들어갈 product의 개별정보
    private int productId;
    private int orderQuantity;

//    private int orderPrice; // 상품가격(변동가능)과 구분을 위해
//    private int addressId; //공통사항으로 뺄 것

    // 주문의 공통사항
    // private String paymentMethod; // 결제방법
    // 주소지여부, 기존선택 or 신규추가
}
