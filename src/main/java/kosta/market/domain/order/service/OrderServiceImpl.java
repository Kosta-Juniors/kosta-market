package kosta.market.domain.order.service;

import kosta.market.domain.order.model.*;
import kosta.market.domain.product.model.Product;
import kosta.market.domain.user.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper mapper;



    // Address 정보받아오기
    public List<Address> getAddress(int user_id){
        List<Address> addressList = mapper.selectAddressByUserId(user_id);
        return addressList;
    }




    @Override
    public boolean addOrder(OrderRequestDto ord, int product_id) {
        // insertOrder() / ORDER테이블 / 상품번호, 주문수량, 주문상태(0.주문완료 형태로 입력말고 mapper단에서 0으로 자동입력되게 변경요망)
        int a = mapper.insertOrder(product_id, ord.getOrder_quantity());
//        System.out.println(a);
        if(a==0){ // sql문 입력성공시 a=1반환(입력한 row개수)
            return false;
        }else{
            int order_id = mapper.lastInsertId();
            // insertPayment() / PAYMENT테이블 / 결제가격(상품가격*주문수량 으로 생성), 결제방법
            // int payment_price = ord.getOrderQuantity() * p.getProductPrice();
            // price의 타입자체를 int로 하면 어떨까?, 정수가 아닌 값일때, parseInt를 사용하면 exception 어떻게 처리할까,,
            Product product = mapper.selectProduct(product_id);


            int payment_price = ord.getOrder_quantity() * product.getProduct_price();
            int b = mapper.insertPayment(payment_price ,ord.getPayment_method());
//            System.out.println(b);

            if(b==0){ // 성공시 b=1반환
                return false;
            }else{
                int paymentId = mapper.lastInsertId();
                // insertOrderPayment() / 매핑테이블 /
                int c = mapper.insertOrderPayment(order_id, paymentId);
//                System.out.println(c);

                if(c==0){
                    return false;
                }else{
                    // USER_ORDER table 입력필요
                    // 회원번호, 주소번호, 주문번호
                    int user_id = 1; //나중에 session처리
                    int address_id = ord.getAddress_id();
                    // order_id 위에서 받아왔음
                    // mapper insert문
//                    System.out.println(order_id);
                    int d = mapper.insertUserOrder(user_id, order_id, address_id);
//                    System.out.println(d);

                    if(d==0){
                        return false;
                    }else{
                        mapper.updateProduct(product_id, ord.getOrder_quantity());

                        return true;
                    }

                }
            }
        }
    }

    public int getLastOrderId(){
        return mapper.selectOrderId();
    }

    @Override
    public OrderDetailDto detailOrder(int order_id) {
        return mapper.selectOrder(order_id);
    }

    @Override
    public boolean exchangeOrder(int orderState, int orderId) {
        return false;
    }


    @Override
    public ArrayList<OrderListDto> listByUserIdOrder(int user_id) {
        return mapper.selectOrderListByUserId(user_id);
    }

    @Override
    public List<OrderListDto> listBySellerIdOrder(int seller_id) {
        return mapper.selectOrderListBySellerId(seller_id);
    }



    @Override
    public boolean exchangeOrder(int orderId) {
        mapper.updateOrderStateExchange(orderId);
        return true;
    }

    public boolean cancelOrder(int orderId) {
        mapper.updateOrderStateCancel(orderId);
        return true;
    }

    public boolean confirmOrder(int orderId) {
        mapper.updateOrderStateConfirm(orderId);
        return true;
    }




    @Override
    public boolean updateProduct(int product_id, int order_quantity) {
        mapper.updateProductQuantity(product_id, order_quantity);
        return true;
    }

//    @Override
//    public boolean cancelOrder(int orderId) {
//        return false;
//    }



//    @Override
//    public boolean confirmOrder(int orderId) {
//        return false;
//    }








}
