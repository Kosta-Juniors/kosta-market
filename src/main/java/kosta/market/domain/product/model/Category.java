package kosta.market.domain.product.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
// 카테고리식별번호 / 카테고리명

    private Integer categoryId; // 우선은 필요하다고 가정하고 넣음(입력 시)
    private String categoryName; // 카타고리 목록을 받아올

}
