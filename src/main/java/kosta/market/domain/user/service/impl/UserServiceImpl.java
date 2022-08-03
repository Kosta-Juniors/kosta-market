package kosta.market.domain.user.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.SellerCreateDto;
import kosta.market.domain.user.model.User;
import kosta.market.domain.user.model.UserAddressDto;
import kosta.market.domain.user.model.UserCheckDto;
import kosta.market.domain.user.model.UserCreateDto;
import kosta.market.domain.user.model.UserMapper;
import kosta.market.domain.user.model.UserModifyDto;
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

        User user = userMapper.selectUserByUserName(userCheckDto.getUsername());

        if (user != null && userCheckDto.getPassword().equals(user.getPassword())) {
            session.setAttribute("user_id", user.getUser_id());
            if (user.getSeller_id() != null) {
                session.setAttribute("seller_id", user.getSeller_id());
            }

            return true;
        } else {
            return false;
        }
    }

    public UserModifyDto modifyUserForm(HttpSession session) {
        int user_id = (int) session.getAttribute("user_id");

        return userMapper.selectUser(user_id);
    }

    @Override
    public boolean modifyUser(UserModifyDto userModifyDto, HttpSession session) {
        int user_id = (int) session.getAttribute("user_id");
        if (userModifyDto != null) {
            userMapper.updateUser(user_id, userModifyDto.getPassword(), userModifyDto.getContact());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int addAddressForm(HttpSession session) {
        int user_id = (int) session.getAttribute("user_id");
        User user = userMapper.selectUserByUserId(user_id);

        if (user != null) {
            return user_id;
        } else

            return 0;
    }

    @Override
    public boolean addAddress(UserAddressDto userAddressDto) {

        if (userAddressDto.getDelivery_place() != null) {
            userMapper.updateAddress(userAddressDto.getUser_id());
            userMapper.insertAddress(userAddressDto.getUser_id(),
                userAddressDto.getDelivery_place());

            return true;
        } else
            return false;
    }

    @Override
    public int addSellerForm(HttpSession session) {
        int user_id = (int) session.getAttribute("user_id");
        User user = userMapper.selectUserByUserId(user_id);

        if (user != null) {
            return user_id;

        } else
            return 0;
    }

    @Override
    public boolean addSeller(SellerCreateDto sellerCreateDto) {

        if (sellerCreateDto.getBusiness_reg_no() != null) {
            userMapper.insertSeller(sellerCreateDto.getUser_id(), sellerCreateDto.getBusiness_reg_no());
            userMapper.updateSeller(sellerCreateDto.getUser_id(), sellerCreateDto.getBusiness_reg_no());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean logoutUser(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return true;
        } else
            return false;
    }

    @Override
    public boolean removeUser(UserCheckDto userCheckDto, HttpSession session) {

        int user_id = (int) session.getAttribute("user_id");
        User user = userMapper.selectUserByUserId(user_id);

        if ( user != null && userCheckDto.getPassword().equals(user.getPassword())) {

            userMapper.deleteUser(user_id);
            session.removeAttribute("user_id");

            return true;
        } else
           return false;
    }
}



