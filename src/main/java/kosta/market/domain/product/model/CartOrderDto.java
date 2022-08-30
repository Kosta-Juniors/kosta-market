package kosta.market.domain.product.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartOrderDto {

  private Integer productId; // 상품식별번호
  private Integer orderQuantity; // 장바구니에 담은 수량
}
