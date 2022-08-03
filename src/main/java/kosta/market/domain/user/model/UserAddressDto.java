package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAddressDto {

    private int user_id;
    private String delivery_place;
    private String is_default_address;
}
