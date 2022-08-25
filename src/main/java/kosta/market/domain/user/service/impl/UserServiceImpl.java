package kosta.market.domain.user.service.impl;

import java.util.List;
import java.util.Objects;
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

        User user = userMapper.selectUserByUsernameAndPassword(userCheckDto.getUsername(),
            userCheckDto.getPassword());
        if (user != null && userCheckDto.getPassword().equals(user.getPassword())) {
            session.setAttribute("userId", user.getUserId());
            if (userMapper.selectJoinUserByUsernameAndPassword(userCheckDto.getUsername(),
                userCheckDto.getPassword()) != null) {
                session.setAttribute("sellerId", user.getSellerId());
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

        User userModify = userMapper.selectUserByUserId(user.getUserId());
        if (userModify != null) {
            userMapper.updateUser(userModify.getUserId(), userModify.getPassword(),
                userModify.getContact());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeUser(UserCheckDto userCheckDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        User user = userMapper.selectUserByPassword(userCheckDto.getPassword());

        if (user != null && userCheckDto.getPassword().equals(user.getPassword())) {
            userMapper.deleteUser(userId);
            session.removeAttribute("userId");
            return true;
        }
        return false;
    }

    @Override
    public boolean addAddress(AddressDto addressDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");


        if (addressDto.getDeliveryPlace() != null) {
            userMapper.updateAddress(addressDto.getUserId());
            userMapper.insertAddress(addressDto.getUserId(), addressDto.getDeliveryPlace(),
                addressDto.getTitle(), addressDto.getRecipient(), addressDto.getContact());
            return true;
        }
        return false;
    }

    @Override
    public List<AddressDto> addressInfo(Integer userId) {
        return userMapper.selectListAddressByUserId(userId);
    }

    @Override
    public boolean removeAddress(Integer addressId, HttpSession session) {

        if (Objects.equals(addressId, null)) {
            return false;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        AddressDto addressInfo = userMapper.selectAddressById(userId, addressId);

        if (addressInfo != null ) { // addressInfo.getAddressId().equals(addressId)
            userMapper.deleteAddress(addressId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addSeller(SellerDto sellerDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        userMapper.selectSellerByUserId(userId);

        if (sellerDto.getBusinessRegNo() != null) {
            session.setAttribute("sellerId", sellerDto.getSellerId());
            userMapper.insertSeller(sellerDto.getUserId(), sellerDto.getBusinessRegNo());
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
        Object userId = session.getAttribute("userId");
        SellerDto seller = userMapper.selectSellerByUserId(userId);
        if (userId != null) {
            userMapper.deleteSeller(seller.getSellerId());
            session.removeAttribute("sellerId");
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




