package kosta.market.domain.order.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderListDto {
    // 주문번호, 상품명, 구매개수, 주문금액, 주문상태
    private int orderId;
    private String productName;
    private int orderQuantity;
    private int paymentPrice;
    private char orderState;


}
