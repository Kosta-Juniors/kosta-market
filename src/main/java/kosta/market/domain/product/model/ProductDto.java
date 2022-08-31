package kosta.market.domain.product.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// 상품상세보기 및 상품수정에 필요한 정보
// 상품리스트를 받아올 때 사용

public class ProductDto {

    private Integer productId; //상품식별번호
    private String productName; // 상품명
    private Integer productPrice; // 상품가격

    //user용
    private String productDescription; // 상품상세 설명
    private String productImgFileName; // 이미지 파일 이름
    private String productImgPath; // 이미지 파일 경로

    private Integer productQuantity; // 구매 가능여부용
}
