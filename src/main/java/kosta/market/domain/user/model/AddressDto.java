package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Integer addressId;
    private Integer userId;
    private String deliveryPlace;
    private String isDefaultAddress;
    private String title;
    private String recipient;
    private String contact;
}
