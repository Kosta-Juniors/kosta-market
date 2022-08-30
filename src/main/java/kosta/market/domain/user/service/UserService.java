package kosta.market.domain.user.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kosta.market.domain.user.model.AddressCheckDto;
import kosta.market.domain.user.model.AddressDto;
import kosta.market.domain.user.model.SellerDto;
import kosta.market.domain.user.model.User;
import kosta.market.domain.user.model.UserCheckDto;
import kosta.market.domain.user.model.UserCreateDto;

public interface UserService {

	/**
	 * 기능 : 로그인 처리
	 * 아이디, 패스워드가 일치하면 로그인
	 * @param userCheckDto 로그인 시 DB와 비교할 아이디, 패스워드가 담긴 Dto
	 * @param session 일반 유저라면 key = "user_id", value = user_id 세션 생성
	 *                판매자라면 key = "seller_id", value = seller_id 추가적으로 세션 생성
	 * @return 로그인이 성공하면 true, 실패했다면 false
	 */
	boolean loginUser(UserCheckDto userCheckDto, HttpSession session);

	/**
	 * 기능 : 회원 가입 처리
	 * 등록할 내용 : 아이디(username), 패스워드(password), 이름(name), 연락처(contact)
	 * user_id는 자동으로 등록
	 * @param userCreateDto 등록할 유저 정보가 담긴 Dto
	 * @return 유저 등록이 성공하면 true, 실패했다면 false
	 */
	boolean addUser(UserCreateDto userCreateDto);

	/**
	 * 기능 : 회원정보 불러오기
	 * 확인 가능한 정보 : user_id, 아이디(username), 패스워드(password), 이름(name), 연락처(contact)
	 * seller_id가 있다면 sell_id도 확인 가능
	 * @param userId userId를 통해 User를 검색
	 * @return
	 */
	User userInfo(Integer userId);

	User userAndSellerInfo(Integer userId);

	/**
	 * 기능 : 회원정보 변경처리
	 * 변경 가능한 정보 : 패스워드(password), 연락처(contact)
	 * @param user user_id를 통해 User를 검색해 userModify 인스턴스를 생성하고 이를 통해 변경처리
	 * @return 회원정보 변경처리에 성공하면 true, 실패하면 false
	 */
	boolean modifyUser(User user, HttpSession session);

	/**
	 * 기능 : 회원 탈퇴 처리
	 * 회원 탈퇴시 필요한 정보 : 패스워드(password)
	 * @param userCheckDto 회원 탈퇴 처리시 필요한 패스워드를 담고 있는 Dto
	 * @param session 회원 탈퇴 처리시 필요한 userId 제공
	 * @return 회원 탈퇴 처리가 성공하면 true, 실패하면 false
	 */
	boolean removeUser(UserCheckDto userCheckDto, HttpSession session);

	/**
	 * 기능 : 배송지 등록
	 * is_default_address '1' -> 현재 배송지
	 * 배송지가 추가되면 update를 통해 is_default_address 값이 '0' 으로 설정하고,
	 * insert를 통해 is_default_address 값을 '1'로 설정함
	 * 새로운 배송지가 추가되면 기존의 is_default_address의 값은 0 으로 변하게 됨
	 * @param addressDto 배송지와 관련된 user_id, address_id, delively_place, is_default_address를 담고 있는 Dto
	 * @return 배송지 등록이 성공하면 true, 실패하면 false
	 */
	boolean addAddress(AddressDto addressDto, HttpSession session);

	/**
	 * 기능 : 배송지 불러오기
	 * 확인 가능한 정보 : user_id, address_id, delively_place, is_default_address
	 * @param userId userId를 통해 AddressDto 검색
	 * @return
	 */
	List<AddressDto> addressInfo(Integer userId);

	/**
	 *
	 * @param addressId addressId를 통해 가장 오래된 배송지 삭제
	 * @param session 세션을 통해 userId 가져옴
	 * @return
	 */
	boolean removeAddress(Integer addressId, HttpSession session);

	/**
	 * 기능 : 판매자 등록
	 * 판매자 등록하면서 key = "seller_id", value = seller_id 생성
	 * @param sellerDto 판매자와 관련된 user_id, Seller_id, business_reg_no 를 담고 있는 Dto
	 * @param session
	 * @return 판매자 등록이 성공하면 true, 실패하면 false
	 */
	boolean addSeller(SellerDto sellerDto, HttpSession session);

	/**
	 * 기능 : 판매자 제거
	 * @param SellId sellId와 userId를 통해 판매자를 삭제
	 * @param session 세션을 통해 userId를 가져옴
	 * @return
	 */
	boolean removeSeller(Integer SellId, HttpSession session);

//	/**
//	 * 기능 : 로그아웃
//	 * @param request
//	 * @return
//	 */
//	boolean logoutUser(HttpServletRequest request);

}
