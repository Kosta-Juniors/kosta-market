package kosta.market.domain.user.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO TBL_USER(username, password, name, contact) "
		+ "VALUES(#{user.username}, #{user.password}, #{user.name}, #{user.contact})")
	int insertUser(@Param("user") User user);

	@Insert("INSERT INTO TBL_SELLER(user_id, business_id) "
		+ "VALUES(#{userId}, #{businessNo})")
	int insertSeller(@Param("userId") int userId, @Param("businessNo") String businessNo);

	@Select("SELECT * FROM TBL_USER WHERE user_id = #{user_id}")
	User selectUserByUserId(@Param("user_id") int userId);

	@Update("UPDATE TBL_USER SET password=#{password}, name=#{name}, contact=#{contact} WHERE user_id=#{userId}" )
	void updateUser(@Param("userId") int userId, @Param("password") String password, @Param("name") String name, @Param("contact") String contact);

	@Select("SELECT * FROM TBL_USER WHERE username = #{username} and password = #{password}")
	User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	@Delete("DELETE FROM TBL_USER WHERE user_id = #{userId}")
	void deleteUser(@Param("userId") int userId);
}
