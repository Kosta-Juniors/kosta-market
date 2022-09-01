package kosta.market.domain.order.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class OrderDetailDto { //cartList주문의 (상품별)개별사항

    // TBL_ORDER_DETAIL
    private int  productId;
    private int  orderQuantity;
    private String  orderState;
    private int orderPrice;

    // TBL_PRODUCT
    private String  productName;






}
