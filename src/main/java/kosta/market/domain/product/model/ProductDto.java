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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
// 상품상세보기 및 상품수정에 필요한 정보
public class ProductDto {

    private int productId; //상품식별번호
    private String productName; // 상품명
    private int productPrice; // 상품가격
    private String productImgFileName; //상품이미지파일명
    private String productImgPath; // 상품이미지저장경로
    private String productDescription; // 상품상세설명
    private int productQuantity; //재고수량
    private MultipartFile productImgFile;
}
