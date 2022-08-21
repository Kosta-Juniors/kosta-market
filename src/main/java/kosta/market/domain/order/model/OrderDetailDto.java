package kosta.market.domain.order.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class OrderDetailDto {

    private int  orderId;
    private int  productId;
    private int  orderQuantity;
    private Date orderDate;
    private String  orderState;
    private String  productName;
    private int  paymentPrice;
    private String  paymentMethod;
    private String  deliveryPlace;
    private String  name;
    private String  contact;

}
