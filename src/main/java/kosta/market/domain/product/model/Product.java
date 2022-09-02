package kosta.market.domain.product.model;

import java.util.Date;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
//	private int product_id;
//	private String product_name;
//	private int product_price;
//	private String product_image_file_name;
//	private String product_image_path;
//	private String product_description;
//	private int product_quantity;
//	private Date product_date;

	private int productId;
	private String productName;
	private int productPrice;
	private String productImageFileName;
	private String productImagePath;
	private String productDescription;
	private int productQuantity;
	private Date productDate;
	private String deleted;
}
