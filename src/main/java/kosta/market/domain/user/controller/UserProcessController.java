package kosta.market.domain.user.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.AddressDto;
import kosta.market.domain.user.model.SellerDto;
import kosta.market.domain.user.model.User;
import kosta.market.domain.user.model.UserCheckDto;
import kosta.market.domain.user.model.UserCreateDto;
import kosta.market.domain.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class UserProcessController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/api/user/signin")
    @ResponseBody
    public ResponseEntity userLogin(HttpServletRequest request,
        @RequestBody UserCheckDto userCheckDto) {

        HttpSession session = request.getSession();
        boolean login = userService.loginUser(userCheckDto, session);

        if (login) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/user", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity getUserInfo(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        Object userInfo = userService.userInfo(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("data", userInfo);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("/api/user/signup")
    @ResponseBody
    public ResponseEntity userAdd(@RequestBody UserCreateDto userCreateDto) {

        boolean addUser = userService.addUser(userCreateDto);

        if (addUser) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/api/user")
    @ResponseBody
    public ResponseEntity UserModify(@RequestBody User user) {

        boolean modifyUser = userService.modifyUser(user);

        if (modifyUser) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/api/user")
    @ResponseBody
    public ResponseEntity userRemove(HttpSession session, @RequestBody UserCheckDto userCheckDto) {

        boolean removeUser = userService.removeUser(userCheckDto, session);

        if (removeUser) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/api/user/address")
    @ResponseBody
    public ResponseEntity addressAdd(@RequestBody AddressDto addressDto, HttpSession session) {

        boolean addAddress = userService.addAddress(addressDto, session);

        if (addAddress) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/api/user/address", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity getAddressInfo(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        Object addressInfo = userService.addressInfo(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("data", addressInfo);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/user/address")
    @ResponseBody
    public ResponseEntity addressDelete(@RequestBody AddressDto addressDto,
        HttpSession session) {

        boolean removeAddress = userService.removeAddress(addressDto, session);

        if (removeAddress) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/api/user/seller")
    @ResponseBody
    public ResponseEntity sellerAdd(@RequestBody SellerDto sellerDto, HttpSession session) {

        boolean addSeller = userService.addSeller(sellerDto, session);

        if (addSeller) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/api/user/seller")
    @ResponseBody
    public ResponseEntity sellerDelete(@RequestBody SellerDto sellerDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        userService.sellerInfo(userId);
        boolean removeSeller = userService.removeSeller(sellerDto, session);

        if (removeSeller) {

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

//    @GetMapping("/user/logout")
//    public ResponseEntity userLogout(HttpServletRequest request) {
//        userService.logoutUser(request);
//        return new ResponseEntity(HttpStatus.OK);
//    }

}

