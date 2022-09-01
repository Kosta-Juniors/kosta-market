package kosta.market.domain.order.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailIdDto {

    // TBL_ORDER_DETAIL
    private int orderId;
    private int productId;

}
