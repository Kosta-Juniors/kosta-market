package kosta.market.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.AddressCheckDto;
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

        Integer userId = (Integer) session.getAttribute("userId");
        Object userAndSellerInfo = userService.userAndSellerInfo(userId);

        if(userAndSellerInfo != null) {

            Map<String, Object> map = new HashMap<>();
            map.put("data", userAndSellerInfo);
            return new ResponseEntity(map, HttpStatus.OK);
        }
        else {
            Object userInfo = userService.userInfo(userId);
            Map<String, Object> map = new HashMap<>();
            map.put("data", userInfo);
            return new ResponseEntity(map, HttpStatus.OK);
        }
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
    public ResponseEntity UserModify(@RequestBody User user, HttpSession session) {

        boolean modifyUser = userService.modifyUser(user, session);

        if (modifyUser) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/api/user")
    @ResponseBody
    public ResponseEntity userRemove(@RequestBody UserCheckDto userCheckDto, HttpSession session) {

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

        Integer userId = (Integer) session.getAttribute("userId");

        List<Object> addressList = new ArrayList();
        ArrayList<AddressDto> addressArrayList = (ArrayList<AddressDto>)userService.addressInfo(userId);
        for(int i=0; i <addressArrayList.size(); i++) {
            Map<String, Object> address = new HashMap<>();
            address.put("addressId",addressArrayList.get(i).getAddressId());
            address.put("userId",addressArrayList.get(i).getUserId() );
            address.put("deliveryPlace",addressArrayList.get(i).getDeliveryPlace() );
            address.put("isDefaultAddress",addressArrayList.get(i).getIsDefaultAddress() );
            address.put("title",addressArrayList.get(i).getTitle() );
            address.put("recipient",addressArrayList.get(i).getRecipient() );
            address.put("contact",addressArrayList.get(i).getContact() );
            addressList.add(address);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", addressList);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/user/address")
    @ResponseBody
    public ResponseEntity addressDelete(@RequestBody AddressCheckDto addressCheckDto, HttpSession session) {

        boolean removeAddress = userService.removeAddress(addressCheckDto.getAddressId(), session);

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

        boolean removeSeller = userService.removeSeller(sellerDto.getSellerId(), session);

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

