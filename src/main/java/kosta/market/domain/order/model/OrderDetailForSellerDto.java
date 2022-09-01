package kosta.market.domain.order.model;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailForSellerDto {

    // TBL_ORDER_DETAIL
    private int orderId;
    private int productId;
    private int orderQuantity;
    private String orderState;
    private int orderPrice;

    // TBL_ORDER
    private Date orderDate;

    // TBL_PRODUCT
    private String productName;


}
