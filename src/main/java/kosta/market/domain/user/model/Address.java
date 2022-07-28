package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {
	private int addressId;
	private int userId;
	private String deliveryPlace;
	private String isDefaultAddress;
}
