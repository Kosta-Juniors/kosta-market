package kosta.market.domain.product.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class ProductListDto {

    private int productId; // 상품식별번호
    private String productName; //상품명
    private int productPrice; //상품가격

    //user용
    private String productImgFileName; // 이미지 파일 이름
    private String productImgPath; // 이미지 파일 경로

    private int productQuantity; // 구매 가능여부용


     // 추후 검색 기능 및 표시 관련해서 의견을 들어보고 결정
//    private int sellerId or userName;
//    private int categoryId;


}
