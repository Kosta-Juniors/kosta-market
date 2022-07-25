package kosta.market.domain.product.model;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper {
	@Insert("INSERT INTO TBL_PRODUCT VALUES(null, #{name}, #{price}, #{imgFileName}, #{imgPath}, #{description}, #{quantity}, null)")
	int insertProduct(@Param("name") String productName,
		@Param("price") int productPrice,
		@Param("imgFileName") String productImageFileName,
		@Param("imgPath") String productImagePath,
		@Param("description") String productDescription,
		@Param("quantity") int productQuantity);

	@Insert("INSERT INTO TBL_SELLER_PRODUCT VALUES(null, ${sellerId}, ${productId})")
	int insertSellerProduct(@Param("sellerId") int sellerId, @Param("productId") int productId);

	@Insert("INSERT INTO TBL_PRODUCT_CATEGORY VALUES(null, ${productId}, ${categoryId})")
	int insertProductCategory(@Param("productId") int productId, @Param("categoryId") int categoryId);

	@Select("SELECT * FROM TBL_SELLER_PRODUCT as A"
		+ "JOIN TBL_PRODUCT as B ON A.product_id = B.product_id"
		+ "WHERE A.seller_id = #{sellerId}")
	List<Product> selectSellerProductList(@Param("sellerId") int sellerId);

	@Select("SELECT * FROM PRODUCT")
	List<Product> selectProductList();

	@Select("SELECT * FROM PRODUCT WHERE product_id=#{productId}")
	Product selectProduct(@Param("productId") int productId);

	@Update("UPDATE SET product_name=#{productName}, product_price=#{productPrice}, product_img_file_name=#{productImageFileName},"
		+ "product_img_path=#{productImagePath}, product_description=#{productDescription}, product_quantity=#{productQuantity}")
	void updateProduct(@Param("productName") String productName,
		@Param("productPrice") int productPrice,
		@Param("productImageFileName") String productImageFileName,
		@Param("productImagePath") String productImagePath,
		@Param("productDescription") String productDescription,
		@Param("productQuantity") int productQuantity);

	@Delete("DELETE FROM TBL_SELLER_PRODUCT WHERE seller_id = #{sellerId}")
	int deleteProduct(@Param("sellerId") int sellerId);
}