package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class SellerCreateDto {
    private int user_id;
    private String business_reg_no;
}
