package kosta.market.domain.product.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductCreateDto {

    private String productName;
    private int productPrice;
    private String productImgFileName;
    private String productImgPath;
    private String productDescription;
    private int productQuantity;
    private int categoryId;

}
