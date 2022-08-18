package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class
AddressDto {

    private Integer address_id;
    private Integer user_id;
    private String delivery_place;
    private String is_default_address;
}
