package kosta.market.domain.product.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductListDto {
    
    private Integer score; // 상품평균점수
    private Integer productId; //상품식별번호
    private String productName; // 상품명
    private Integer productPrice; // 상품가격

    //user용
    private String productDescription; // 상품상세 설명
    private String productImgFileName; //상품이미지파일명
    private String productImgPath; // 상품이미지저장경로

    private Integer productQuantity; // 구매 가능여부용
  }
