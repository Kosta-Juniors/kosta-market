package kosta.market.global;

import java.util.List;
import kosta.market.global.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	private final static String REDIRECT_TO_LOGIN_URL = "redirect:/user/signin";
	private final static String REDIRECT_TO_ROOT_URL = "redirect:/";

	@GetMapping("/")
	public String index(){
		return "/index.html";
	}
	@GetMapping("/notfound")
	public String notFound(){
		return "/404.html";
	}

	/* 유저 도메인 시작 */
	@GetMapping("/user/signin")
	public String signIn(){
		if(SecurityUtil.isAuthenticated()) {
			return REDIRECT_TO_ROOT_URL;
		}
		return "/login.html";
	}

	@GetMapping("/user/signup")
	public String signUp(){
		if(SecurityUtil.isAuthenticated()) {
			return REDIRECT_TO_ROOT_URL;
		}
		return "/register.html";
	}
	@GetMapping("/user/signout")
	public String signOut(){
		if(SecurityUtil.isAuthenticated()) {
			SecurityUtil.getCurrentSession().invalidate();
		}
		return REDIRECT_TO_ROOT_URL;
	}
	@GetMapping("/user/account")
	public String myAccount(){
		if(SecurityUtil.isAuthenticated()) {
			return "/account.html";
		}
		return REDIRECT_TO_LOGIN_URL;
	}
	@GetMapping("/user/wishlist")
	public String wishlist(){
		if(SecurityUtil.isAuthenticated()) {
			return "/wishlist.html";
		}
		return REDIRECT_TO_LOGIN_URL;
	}
	@GetMapping("/user/cart")
	public String myCart(){
		if(SecurityUtil.isAuthenticated()) {
			return "/cart.html";
		}
		return REDIRECT_TO_LOGIN_URL;
	}
	/* 유저 도메인 끝 */


	/* 상품 도메인 시작 */
	@GetMapping("/product/create")
	public String productCreate(){
		if(!SecurityUtil.isAuthenticated()) {
			return REDIRECT_TO_LOGIN_URL;
		}
		return "/index.html";
	}

	@GetMapping("/product")
	public String productList(@RequestParam(value = "user-type", required = false, defaultValue = "user") Object userType,
								@RequestParam(value = "categoryId", required = false) Object categoryId,
								@RequestParam(value = "search", required = false) Object search){
		System.out.println("insiede");
		if ("user".equals(userType)) return "/shop-left-sidebar.html";
		return "/404.html";
	}

	@GetMapping("/product/{productId}")
	public String productDetail(@PathVariable String productId){
		return "/product-details.html";
	}


	//등록, 수정, 삭제만 구현하기
	/* 상품 도메인 끝 */




	/* 주문 도메인 시작 */
	@GetMapping("/order")
	public String makeOrder(@RequestParam String productList){ return "/checkout.html"; }

	/* 주문 도메인 끝 */


}
