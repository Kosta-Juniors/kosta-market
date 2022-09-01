package kosta.market.domain.order.service;

import kosta.market.domain.order.model.OrderDetailDto;
import kosta.market.domain.order.model.OrderDetailForSellerDto;
import kosta.market.domain.order.model.OrderListDto;
import kosta.market.domain.order.model.OrderRequestDto;
import kosta.market.domain.user.model.AddressDto;

import java.util.ArrayList;
import java.util.List;
import kosta.market.domain.user.model.AddressDto;

public interface OrderService {

	List<AddressDto> getAddress(int userId);

	boolean addOrder(ArrayList<OrderRequestDto> cartList, String paymentMethod, int addressId, int userId);

	// addOrder 작업중 to 장바구니에서 LIST로 구매기능
	boolean addOrder(List<OrderRequestDto> orderRequestDtosList, String paymentMethod, int addressId, int userId);

	List<OrderListDto> listByUserIdOrder(int userId);

	ArrayList<OrderDetailForSellerDto> listBySellerIdOrder(int sellerId);


	OrderDetailDto detailOrder(int orderId);

	boolean exchangeOrder(int orderState , int orderId);

	boolean confirmOrder(int orderId, int orderState);

	boolean cancelOrder(int orderId, int orderState);


}
