package kosta.market.domain.order.service;

import kosta.market.domain.order.model.*;
import kosta.market.domain.product.model.Product;
import kosta.market.domain.user.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper mapper;



    // Address 정보받아오기
    public List<Address> getAddress(int userId){
        List<Address> addressList = mapper.selectAddressByUserId(userId);
        return addressList;
    }


    @Override
    public boolean addOrder(OrderRequestDto ord, int productId) {

        int insertOrderCount = mapper.insertOrder(productId, ord.getOrderQuantity());
        if(insertOrderCount==0){ // sql문 입력성공시 insertOrderCount=1반환(입력한 row개수)
            return false;
        }else{

            int orderId = mapper.lastInsertId();
            Product product = mapper.selectProduct(productId);
            int paymentPrice = ord.getOrderQuantity() * product.getProductPrice();
            int insertPaymentCount = mapper.insertPayment(paymentPrice ,ord.getPaymentMethod());

            if(insertPaymentCount==0){
                return false;
            }else{

                int paymentId = mapper.lastInsertId();
                int insertOrderPaymentCount = mapper.insertOrderPayment(orderId, paymentId);

                if(insertOrderPaymentCount==0){
                    return false;
                }else{

                    int userId = 1; // 하드코딩
                    int addressId = ord.getAddressId();
                    int insertUserOrderCount = mapper.insertUserOrder(userId, orderId, addressId);

                    if(insertUserOrderCount==0){
                        return false;
                    }else{
                        mapper.updateProduct(productId, ord.getOrderQuantity());
                        return true;
                    }

                }
            }
        }
    }

    @Override
    public ArrayList<OrderListDto> listByUserIdOrder(int userId) {
        return mapper.selectOrderListByUserId(userId);
    }

    @Override
    public List<OrderListDto> listBySellerIdOrder(int sellerId) {
        return mapper.selectOrderListBySellerId(sellerId);
    }


    @Override
    public OrderDetailDto detailOrder(int orderId) {
        return mapper.selectOrderDetail(orderId);
    }


    @Override
    public boolean exchangeOrder(int orderId, int orderState) {
        if (Objects.equals(orderState, 0)){
            mapper.updateOrderStateExchange(orderId);
            return true;
        }else{
            return false;
        }
    }

    public boolean confirmOrder(int orderId, int orderState) {
        if (Objects.equals(orderState, 0) || Objects.equals(orderState, 3)){
            mapper.updateOrderStateConfirm(orderId);
            return true;
        }else{
            return false;
        }
    }

    public boolean cancelOrder(int orderId, int orderState) {
        if (Objects.equals(orderState, 0) || Objects.equals(orderState, 3)){
            mapper.updateOrderStateCancel(orderId);

            // productId , orderQuantity 받아와야한다, TBL_ORDER에 포함되어 있다.
            Order order = mapper.selectOrder(orderId);
            int productId = order.getProductId();
            int orderQuantity = order.getOrderQuantity();

            // 갯수 원상복구
            mapper.updateProductQuantity(productId, orderQuantity);

            return true;
        }else{
            return false;
        }
    }

}
