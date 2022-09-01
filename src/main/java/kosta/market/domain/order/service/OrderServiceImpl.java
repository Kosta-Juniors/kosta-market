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
    public boolean addOrder(ArrayList<OrderRequestDto> cartList, String paymentMethod, int addressId, int userId) {
        return false;
    }


//    // addOrder 원본
//    @Override
//    public boolean addOrder(OrderRequestDto ord, int productId) {
//
//        int insertOrderCount = mapper.insertOrder(productId, ord.getOrderQuantity());
//        if(insertOrderCount==0){ // sql문 입력성공시 insertOrderCount=1반환(입력한 row개수)
//            return false;
//        }else{
//
//            int orderId = mapper.lastInsertId();
//            Product product = mapper.selectProduct(productId);
//            int paymentPrice = ord.getOrderQuantity() * product.getProductPrice();
//            int insertPaymentCount = mapper.insertPayment(paymentPrice ,ord.getPaymentMethod());
//
//            if(insertPaymentCount==0){
//                return false;
//            }else{
//
//                int paymentId = mapper.lastInsertId();
//                int insertOrderPaymentCount = mapper.insertOrderPayment(orderId, paymentId);
//
//                if(insertOrderPaymentCount==0){
//                    return false;
//                }else{
//
//                    int userId = 1; // 하드코딩
//                    int addressId = ord.getAddressId();
//                    int insertUserOrderCount = mapper.insertUserOrder(userId, orderId, addressId);
//
//                    if(insertUserOrderCount==0){
//                        return false;
//                    }else{
//                        mapper.updateProduct(productId, ord.getOrderQuantity());
//                        return true;
//                    }
//
//                }
//            }
//        }
//    }


    // addOrder 작업중 to 장바구니에서 LIST로 구매기능
    @Override
    public boolean addOrder(List<OrderRequestDto> orderRequestDtosList , String paymentMethod, int addressId, int userId) {

        // 주문하는 상품을 cart에서 지우는 것도 필요하다!!
        // TBL_CART는 where user_id(FK)로 찾을 것!, cart_id(PK)는 지울값
        // cart에 없으면 그냥 pass



        // 메소드 흐름 정리
        // 1.insertOrder를 통해 cart전체에 대한 주문(번호를)입력
        int CountOrder = mapper.insertOrder();
        if(Objects.equals(CountOrder, 0)){
            return false;
        }

        // 3.lastInsertId를 통해 주문번호(orderId)를 받아와서 변수에 저장 >> insertOrderDetail에 뿌려줘야한다.
        int orderId = mapper.lastInsertId();

        // (반복문) - cart에 담긴 product갯수 만큼 실제 (개별적인)주문기능을 실행한다.
        //      4.selectProduct를 통해 상품의 가격을 가져온다.
        //      추후 수량(재고)도 가져와서 현재 갯수와, 가격이 남아있는지 검증한다. >> 안맞으면 주문전체실패 >> Rollback(트랜잭션을 취소하는 내용아닌가? 범위가 짧은거 같다,,,)
        //      2.insertOrderDetail 실제 product에 대한 주문을 입력
        //      8.updateProduct 주문한 만큼 상품의 재고를 줄인다.
        //      // cart에서 삭제

        // (반복문 종료) , 반복문 과정에서 paymentPrice를 연산 = 시그마(상품가격*주문갯수)

        int paymentPrice = 0; // 결제가격 = total가격
        for(int i=0; i< orderRequestDtosList.size(); i++){
             OrderRequestDto orderRequest = orderRequestDtosList.get(i);
            // 4.
            Product p = mapper.selectProduct(orderRequest.getProductId());
            if (p.getProductQuantity() < orderRequest.getOrderQuantity()){ //재고확인
                return false;
            }
//            if (!Objects.equals(p.getProductPrice(),orderRequest.getOrderPrice())) { //가격확인
//                return false;
//            }
            // 2.
            int countOrderDetail = mapper.insertOrderDetail(orderId, p.getProductId(), orderRequest.getOrderQuantity(), p.getProductPrice());
            if (Objects.equals(countOrderDetail,0)){
                return false;
            }
            // 8.
            int countUpdateProduct = mapper.updateProduct(p.getProductId(),orderRequest.getOrderQuantity());
            if (Objects.equals(countUpdateProduct,0)){
                return false;
            }

            // TBL_CART에서 삭제기능 추가 필요
            // paymentPrice(최종가격, 합계가격)에 추가
            paymentPrice += orderRequest.getOrderQuantity() * p.getProductPrice();
        }

        // 5.insertPayment - 반복문이 끝나고 paymentPrice를 받아서 실행해야함
        int countInsertPayment = mapper.insertPayment(paymentPrice, paymentMethod);
        if(Objects.equals(countInsertPayment,0)){
            return false;
        }
        // 3. lastInsertId를 통해 paymentId를 받아온다.
        int paymentId = mapper.lastInsertId();
        // 6.insertOrderPayment - 5번이 끝나고 paymentId를 받아서 실행해야함
        int countInsertOrderPayment = mapper.insertOrderPayment(orderId, paymentId);
        if (Objects.equals(countInsertOrderPayment,0)){
            return false;
        }

        // (나중에 구현) 9.insertAddress - 주소지 등록을 선택했을 경우에만 실행
        // 7.insertUserOrder - 매핑테이블 for 주소지연결
        int countInsertUserOrder = mapper.insertUserOrder(userId, orderId, addressId);
        if(Objects.equals(countInsertUserOrder,0)){
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<OrderListDto> listByUserIdOrder(int userId) {
        // 1. List<orderId>가져오기 >> 새로운 maaper필요
        List<Integer> orderIdList = mapper.getOrderIdListByUserId(userId);

        // 2. List<OrderListDto>가져오기 >> 1의 리스트.size만큼 반복문으로 할당

        ArrayList<OrderListDto> orderAllList = new ArrayList<>();
        for(int i=0; i<orderIdList.size(); i++){
            OrderListDto orderList = new OrderListDto(); //orderAllList에 담을 객체
            int orderId = orderIdList.get(i); // 출력할 orderId 설정

            // 공통사항 - OrderDto selectOrder(int orderId)
            OrderDto orderData = selectOrder(orderId);
            orderList.setOrderMetaInfo(orderData);

            // 개별사항 - ArrayList<OrderDetailDto> selectOrderDetailDto
            orderList.setOrderList(selectOrderDetailDto(orderId));
            // orderAllList 에 orderList를 담아준다
            orderAllList.add(orderList);
        }

        return orderAllList;
    }

    @Override
    public ArrayList<OrderDetailForSellerDto> listBySellerIdOrder(int sellerId) {
        // 1. sellerId를 통해 LIST<productId>를 뽑아온다.
        List<Integer> productIdList = mapper.getProductIdListBySellerId(sellerId);

        // 2. productIdList[k] 에 해당하는 (즉, 하나의 product에 해당하는) 판매내역 LIST<OrderDetailDto>를 뽑아온다.
        ArrayList<OrderDetailForSellerDto> OrderAllList = new ArrayList<OrderDetailForSellerDto>();
        for(int i=0; i<productIdList.size(); i++){
            int productId = productIdList.get(i);
            ArrayList<OrderDetailForSellerDto> orderDetailList = mapper.selectOrderDetailForSellerByProductId(productId);
            OrderAllList.addAll(orderDetailList);

        }
        return OrderAllList;
    }

    @Override
    public OrderDetailDto detailOrder(int orderId) {
        return null;
    }


    // 주문공통정보 OrderDto
    public OrderDto selectOrder(int orderId){
        return mapper.selectOrder(orderId);
    }


    //주문 (상품별)상세정보 OrderDetailDto
    public ArrayList<OrderDetailDto> selectOrderDetailDto(int orderId){
        return mapper.selectOrderDetailByOrderId(orderId);
    }


    @Override
    public boolean exchangeOrder(int orderId, int productId) {
    int orderState = mapper.getOrderState(orderId, productId);

        if (Objects.equals(orderState, 0)){ // 현재 orderState를 받아와서 0과 같은지 확인한다.
            mapper.updateOrderStateExchange(orderId, productId);
            return true;
        }else{
            return false;
        }
    }

    public boolean confirmOrder(int orderId, int productId) {
        int orderState = mapper.getOrderState(orderId, productId);

        if (Objects.equals(orderState, 0) || Objects.equals(orderState, 3)){
            mapper.updateOrderStateConfirm(orderId,productId);
            return true;
        }else{
            return false;
        }
    }

    public boolean cancelOrder(int orderId, int productId) {
        int orderState = mapper.getOrderState(orderId, productId);

        if (Objects.equals(orderState, 0) || Objects.equals(orderState, 3)){
            mapper.updateOrderStateCancel(orderId, productId);

            // orderQuantity 받아와야한다, TBL_ORDER_DETAIL에 포함되어 있다.
            OrderRequestDto order = mapper.selectOrderRequestDto(orderId, productId);
            int orderQuantity = order.getOrderQuantity();

            // 갯수 원상복구
            mapper.updateProductQuantity(productId, orderQuantity);

            return true;
        }else{
            return false;
        }
    }

    // productId , orderId 예외처리
    public boolean handlingIdException(Object Id){

        // 1. null값인지 확인
        // 2. Data-type이 int인지 확인
        // 3. DB에 존재하는 값인지 확인

        //try-catch 구문으로 시도
        try{
            int id = Integer.parseInt(Id.toString());
//            if(Id instanceof Integer){} // 정수인지 검사

            return true;
        }catch(NullPointerException e){ // 1. null값인지 확인
            return false;
        }catch (NumberFormatException e){ // 2. Data-type이 int인지 확인
            return false;
        }


    }






}
