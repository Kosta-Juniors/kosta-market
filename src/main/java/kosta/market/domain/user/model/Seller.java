package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Seller {
	private int sellerId;
	private int userId;
	private String businessRegNo;
}
