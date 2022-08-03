package kosta.market.domain.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.SellerCreateDto;
import kosta.market.domain.user.model.UserAddressDto;
import kosta.market.domain.user.model.UserCreateDto;
import kosta.market.domain.user.model.UserCheckDto;
import kosta.market.domain.user.model.UserModifyDto;
import kosta.market.domain.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/signin")
    public String userLoginForm() {

        return "user/signin";

    }

    @PostMapping("/signin")
    public String userLogin(@ModelAttribute UserCheckDto userCheckDto, HttpSession session, Model model) {
        boolean loginConfirm = userService.loginUser(userCheckDto, session);

        if (loginConfirm) {
            return "redirect:/";
        }

        model.addAttribute("message", "아이디를 찾을 수 없거나 비밀번호가 일치하지 않습니다.");

        return "user/signin";

    }
    @GetMapping("/signup")
    public String userAddForm() {

        return "user/signup";
    }

    @PostMapping("/signup")
    public String userAdd(UserCreateDto userCreateDto) {
        userService.addUser(userCreateDto);
        return "redirect:user/signin";
    }

    @GetMapping("/update")
    public String addModifyForm(Model model, HttpSession session) {
        UserModifyDto userModifyDto = userService.modifyUserForm(session);
        model.addAttribute("user", userModifyDto);

        return "user/update";
    }

    @PostMapping("/update")
    public String UserModify(HttpSession session, UserModifyDto userModifyDto) {
        boolean modifyConfirm = userService.modifyUser(userModifyDto, session);

        if (modifyConfirm) {
            return "redirect:/";
        } else {

            return "redirect:user/update";

        }
    }

    @GetMapping("/address")
    public String addressAddForm(Model model, HttpSession session) {

        int user_id = userService.addAddressForm(session);
        model.addAttribute("user_id", user_id);

        return "user/address";
    }

    @PostMapping("/address")
    public String addressAdd(UserAddressDto userAddressDto) {

        boolean addressForms = userService.addAddress(userAddressDto);

        if (addressForms) {
            return "redirect:/index";
        } else {

            return "user/address";
        }
    }

    @GetMapping("/seller")
    public String sellerAddForm(Model model, HttpSession session) {

        int user_id = userService.addSellerForm(session);
        model.addAttribute("user_id", user_id);

        return "user/seller";

    }


    @PostMapping("/seller")
    public String sellerAdd(SellerCreateDto sellerCreateDto) {

        boolean sellerForms = userService.addSeller(sellerCreateDto);

        if (sellerForms) {
            return "redirect:/index";
        } else {

            return "user/seller";
        }
    }

    @GetMapping("/logout")
    public String userLogout(HttpServletRequest request) {

        userService.logoutUser(request);

        return "redirect:/";

    }

    @GetMapping("/delete")
    public String userRemoveForm(HttpSession session) {

        return "user/delete";
    }

    @PostMapping("/delete")
    public String userRemove(UserCheckDto userCheckDto, HttpSession session) {
        boolean removeConfirm = userService.removeUser(userCheckDto, session);

        if (removeConfirm) {
            return "redirect:user/signin";
        } else
            return "user/delete";
    }
}

