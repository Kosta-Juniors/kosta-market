package kosta.market.domain.product.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductCreateDto {

    private String productName;
    private Integer productPrice;
    private String productImgFileName;
    private String productImgPath;
    private String productDescription;
    private Integer productQuantity;
    private Integer categoryId;

}
