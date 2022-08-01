package kosta.market.domain.product.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private int productId;
	private String productName;
	private String productPrice;
	private String productImageFileName;
	private String productImagePath;
	private String productDescription;
	private int productQuantity;
	private Date productDate;
}
