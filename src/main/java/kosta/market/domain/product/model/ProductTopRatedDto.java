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
public class ProductTopRatedDto {
    
    private Integer score; // 상품평균점수
    private Integer productId; //상품식별번호
    private String productName; // 상품명
    private Integer productPrice; // 상품가격
    private String productImgFileName; //상품이미지파일명
    private String productImgPath; // 상품이미지저장경로
  }
