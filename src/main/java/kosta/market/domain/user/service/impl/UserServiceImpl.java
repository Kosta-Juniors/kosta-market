package kosta.market.domain.user.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.AddressDto;
import kosta.market.domain.user.model.SellerDto;
import kosta.market.domain.user.model.User;
import kosta.market.domain.user.model.UserCheckDto;
import kosta.market.domain.user.model.UserCreateDto;
import kosta.market.domain.user.model.UserMapper;
import kosta.market.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addUser(UserCreateDto userCreateDto) {
        if (userCreateDto.getUsername() != null) {
            userMapper.insertUser(userCreateDto.getUsername(), userCreateDto.getPassword(),
                userCreateDto.getName(), userCreateDto.getContact());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean loginUser(UserCheckDto userCheckDto, HttpSession session) {

        User user = userMapper.selectUserByUsernameAndPassword(userCheckDto.getUsername(), userCheckDto.getPassword());
        if (user != null && userCheckDto.getPassword().equals(user.getPassword())) {
            session.setAttribute("user_id", user.getUser_id());
            if ( userMapper.selectJoinUserByUsernameAndPassword(userCheckDto.getUsername(), userCheckDto.getPassword()) != null) {
                session.setAttribute("seller_id", user.getSeller_id());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User userInfo(Integer userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public boolean modifyUser(User user) {

        User userModify = userMapper.selectUserByUserId(user.getUser_id());
        if (userModify != null) {
            userMapper.updateUser(userModify.getUser_id(), userModify.getPassword(), userModify.getContact());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeUser(UserCheckDto userCheckDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        User user = userMapper.selectUserByPassword(userCheckDto.getPassword());

        if (user != null && userCheckDto.getPassword().equals(user.getPassword())) {
            userMapper.deleteUser(userId);
            session.removeAttribute("user_id");
            return true;
        }
        return false;
    }

    @Override
    public boolean addAddress(AddressDto addressDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        userMapper.selectAddressByUserId(userId);

        if (addressDto.getDelivery_place() != null) {
            userMapper.updateAddress(addressDto.getUser_id());
            userMapper.insertAddress(addressDto.getUser_id(), addressDto.getDelivery_place());
            return true;
        }
        return false;
    }

    @Override
    public AddressDto addressInfo(Integer userId) {
        return userMapper.selectAddressByUserId(userId);
    }

    @Override
    public boolean removeAddress(AddressDto addressDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        userMapper.selectAddressByUserId(userId);

        if (addressDto != null) {
            userMapper.deleteAddress(addressDto.getAddress_id());
            return true;
        }
        return false;
    }

    @Override
    public boolean addSeller(SellerDto sellerDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        userMapper.selectSellerByUserId(userId);

        if (sellerDto.getBusiness_reg_no() != null) {
            session.setAttribute("seller_id", sellerDto.getSeller_id());
            userMapper.insertSeller(sellerDto.getUser_id(), sellerDto.getBusiness_reg_no());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SellerDto sellerInfo(Integer userId) {
        return userMapper.selectSellerByUserId(userId);
    }

    @Override
    public boolean removeSeller(SellerDto sellerDto, HttpSession session) {
        Object userId = session.getAttribute("user_id");
        SellerDto seller = userMapper.selectSellerByUserId(userId);
        if (userId != null) {
            userMapper.deleteSeller(seller.getSeller_id());
            session.removeAttribute("seller_id");
            return true;
        }
        return false;
    }

//    @Override
//    public boolean logoutUser(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//            return true;
//        }
//        return false;
//    }

}




