package kosta.market.domain.user.service.impl;

import java.util.Objects;
import kosta.market.domain.product.model.ProductMapper;
import kosta.market.domain.user.model.Seller;
import kosta.market.domain.user.model.User;
import kosta.market.domain.user.model.UserMapper;
import kosta.market.domain.user.service.UserService;
import kosta.market.global.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ProductMapper productMapper;
	/**
	 * 기능 : 회원가입 <br> 설명 : <br> 아이디, 비밀번호, 회원명, 연락처가 담긴 <b>개인정보 객체</b>를 받아 <br> 일반 유저 계정을 생성한다. <br> 만약, 사업자등록번호가 개인정보
	 * 객체에 포함되었다면 <br> 판매자 계정을 생성한다.
	 *
	 * @return 회원 생성이 성공했다면 true, 아니면 false
	 */
	@Override
	@Transactional
	public boolean addUser(Object username, Object password, Object name, Object contact, @Nullable Object businessNo) {
		userMapper.insertUser(username, password, name, contact);
		if(!Objects.equals(businessNo, null)){
			Object lastInsertId = userMapper.lastInsertId();
			userMapper.insertSeller(lastInsertId,businessNo);
		}
		return false;
	}

	/**
	 * 기능 : 로그인 <br> 설명 : 아이디와 비밀번호를 받아 사용자 인증을 하고 세션에 User 객체를 등록한다. <br> 세션으로 쓰일 키 값은 아래와 같다.
	 * <pre> "authentication" : User 고유번호
	 * "authorization" : <u><b>*enum</b></u> 타입의 값
	 *
	 * <u><b>*enum</b></u> { 0:관리자, 10:판매자, 20:일반사용자 }</pre>
	 *
	 * @param username 회원 아이디
	 * @param password 비밀번호
	 * @return 인증이 성공한다면 true, 아니면 false
	 */
	@Override
	public boolean loginUser(Object username, Object password) {
		User user = userMapper.selectUserByUsernameAndPassword(username, password);
		if (Objects.equals(user, null)) return false;
		else {
			SecurityUtil.setAuthentication(user.getUserId());
			SecurityUtil.setAttribute("username", user.getUsername());
			SecurityUtil.setAttribute("name", user.getName());

			Seller seller = userMapper.selectSellerByUserId(user.getUserId());
			if (!Objects.equals(seller, null)) {
				SecurityUtil.setAuthorization(seller.getSellerId());
				SecurityUtil.setAttribute("businessNo", seller.getBusinessRegNo());
			}

			return true;
		}
	}

	/**
	 * 기능 : 회원 정보 수정 <br> 설명 : 연락처, 사업자번호, 배송지 정보가 담긴 개인정보 객체를 받아 <br> 회원정보를 변경한다. <br>
	 *
	 * @param userModifyDto 변경할 개인정보가 담긴 DTO 객체
	 * @return 회원 정보 수정이 성공한다면 true, 아닌경우 false
	 */
	@Override
	public boolean modifyUser(Object userModifyDto) {
		return false;
	}

	/**
	 * 기능 : 회원 삭제 <br> 설명 : 입력한 비밀번호가 회원의 비밀번호 일치하면 삭제한다. <br>
	 *
	 * @param password 비밀번호
	 * @return 회원 삭제가 성공한다면 true, 아닌경우 false
	 */
	@Override
	public boolean removeUser(Object password) {
		if(SecurityUtil.isAuthenticated()){
			User user = userMapper.selectUserByUserId(SecurityUtil.getAuthentication());
			if(!Objects.equals(user, null) && Objects.deepEquals(user.getPassword(), password)){
				userMapper.deleteUser(SecurityUtil.getAuthentication());
				SecurityUtil.invalidate();
				return true;
			}
		}

		return false;
	}

	public boolean addAddress(Object deliveryPlace, Object defaultAddressYn){

		if(SecurityUtil.isAuthenticated()){
      		userMapper.insertAddress(SecurityUtil.getAuthentication(), deliveryPlace, defaultAddressYn);
			return true;
		}
		return false;
	}
	public Object detailAddress(){
		if(SecurityUtil.isAuthenticated()){
			return userMapper.selectAddressByUserId(SecurityUtil.getAuthentication());
		}
		return null;
	}

	public Object getUserByUserId(Object userId){
		return null;
	}
}
