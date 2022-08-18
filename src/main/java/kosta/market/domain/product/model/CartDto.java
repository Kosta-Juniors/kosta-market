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

// 장바구니 추가 및 수정에 이용
public class CartDto {

    private int userId; // 사용자 Id
    private int productId; // 상품 식별 번호
    private int quantity; //장바구니 주문 수량
}
