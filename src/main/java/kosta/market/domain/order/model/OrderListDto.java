package kosta.market.domain.order.model;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderListDto { // 하나의주문(=장바구니 주문)

    // 공통사항
    private int orderId;
    private Date orderDate;

    // TBL_PAYMENT
    private int paymentPrice;
    private String paymentMethod;

    // TBL_ADDRESS
    private String deliveryPlace;
    private String contact;
    private String recipient;
    // 개별사항
    private List<OrderDetailDto> orderList;

//    private int orderId;
//    private String productName;
//    private int orderQuantity;
//    private int paymentPrice;
//    private char orderState;

    public void setOrderMetaInfo(OrderDto orderDto){
        this.orderId = orderDto.getOrderId();
        this.orderDate =orderDto.getOrderDate();

        this.paymentPrice =orderDto.getPaymentPrice();
        this.paymentMethod =orderDto.getPaymentMethod();

        this.contact =orderDto.getContact();
        this.deliveryPlace =orderDto.getDeliveryPlace();
        this.recipient =orderDto.getRecipient();
    }
}
