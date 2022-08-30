package kosta.market.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/user/signup")
    public String signup() {
        return "/user/signup.jsp";
    }

    @GetMapping("/user/signin")
    public String singin() {
        return "/user/signin.jsp";
    }

    @GetMapping("/user/update")
    public String update() {
        return "/user/update.jsp";
    }

    @GetMapping("/user/delete")
    public String delete() {
        return "/user/delete.jsp";
    }
}
