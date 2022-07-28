package kosta.market.domain.product.model;

import java.util.List;
import kosta.market.domain.user.model.Seller;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper {

	@Select("SELECT LAST_INSERT_ID()")
	int lastInsertId();

	@Insert("INSERT INTO TBL_PRODUCT VALUES(null,"
		+ " #{name}, "
		+ "#{price}, "
		+ "#{imgFileName}, "
		+ "#{imgPath}, "
		+ "#{description}, "
		+ "#{quantity}, "
		+ "now() )")
	int insertProduct(@Param("name") Object productName,
		@Param("price") Object productPrice,
		@Param("quantity") Object productQuantity,
		@Param("imgFileName") Object productImageFileName,
		@Param("imgPath") Object productImagePath,
		@Param("description") Object productDescription);

	@Insert("INSERT INTO TBL_SELLER_PRODUCT VALUES(null, ${sellerId}, ${productId})")
	int insertSellerProduct(@Param("sellerId") Object sellerId, @Param("productId") Object productId);

	@Insert("INSERT INTO TBL_PRODUCT_CATEGORY VALUES(null, ${productId}, ${categoryId})")
	int insertProductCategory(@Param("productId") Object productId, @Param("categoryId") Object categoryId);

	@Select("SELECT * FROM TBL_PRODUCT as A "
		+ "JOIN TBL_SELLER_PRODUCT as B ON A.product_id = B.product_id "
		+ "WHERE B.seller_id = #{sellerId}")
	List<Product> selectSellerProductList(@Param("sellerId") Object sellerId);

	@Select("SELECT * FROM TBL_SELLER as A "
		+ "JOIN TBL_SELLER_PRODUCT as B ON A.seller_id = B.seller_id "
		+ "WHERE B.product_id = #{productId}")
	Seller selectProductOwner(@Param("productId") Object productId);

	@Select("SELECT * FROM TBL_PRODUCT")
	List<Product> selectProductList();

	@Select("SELECT * FROM TBL_PRODUCT WHERE product_id=#{productId}")
	Product selectProduct(@Param("productId") Object productId);

	@Update("UPDATE SET product_name=#{productName}, product_price=#{productPrice}, product_img_file_name=#{productImageFileName},"
		+ "product_img_path=#{productImagePath}, product_description=#{productDescription}, product_quantity=#{productQuantity}")
	void updateProduct(@Param("productName") Object productName,
		@Param("productPrice") Object productPrice,
		@Param("productImageFileName") Object productImageFileName,
		@Param("productImagePath") Object productImagePath,
		@Param("productDescription") Object productDescription,
		@Param("productQuantity") Object productQuantity);

	@Delete("DELETE FROM TBL_PRODUCT WHERE product_id = #{productId}")
	int deleteProduct(@Param("productId") Object productId);
}