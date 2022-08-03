package kosta.market.domain.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.UserAddressDto;
import kosta.market.domain.user.model.UserCheckDto;
import kosta.market.domain.user.model.SellerCreateDto;
import kosta.market.domain.user.model.UserCreateDto;
import kosta.market.domain.user.model.UserModifyDto;

public interface UserService {

	boolean addUser(UserCreateDto userCreateDto);

	boolean loginUser(UserCheckDto userCheckDto, HttpSession session);

	boolean modifyUser(UserModifyDto userModifyDto, HttpSession session);

	int addAddressForm(HttpSession session);

	boolean addAddress(UserAddressDto userAddressDto);

	int addSellerForm(HttpSession session);

	boolean addSeller(SellerCreateDto sellerCreateDto);

	boolean logoutUser(HttpServletRequest request);

	boolean removeUser(UserCheckDto userCheckDto, HttpSession session);
}
