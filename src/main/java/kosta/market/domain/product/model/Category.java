package kosta.market.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Category {
	private int categoryId;
	private String categoryName;
}
