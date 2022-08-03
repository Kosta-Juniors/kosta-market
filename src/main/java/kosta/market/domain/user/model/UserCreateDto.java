package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {

    private String username;
    private String password;
    private String name;
    private String contact;

}
