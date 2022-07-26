package kosta.market.domain.user.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
	@Select("SELECT LAST_INSERT_ID()")
	int lastInsertId();
	@Insert("INSERT INTO TBL_USER(username, password, name, contact) "
		+ "VALUES(#{username}, #{password}, #{name}, #{contact})")
	int insertUser(@Param("username") Object username, @Param("password") Object password, @Param("name") Object name, @Param("contact") Object contact);

	@Insert("INSERT INTO TBL_SELLER(user_id, business_reg_no) "
		+ "VALUES(#{userId}, #{businessNo})")
	int insertSeller(@Param("userId") Object userId, @Param("businessNo") Object businessNo);

	@Insert("INSERT INTO TBL_ADDRESS(user_id, delivery_place, is_default_address) "
		+ "VALUES(#{userId}, #{deliveryPlace}, #{isDefaultAddress})")
	int insertAddress(@Param("userId") Object userId, @Param("deliveryPlace") Object deliveryPlace, @Param("isDefaultAddress") Object isDefaultAddress);

	@Select("SELECT * FROM TBL_USER WHERE user_id = #{userId}")
	User selectUserByUserId(@Param("userId") Object userId);

	@Select("SELECT * FROM TBL_SELLER WHERE user_id = #{userId}")
	Seller selectSellerByUserId(@Param("userId") Object userId);

	@Update("UPDATE TBL_USER SET password=#{password}, name=#{name}, contact=#{contact} WHERE user_id=#{userId}" )
	void updateUser(@Param("userId") Object userId, @Param("password") String password, @Param("name") String name, @Param("contact") String contact);

	@Select("SELECT * FROM TBL_USER WHERE username = #{username} and password = #{password}")
	User selectUserByUsernameAndPassword(@Param("username") Object username, @Param("password") Object password);

	@Delete("DELETE FROM TBL_USER WHERE user_id = #{userId}")
	void deleteUser(@Param("userId") Object userId);
}
