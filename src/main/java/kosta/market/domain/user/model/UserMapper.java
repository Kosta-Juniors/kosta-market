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

	@Select("SELECT * FROM TBL_USER WHERE username = #{username} AND password= #{password}")
	User selectUserByUsernameAndPassword(@Param("username") Object username, @Param("password") Object password);

	@Select("SELECT * FROM TBL_USER WHERE password = #{password}")
	User selectUserByPassword(@Param("password") Object password);

	@Select("SELECT A.user_id, A.username, A.password, A.name, A.contact, B.seller_id FROM TBL_USER AS A"
		+ " JOIN TBL_SELLER AS B ON A.user_id = B.user_id"
		+ " WHERE A.username = #{username} and A.password = #{password}")
	User selectJoinUserByUsernameAndPassword(@Param("username") Object username, @Param("password") Object password);

	@Select("SELECT * FROM TBL_USER WHERE user_id = #{user_id}")
	User selectUserByUserId(@Param("user_id") Object user_id);

	@Update("UPDATE TBL_USER SET password=#{password}, contact=#{contact} WHERE user_id= #{user_id}" )
	void updateUser(@Param("user_id") Object user_id, @Param("password") Object password, @Param("contact") Object contact);

	@Delete("DELETE FROM TBL_USER WHERE user_id = #{user_id}")
	void deleteUser(@Param("user_id") Object user_id);

	@Update("UPDATE TBL_ADDRESS SET is_default_address='0' WHERE user_id=#{user_id}")
	void updateAddress(@Param("user_id") Object user_id);

	// is_default 1이면 현재 배송지, 0이면 과거 배송지
	@Insert("INSERT INTO TBL_ADDRESS(user_id, delivery_place, is_default_address) VALUES(#{user_id}, #{delivery_place}, '1')")
	int insertAddress(@Param("user_id") Object user_id, @Param("delivery_place") Object delivery_place);

	@Select("SELECT * FROM TBL_ADDRESS WHERE user_id = #{user_id}")
	AddressDto selectAddressByUserId(@Param("user_id") Object user_id);

	@Delete("DELETE FROM TBL_ADDRESS WHERE address_id = #{address_id}")
	void deleteAddress(@Param("address_id") Object address_id);

	@Insert("INSERT INTO TBL_SELLER(user_id, business_reg_no) VALUES(#{user_id}, #{business_reg_no})")
	int insertSeller(@Param("user_id") Object user_id, @Param("business_reg_no") Object business_reg_no);

	@Select("SELECT * FROM TBL_SELLER WHERE user_id = #{user_id}")
	SellerDto selectSellerByUserId(@Param("user_id") Object user_id);

	@Delete("DELETE FROM TBL_SELLER WHERE seller_id = #{seller_id}")
	void deleteSeller(@Param("seller_id") Object seller_id);

}
