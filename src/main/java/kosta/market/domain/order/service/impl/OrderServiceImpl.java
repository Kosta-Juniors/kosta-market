package kosta.market.domain.order.service.impl;

import java.util.List;
import kosta.market.domain.order.model.OrderMapper;
import kosta.market.domain.order.model.OrderState;
import kosta.market.domain.order.model.PaymentMethod;
import kosta.market.domain.order.service.OrderService;
import kosta.market.domain.product.model.Product;
import kosta.market.domain.product.model.ProductMapper;
import kosta.market.domain.user.model.UserMapper;
import kosta.market.global.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ProductMapper productMapper;

	@Override
	@Transactional
	public boolean addOrder(Object productId, Object orderQuantity, Object paymentMethod, Object addressId) {
		if (SecurityUtil.isAuthenticated()) {
			orderMapper.insertOrder(productId, orderQuantity, OrderState.CHECKING_ORDER.getStatusCode());
			Object orderId = orderMapper.lastInsertId();

			Product product = productMapper.selectProduct(productId);

			orderMapper.insertPayment(product.getProductPrice() * Integer.parseInt((String)orderQuantity), "00"); // paymethod
			Object paymentId = orderMapper.lastInsertId();

			orderMapper.insertOrderPayment(orderId, paymentId);

			//TBL_USER_ORDER
			orderMapper.insertUserOrder(SecurityUtil.getAuthentication(), addressId, orderId);
			return true;
		}
		// 자동으로 추가해주는 DB 트리거 확인
		return false;
	}

	@Override
	public Object listByUserIdOrder(Object userId) {
		if (SecurityUtil.isAuthenticated()) {
			return orderMapper.selectOrderListByUserId(userId);
		}
		return null;
	}

	@Override
	public Object listBySellerIdOrder(Object sellerId) {
		if (SecurityUtil.isAuthenticated()) {
			return orderMapper.selectOrderListBySellerId(sellerId);
		}
		return null;
	}

	@Override
	public Object detailOrder(Object orderId) {
		return orderMapper.selectOrder(orderId);
	}

	@Override
	public boolean exchangeOrder(Object orderNo) {
		return false;
	}

	@Override
	public boolean cancelOrder(Object orderNo) {
		return false;
	}

	@Override
	public boolean confirmOrder(Object orderNo) {
		return false;
	}
}
