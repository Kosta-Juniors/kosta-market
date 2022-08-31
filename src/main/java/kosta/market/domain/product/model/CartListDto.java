package kosta.market.domain.product.model;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// 장바구니 목록 불러오기에 이용
public class CartListDto {

    // 장바구니 정보
    private Integer productId; // 상품식별번호
    private Integer quantity; // 장바구니에 담은 수량
    
    //상품정보
    private String productName; // 상품명
    private Integer productPrice; // 상품가격
    private String productImgFileName; // 상품이미지명
    private String productImgPath; // 이미지저장경로
    private Integer productQuantity; // 상품 수량-> 주문 시 수량 제한을 걸기 위해

    
}
