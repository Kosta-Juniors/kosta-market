package kosta.market.domain.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

	/**
	 * 기능 : 회원정보 변경처리
	 * 변경 가능한 정보 : 패스워드(password), 연락처(contact)
	 * @param user user_id를 통해 User를 검색해 userModify 인스턴스를 생성하고 이를 통해 변경처리
	 * @return 회원정보 변경처리에 성공하면 true, 실패하면 false
	 */
	boolean modifyUser(User user);

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
	AddressDto addressInfo(Integer userId);

	/**
	 * 기능 : 배송지 삭제
	 * @param addressDto
	 * @param session 세션을 통해 key값에 저장된 user_id를 가져옴
	 *                user_id를 통해 addressDto 값들을 가져오고
	 *                addressDto의 address_id를 삭제
	 * @return 배송지 삭제가 성공하면 true, 실패하면 false
	 */
	boolean removeAddress(AddressDto addressDto, HttpSession session);

	/**
	 * 기능 : 판매자 등록
	 * 판매자 등록하면서 key = "seller_id", value = seller_id 생성
	 * @param sellerDto 판매자와 관련된 user_id, Seller_id, business_reg_no 를 담고 있는 Dto
	 * @param session
	 * @return 판매자 등록이 성공하면 true, 실패하면 false
	 */
	boolean addSeller(SellerDto sellerDto, HttpSession session);

	/**
	 * 기능 : 판매자 정보 불러오기 -> 판매자 정보를 삭제 위한 목적으로 생성
	 * @param userId
	 * @return
	 */
	SellerDto sellerInfo(Integer userId);

	/**
	 * 기능 : 판매자 제거
	 * 판매자를 삭제하면서 "seller_id" 세션 삭제
	 * @param sellerDto
	 * @param session "user_id" 세션을 통해 user_id를 가져와 sellerDto에 있는 판매자 정보를 불러오고,
	 *                정보가 있으면 판매자 삭제
	 * @return
	 */
	boolean removeSeller(SellerDto sellerDto, HttpSession session);

//	/**
//	 * 기능 : 로그아웃
//	 * @param request
//	 * @return
//	 */
//	boolean logoutUser(HttpServletRequest request);

}
