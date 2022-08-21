package kosta.market.domain.order.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequestDto {
    // 구매개수, 결제수단, 기본주소여부, 배송지
    private int orderQuantity;
    private String paymentMethod;
    private int addressId;
}
