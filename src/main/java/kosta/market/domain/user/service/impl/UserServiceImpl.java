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

        UserCheckDto checkUser = userMapper.selectUserByUsernameAndPassword(userCheckDto.getUsername(),
            userCheckDto.getPassword());
        if (checkUser != null && userCheckDto.getPassword().equals(checkUser.getPassword())) {
            session.setAttribute("userId", checkUser.getUserId());
            if (userMapper.selectJoinUserByUsernameAndPassword(userCheckDto.getUsername(),
                userCheckDto.getPassword()) != null) {
                session.setAttribute("sellerId", checkUser.getSellerId());
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
    public SellerDto userAndSellerInfo(Integer userId) {
        return userMapper.selectUserAndSellerByUserId(userId);
    }

    @Override
    public boolean modifyUser(UserModifyDto userModifyDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        UserModifyDto userModify = userMapper.selectUserCheckByUserId(userId);
        if (userModify != null) {
            userMapper.updateUser(userModifyDto.getUserId(), userModifyDto.getPassword(),
                userModifyDto.getContact());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeUser(UserCheckDto userCheckDto, HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        UserCheckDto checkUser = userMapper.selectUserByPassword(userCheckDto.getPassword());

        if (checkUser != null && userCheckDto.getPassword().equals(checkUser.getPassword())) {
            userMapper.deleteUser(userId);
            session.removeAttribute("userId");
            return true;
        }
        return false;
    }

    @Override
    public boolean addAddress(AddressDto addressDto, HttpSession session) {

        if (addressDto.getDeliveryPlace() != null) {
            userMapper.updateAddress(addressDto.getUserId());
            userMapper.insertAddress(addressDto.getUserId(), addressDto.getDeliveryPlace(),
                addressDto.getTitle(), addressDto.getContact(), addressDto.getRecipient() );
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
        AddressDto address = userMapper.selectAddressById(userId, addressId);

        if (address != null && address.getAddressId().equals(addressId) ) {
            userMapper.deleteAddress(addressId);
            return true;
        }
        return false;
    }

    @Override
    public boolean addSeller(SellerDto sellerDto, HttpSession session) {
        if (sellerDto.getBusinessRegNo() != null) {
            session.setAttribute("sellerId", sellerDto.getSellerId());
            userMapper.insertSeller(sellerDto.getUserId(), sellerDto.getBusinessRegNo());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeSeller(Integer sellerId, HttpSession session) {

        if (Objects.equals(sellerId, null)) {
            return false;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        SellerDto seller = userMapper.selectSellerById(userId, sellerId);
        if (seller != null && seller.getSellerId().equals(sellerId)) {
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




