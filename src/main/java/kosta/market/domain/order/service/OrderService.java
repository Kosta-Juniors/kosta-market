package kosta.market.domain.order.service;

import kosta.market.domain.order.model.OrderDetailDto;
import kosta.market.domain.order.model.OrderListDto;
import kosta.market.domain.order.model.OrderRequestDto;
import kosta.market.domain.user.model.Address;

import java.util.List;

public interface OrderService {

	List<Address> getAddress(int userId);

	boolean addOrder(OrderRequestDto ord, int productId);

	List<OrderListDto> listByUserIdOrder(int userId);

	List<OrderListDto> listBySellerIdOrder(int sellerId);


	OrderDetailDto detailOrder(int orderId);

	boolean exchangeOrder(int orderState , int orderId);

	boolean confirmOrder(int orderId, int orderState);

	boolean cancelOrder(int orderId, int orderState);


}
