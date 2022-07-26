package kosta.market.domain.user.controller;

import java.util.Map;
import kosta.market.domain.user.service.impl.UserServiceImpl;
import kosta.market.global.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping("/")
	public String index(Model model){

		model.addAttribute("username", SecurityUtil.getAttribute("username"));
		model.addAttribute("name", SecurityUtil.getAttribute("name"));
		model.addAttribute("seller", SecurityUtil.getAuthorization());
		model.addAttribute("businessNo", SecurityUtil.getAttribute("businessNo"));
		return "index";
	}

	@GetMapping("/login")
	public String userLogin(){
		if(SecurityUtil.isAuthenticated()){
			return "redirect:/";
		}
		return "login";
	}

	@PostMapping("/login/proc")
	@ResponseBody
	public ResponseEntity userLoginProc(@RequestBody Map<String, Object> resBody){
		if(userService.loginUser(resBody.get("username"), resBody.get("password"))){
			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/logout/proc")
	public String userLogout(){
		SecurityUtil.invalidate();
		return "redirect:/";
	}

	@GetMapping("/user/add")
	public String userAdd(){
		if (SecurityUtil.isAuthenticated()){
			return "redirect:/";
		}

		return "signup";
	}

	@ResponseBody
	@PostMapping("/user/add/proc")
	public String userAddProc(@RequestBody Map<String, Object> resBody){
		userService.addUser(resBody.get("username"),
			resBody.get("password"),
			resBody.get("name"),
			resBody.get("contact"),
			resBody.get("business_no"));
		return "OK";
	}

	@GetMapping("/user/address/add")
	public String addAddress(){
		if(SecurityUtil.isAuthenticated()) return "address";
		return "redirect:/";
	}

	@PostMapping("/user/address/add/proc")
	@ResponseBody
	public String addAddressProc(@RequestBody Map<String, Object> resBody){
		userService.addAddress(resBody.get("delivery"), resBody.get("default-address"));
		return "OK";
	}

	@GetMapping("/user/modify")
	public String userModify(){
		if(SecurityUtil.isAuthenticated()) return "modify-user";
		return "redirect:/";
	}

	@PostMapping("/user/modify/proc")
	@ResponseBody
	public String userModifyProc(){
		if(SecurityUtil.isAuthenticated()){
			return "modify-user";
			//userService.modifyUser();
		}
		return "redirect:/";
	}

	@GetMapping("/user/delete")
	public String userRemove(){
		if(SecurityUtil.isAuthenticated()) return "delete-user";
		return "redirect:/";
	}

	@PostMapping("/user/delete/proc")
	@ResponseBody
	public HttpEntity userRemoveProc(@RequestBody Map<String, Object> resBody) {
    	System.out.println("RECV : " + resBody.get("password"));
		if(userService.removeUser(resBody.get("password"))){
			return new HttpEntity(HttpStatus.OK);
		}
		return new HttpEntity(HttpStatus.FORBIDDEN);
	}
}
