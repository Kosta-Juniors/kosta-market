package kosta.market.domain.user.model;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO TBL_USER(username, password, name, contact) "
		+ "VALUES(#{username}, #{password}, #{name}, #{contact})")
	int insertUser(@Param("username") Object username, @Param("password") Object password, @Param("name") Object name, @Param("contact") Object contact);

	@Insert("INSERT INTO TBL_SELLER(user_id, business_reg_no) "
		+ "VALUES(#{user_id}, #{business_reg_no})")
	int insertSeller(@Param("user_id") int user_id, @Param("business_reg_no") String business_reg_no);

	// is_default y,n 될 수도 있음
	@Insert("INSERT INTO TBL_ADDRESS(user_id, delivery_place,  is_default_address) "
	+ "VALUES(#{user_id}, #{delivery_place}, '1')")
	int insertAddress(@Param("user_id") int user_id, @Param("delivery_place") String delivery_place);

	@Update("UPDATE TBL_ADDRESS SET is_default_address='0' WHERE user_id=#{user_id}")
	void updateAddress(@Param("user_id") int user_id);

	@Select("SELECT * FROM TBL_USER WHERE user_id = #{user_id}")
	User selectUserByUserId(@Param("user_id") int user_id);

	@Select("SELECT * FROM TBL_USER WHERE username = #{username}")
	User selectUserByUserName(@Param("username") String username);

	@Select("SELECT * FROM TBL_USER WHERE user_id = #{user_id}")
	UserModifyDto selectUser(@Param("user_id") int user_id);

	// 유저 정보 수정에 필요한 데이터 호출
	@Select("SELECT A.username,A.password,A.name,A.contact,B.business_reg_no FROM TBL_USER AS A "
		+ "JOIN TBL_SELLER AS B ON A.user_id = B.user_id "
		+ "WHERE A.user_id=#{user_id}")
	UserModifyDto selectUserModifyDto(@Param("user_id")int user_id);

	//공통정보 수정
	@Update("UPDATE TBL_USER SET password=#{password}, contact=#{contact} WHERE user_id=#{user_id}" )
	void updateUser(@Param("user_id") int user_id, @Param("password") String password, @Param("contact") String contact);

	//사업자등록번호 수정
	@Update("UPDATE TBL_SELLER SET business_reg_no=#{business_reg_no} WHERE user_id=#{user_id}" )
	void updateSeller(@Param("user_id") int user_id, @Param("business_reg_no") String business_reg_no);

	@Select("SELECT * FROM TBL_USER WHERE username = #{username}, password= #{password}")
	User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	@Select("SELECT * FROM TBL_USER WHERE username password= #{password}")
	User selectUserByPassword(@Param("password") String password);

	@Delete("DELETE FROM TBL_USER WHERE user_id = #{user_id}")
	void deleteUser(@Param("user_id") int user_id);
}
