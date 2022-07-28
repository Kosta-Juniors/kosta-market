package kosta.market.domain.product.controller;

import java.util.List;
import java.util.Map;
import kosta.market.domain.product.service.impl.ProductServiceImpl;
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
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;

	@GetMapping("/product/add")
	public String productAdd(){
		if(SecurityUtil.hasAuthorization()) return "product/add-product";
		return "redirect:/";
	}

	@PostMapping("/product/add/proc")
	@ResponseBody
	public HttpEntity productAddProc(@RequestBody Map<String, Object> reqBody){

		if(productService.addProduct(reqBody.get("categoryId"), reqBody.get("productName"),
			reqBody.get("productPrice"), reqBody.get("productQuantity"), "prod.png",
			"/", reqBody.get("productDescription"))){
			return new HttpEntity(HttpStatus.OK);
		}

		return new HttpEntity(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/product/remove")
	public String productRemove(){
		if(SecurityUtil.hasAuthorization()) return "product/remove-product";
		return "redirect:/";
	}

	@PostMapping("/product/remove/proc")
	public HttpEntity productRemoveProc(@RequestBody Map<String, Object> reqBody){
		if(productService.removeProduct(reqBody.get("productId"))){
			return new HttpEntity(HttpStatus.OK);
		}
		return new HttpEntity(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/product/{productId}")
	public String productDetail(Model model, @PathVariable("productId") Object productId){
    	System.out.println(productService.detailProduct(productId));
		model.addAttribute("product", productService.detailProduct(productId));
		return "product/product";
	}

	@GetMapping("/product/list")
	public String productList(Model model){
		List<Object> productList = productService.listProduct();
		model.addAttribute("productList", productList.get(0));
		return "product/list-product";
	}
	@GetMapping("/product/registlist")
	public String productRegistList(Model model){
		if (SecurityUtil.hasAuthorization()) {
			List<Object> productList = productService.registListProduct();
			model.addAttribute("productList", productList.get(0));
			return "product/list-registproduct";
		}
		return "redirect:/";
	}
}
