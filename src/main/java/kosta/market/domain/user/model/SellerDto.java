package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private Integer userId;
    private String username;
    private String name;
    private String contact;
    private Integer sellerId;
    private String businessRegNo;
}