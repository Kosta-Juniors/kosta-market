package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class UserModifyDto {

    private String username;
    private String password;
    private String name;
    private String contact;
    private String business_reg_no;

}
