package kosta.market.domain.order.controller;

import java.util.Map;
import java.util.Objects;
import kosta.market.domain.order.service.OrderService;
import kosta.market.domain.order.service.impl.OrderServiceImpl;
import kosta.market.domain.product.service.impl.ProductServiceImpl;
import kosta.market.domain.user.service.impl.UserServiceImpl;
import kosta.market.global.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private UserServiceImpl userService;

	@GetMapping("/order/{productId}/add")
	public String orderAdd(Model model, @PathVariable("productId") Object productId){
		if(SecurityUtil.isAuthenticated()){
      		System.out.println(userService.detailAddress());
			//userService
			model.addAttribute("product", productService.detailProduct(productId));
			model.addAttribute("seller", productService.ownerProduct(productId));
			model.addAttribute("addressList", userService.detailAddress());
			return "order/add-order";
		}
		return "redirect:/login";
	}
	@PostMapping("/order/add/proc")
	@ResponseBody
	public HttpEntity orderAddProc(@RequestBody Map<String, Object> reqBody){
		if (SecurityUtil.isAuthenticated()) {
		  orderService.addOrder(
			  reqBody.get("productId"),
			  reqBody.get("orderQuantity"),
			  reqBody.get("paymentMethod"),
			  reqBody.get("addressId"));
		  return new HttpEntity<>(HttpStatus.OK);
		}

		return new HttpEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/order/list")
	public String orderUserList(Model model){
		if (SecurityUtil.isAuthenticated()){
			model.addAttribute("userorderList", orderService.listByUserIdOrder(SecurityUtil.getAuthentication()));

			return "order/list-userorder";
		}
		return "redirect:/";
	}

	@GetMapping("/order/sellerlist")
	public String orderSellerList(Model model){
		if (SecurityUtil.isAuthenticated()){
			model.addAttribute("sellerorderList", orderService.listBySellerIdOrder(SecurityUtil.getAuthorization()));
			return "order/list-sellerorder";
		}
		return "redirect:/";
	}
	@GetMapping("/order/detail/{orderId}")
	public String orderDetail(Model model, @PathVariable Object orderId){
		if (SecurityUtil.isAuthenticated()){
			model.addAttribute("order", orderService.detailOrder(orderId));
			return "order/order";
		}
		return "redirect:/";
	}
}
