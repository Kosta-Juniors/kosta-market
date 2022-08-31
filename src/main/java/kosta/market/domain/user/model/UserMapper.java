package kosta.market.domain.user.model;


import java.util.List;
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
    int insertUser(@Param("username") Object username, @Param("password") Object password,
        @Param("name") Object name, @Param("contact") Object contact);

    @Select("SELECT * FROM TBL_USER WHERE username = #{username} AND password= #{password}")
    User selectUserByUsernameAndPassword(@Param("username") Object username,
        @Param("password") Object password);

    @Select("SELECT * FROM TBL_USER WHERE password = #{password}")
    User selectUserByPassword(@Param("password") Object password);

    @Select("SELECT A.user_id, A.username, A.password, A.name, A.contact, B.seller_id FROM TBL_USER AS A"
            + " JOIN TBL_SELLER AS B ON A.user_id = B.user_id WHERE A.username = #{username} and A.password = #{password}")
    User selectJoinUserByUsernameAndPassword(@Param("username") Object username,
        @Param("password") Object password);

    @Select("SELECT * FROM TBL_USER WHERE user_id = #{userId}")
    User selectUserByUserId(@Param("userId") Object userId);

    @Select("SELECT A.user_id, A.username, A.password, A.name, A.contact, B.seller_id FROM TBL_USER AS A"
            + " JOIN TBL_SELLER AS B ON A.user_id = B.user_id WHERE A.user_id = #{userId}")
    User selectUserAndSellerByUserId(@Param("userId") Object userId);

    @Update("UPDATE TBL_USER SET password=#{password}, contact=#{contact} WHERE user_id= #{userId}")
    void updateUser(@Param("userId") Object userId, @Param("password") Object password,
        @Param("contact") Object contact);

    @Delete("DELETE FROM TBL_USER WHERE user_id = #{userId}")
    void deleteUser(@Param("userId") Object userId);

    @Update("UPDATE TBL_ADDRESS SET is_default_address='0' WHERE user_id=#{userId}")
    void updateAddress(@Param("userId") Object userId);

    // is_default 1이면 현재 배송지, 0이면 과거 배송지
    @Insert("INSERT INTO TBL_ADDRESS(user_id, delivery_place, is_default_address, title, contact, recipient) VALUES(#{userId}, #{deliveryPlace}, '1', #{title}, #{contact}, #{recipient})")
    int insertAddress(@Param("userId") Object userId, @Param("deliveryPlace") Object deliveryPlace,
        @Param("title") Object title, @Param("contact") Object contact,
        @Param("recipient") Object recipient);

    @Select("SELECT * FROM TBL_ADDRESS WHERE user_id = #{userId} ORDER BY address_id DESC LIMIT 3")
    List<AddressDto> selectListAddressByUserId(@Param("userId") Object userId);

    @Select("SELECT * FROM TBL_ADDRESS WHERE user_id = #{userId} AND address_id = #{addressId}")
    AddressDto selectAddressById(@Param("userId") Object userId,
        @Param("addressId") Object addressId);

    @Delete("DELETE FROM TBL_ADDRESS WHERE address_id = #{addressId} ORDER by address_id")
    void deleteAddress(@Param("addressId") Object addressId);

    @Insert("INSERT INTO TBL_SELLER(user_id, business_reg_no) VALUES(#{userId}, #{businessRegNo})")
    int insertSeller(@Param("userId") Object userId, @Param("businessRegNo") Object businessRegNo);

    @Select("SELECT * FROM TBL_SELLER WHERE user_id = #{userId} AND seller_id = #{sellerId}")
    SellerDto selectSellerById(@Param("userId") Object userId, @Param("sellerId") Object SellerId);

    @Delete("DELETE FROM TBL_SELLER WHERE seller_id = #{sellerId}")
    void deleteSeller(@Param("sellerId") Object sellerId);

}
